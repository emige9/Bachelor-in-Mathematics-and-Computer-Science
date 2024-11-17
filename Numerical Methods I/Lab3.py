from numpy import *
from matplotlib.pyplot import *


print('Práctica 3')

print('Ejercicio 1')

def tabla_diferencias_divididas(x,y):
    """ Calcula la tabla completa de las diferencias divididas a partir de los datos x e y.
    Devuelve una matriz (df) triangular inferior que en la columna k-esima contiene las
    diferencias divididas de orden k"""

    n= len(y)
    df=zeros([n,n])
    df[:,0]=y
    yn=y
    for i in range(0, len(x)-1):
        dx=x[i+1: len(x)]-x[0:n-(i+1)]
        yn=diff(yn)/dx     #diff se encarga de hacer las diferencias divididas (una menos la anterior)
        df[i+1:n,i+1]=yn
    return df



print('Apartado a')

x = linspace(0,1,5)
y = exp(x)

tabla_dif = tabla_diferencias_divididas(x,y)
print(tabla_dif)




print('Ejercicio 2')

    
def eval_forma_newton(x,y,z_0):
    """ Calcula en primer lugar el polinomio de interpolacion de Lagrange que interpola los datos x e
    y mediante la formula de Newton y lo evalua en z0."""  
    n= len(y)
    df=tabla_diferencias_divididas(x,y)
    peval=df[0,0]
    prod=1.0
    for i in range(1,n):
        prod=prod*(z_0-x[i-1])
        peval=peval+df[i,i]*prod
    return peval


print('Apartado a')

x = linspace(0,1,5)
y = exp(x)
z_0 = 1/3

eval1 = eval_forma_newton(x, y, z_0)
print('El polinomio de interpolacion evaluado en 1/3 vale ', eval1)
print('¿Pasa por los puntos de interpolacion?')
print(eval_forma_newton(x, y, x) == y)
print(all(eval_forma_newton(x, y, x) == y))



print('Apartado b')


def Horner(coefs,z0):
    
    n = len(coefs)
    peval = coefs[n-1]
    for i in range(n-2,-1,-1):
        peval = peval*z0 + coefs[i]
    return peval

coefs = [1,1,1]    # x**2 + x + 1
print(Horner(coefs, 0))
print(Horner(coefs, 1))


print('Apartado c')

def eval_forma_Horner(x, y, z0):
    n = len(y)
    df = tabla_diferencias_divididas(x, y)
    peval = df[n-1,n-1]
    for i in range(n-2,-1,-1):
        peval = peval*(z0 - x[i]) + df[i,i]
    return peval

eval2 = eval_forma_Horner(x,y,1/3)
print('El polinomio de interpolacion evaluado en 1/3 vale ', eval2)


print('Apartado d')

def evalpol_eqd(f, a, b, N, z0):
    x = linspace(a,b,N+1)   #particion equidistante con N intervalos
    y = f(x)
    pz0 = eval_forma_Horner(x, y, z0)
    error = max(abs(f(z0)-pz0))
    return pz0,error


print('Apartado e')

a1 = -3
b1 = 3
n1 = (b1 -a1)/0.01   #numero de intervalos para pintar
z0 = linspace(a1,b1,int(n1+1))

for N in array([5,10,15,20]):
    pz0, error = evalpol_eqd(exp, a1, b1, N, z0)
    x = linspace(a1,b1,N+1)    #nodos de interpolacion
    y = exp(x)
    plot(x,y,'o')
    plot(z0,pz0,label = 'Polinomio de interpolacion')
    plot(z0,exp(z0), label = 'f')
    legend()
    show()
    print('Error con ', N, ' intervalos ', error)
    
    
    
print('Apartado f')

def fun(x):
    return 1/(1+x**2)


a1 = -5
b1 = 5
n1 = (b1 -a1)/0.01   #numero de intervalos para pintar
z0 = linspace(a1,b1,int(n1+1))

for N in array([5,10,15,20]):
    pz0, error = evalpol_eqd(fun, a1, b1, N, z0)
    x = linspace(a1,b1,N+1)    #nodos de interpolacion
    y = fun(x)
    plot(x,y,'o')
    plot(z0,pz0,label = 'Polinomio de interpolacion')
    plot(z0,fun(z0), label = 'f')
    legend()
    show()
    print('Error con ', N, ' intervalos ', error)




print('Apartado g')

def evalpol_cheb(f, a, b, N, z0):
    ind = linspace(0,N, N+1)
    x = cos((2*ind+1)*pi/(2*(N+1)))    #nodos en [-1,1]
    x = a + (b-a)/2*(x+1)   #nodos en [a,b]
    y = f(x)
    pz0 = eval_forma_Horner(x, y, z0)
    error = max(abs(f(z0)-pz0))
    return pz0,error


a1 = -5
b1 = 5
n1 = (b1 -a1)/0.01   #numero de intervalos para pintar
z0 = linspace(a1,b1,int(n1+1))

for N in array([5,10,15,20]):
    pz0, error = evalpol_cheb(fun, a1, b1, N, z0)
    ind = linspace(0,N, N+1)
    x = cos((2*ind+1)*pi/(2*(N+1)))    #nodos en [-1,1]
    x = a1 + (b1-a1)/2*(x+1)   #nodos en [a,b]
    y = fun(x)
    plot(x,y,'o')
    plot(z0,pz0,label = 'Polinomio de interpolacion')
    plot(z0,fun(z0), label = 'f')
    legend()
    show()
    print('Error con ', N, ' intervalos ', error)


print('Ejercicio 3')

print('Apartado a')

def ejercicio_3_lineal(f, a,b,N):
    x = linspace(a, b, N+1)
    y = f(x)
    pol = interp1d(x,y,kind='linear')
    return pol

def ejercicio_3_cubico(f, a,b,N):
    x = linspace(a, b, N+1)
    y = f(x)
    pol = interp1d(x,y,kind='cubic')
    return pol

print('Apartado b')


a = -5
b = 5
h = 0.01
n = int((b-a)/n)
z_0 = linspace(a, b, n+1)

N = 5

spline_lineal = ejercicio_3_lineal(f, a, b, N)
spline_cubico = ejercicio_3_cubico(f, a, b, N)

error_lineal = max(abs(fun(z_0) - spline_lineal(z_0)))
error_cubico = max(abs(fun(z_0) - spline_cubico(z_0)))

print('El error del interpolante lineal a trozos para N = ',N, ' trozos es ', error_lineal)
print('El error del interpolante cubico a trozos para N = ',N, ' trozos es ', error_cubico)

figure()
clf()
subplot(2,1,1)
plot(z_0, fun(z_0), 'b: ', z_0, spline_lineal(z_0), 'r')
subplot(2,1,2)
plot(z_0, fun(z_0, 'b: ', z_0, spline_cubico(z_0), 'r'))























