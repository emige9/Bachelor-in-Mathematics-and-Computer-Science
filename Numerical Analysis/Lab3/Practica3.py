# -*- coding: utf-8 -*-
"""
Created on Sun May 17 11:38:44 2020

@author: Usuario
"""

from pylab import *
import pylab


###############################################################################
###############################################################################
    
print('Ejercicio 1')

###############################################################################
###############################################################################


def locfron(rho, sigma):
# Dibuja la frontera de la region de estabilidad absoluta 
# de un metodo multipaso.
# rho y sigma son los coeficientes de los polinomios caracteristicos
# ordenados de mayor a menor grado '''
    theta = arange(0, 2.*pi, 0.01)
    numer = polyval(rho, exp(theta*1j)) # rho(e^{theta*i})
    denom = polyval(sigma, exp(theta*1j)) # sigma(e^{theta*i})
    mu = numer/denom
    x = real(mu)
    y = imag(mu)
    plot(x, y)
    grid(True)
    axis('equal')

###############################################################################

print('Apartado b - AB')

###############################################################################


figure('AB')
# AB 1 paso
rho = array([1.,-1])
sigma = array([0,1.])
locfron(rho,sigma)
# AB 2 pasos
rho = array([1.,-1, 0])
sigma = array([0,3/2., -1/2.])
locfron(rho,sigma)
# Ejemplo: AB3 y_{k+1} - y_k  = h/12*(23*f_k - 16*f_{k-1} + 5*f_{k-2})
rho = array([1., -1., 0.,0.]) # primero
sigma = array([0., 23., -16., 5.])/12. # segundo
locfron(rho,sigma)
# AB 4 pasos 
rho = array([1.,-1, 0., 0., 0.])
sigma = array([0,55., -59., 37., -9.])/24
locfron(rho,sigma)

legend(['AB1','AB2','AB3','AB4'])
show()

###############################################################################

print('Apartado b - AM')

###############################################################################


figure('AM')
# AM 1 paso
rho = array([1.,-1])
sigma = array([0.5,0.5])
locfron(rho,sigma)
# AM 2 pasos
rho = array([1.,-1, 0])
sigma = array([5/12.,2/3., -1/12.])
locfron(rho,sigma)
# AM 3 pasos
rho = array([1., -1., 0.,0.]) # primero
sigma = array([9., 19., -5., 1.])/24 # segundo
locfron(rho,sigma)
# AM 4 pasos 
rho = array([1.,-1, 0., 0., 0.])
sigma = array([251., 646., -264., 106., -19.])/720   #no me ha dado tiempo a copiarlo
locfron(rho,sigma)
legend(['AM1','AM2','AM3','AM4'])
axis([-8,1,-4,4])
show()

###############################################################################

print('Apartado b - Diferenciación regresiva')

###############################################################################

figure('BDF')
#BDF1
rhoBDF1 = array([1,-1]) # primero
sigmaBDF1 = array([1,0]) # segundo
locfron(rhoBDF1,sigmaBDF1)


#BDF2
rhoBDF2 = array([1,-4/3, 1/3]) # primero
sigmaBDF2 = array([2/3,0,0]) # segundo
locfron(rhoBDF2,sigmaBDF2)

#BDF3
rhoBDF3 = array([1, -18/11, 9/11, -2/11]) # primero
sigmaBDF3 = array([6/11, 0, 0, 0]) # segundo
locfron(rhoBDF3,sigmaBDF3)


#BDF4
rhoBDF4 = array([1,-48/25, 36/25, -16/25, 3/25]) # primero
sigmaBDF4 = array([12/25,0,0,0,0]) # segundo
locfron(rhoBDF4,sigmaBDF4)
legend(['BDF1','BDF2','BDF3','BDF4'])
show()



###############################################################################

print('Apartado a')

###############################################################################

def locfronRK(dR, N):
# Localizacion de la frontera de un metodo RK
#  Derividada de la funcion R
    Npoints = 5000
    T = 2*N*pi
    h = 2*N*pi/Npoints
    z = zeros(Npoints +1 , dtype = complex)
    z[0] = 0.
    t = 0
    for k in range(len(z)-1):
        k1 = 1j*exp(1j*t)/dR(z[k])
        k2 = 1j*exp(1j*(t+0.5*h))/dR(z[k] + 0.5*h*k1)
        k3 = 1j*exp(1j*(t+0.5*h))/dR(z[k] + 0.5*h*k2)
        k4 = 1j*exp(1j*(t+h))/dR(z[k] + h*k3)
        z[k+1] = z[k]+ h*(k1+ 2*k2+ 2*k3 + k4)/6
        t = t + h
    x = real(z)
    y = imag(z)
    plot(x,y)
    grid(True)
    axis('equal')

figure('RK explicitos')

# Euler: función de estabilidad R = 1 + z

def dREuler(z):
    return 1.
locfronRK(dREuler, 1)

# RK2 explicitos: función de estabilidad R = 1 + z + z**2/2


def dRK2exp(z):
    return 1. + z
locfronRK(dRK2exp, 2)

def dRK3exp(z):
    return 1. + z + z**2/2.
locfronRK(dRK3exp,3)

def dRK4exp(z):
    return 1. + z + z**2/2 + z**3/6
locfronRK(dRK4exp, 4.)

legend(['RK1','RK2','RK3','RK4'])
show()



###############################################################################
###############################################################################

print('Ejercicio 2')

###############################################################################
###############################################################################



###############################################################################

print('Apartado a')

###############################################################################

def fun(t,y):
    return -1200*y + 2000 -1500*exp(-t)


def exacta(t):
    return 5/3. - 1495/3597*exp(-1200*t) - 1500/1199*exp(-t)

a = 0.
b = 4.
y0 = 0.


def euler(a, b, fun, N, y0):
    """Implementacion del metodo de Euler en el intervalo [a, b]
    usando N particiones y condicion inicial y0"""
    
    h = (b-a)/N # paso de malla
    t = zeros(N+1) # inicializacion del vector de nodos
    y = zeros(N+1) # inicializacion del vector de resultados
    t[0] = a # nodo inicial
    y[0] = y0 # valor inicial

    # Metodo de Euler
    for k in range(N):
        y[k+1] = y[k]+h*fun(t[k], y[k])
        t[k+1] = t[k]+h
    
    return (t, y)


figure('Apartado 2.a')

# Antes hemos calculado la frontera de la región de estabilidad para el método de Euler.
# Vimos que es la la circunferencia de centro (-1,0) y radio 1. En este problema, el autovalor lambda nos lo dan
# como df/dy y vemos que es un valor real, por tanto podemos considerar el intervalo de estabilidad (-2,0) como
# vimos en teoría. Por tanto, tenemos que -2 < (-1200)h < 0 y despejando vemos que 0 < h < 2/1200 = 1/600
# Entonces el h crítico va a ser h* = 1/600. Como este es el h máximo, el N calculado va a ser el mínimo, ya que N = (b-a)/h

malla = [0.001, 1/600, 0.0017]   # buscamos un valor más grande y otro más pequeño que 1/600

for h in malla:
    N = int((b-a)/h) + 1
    (t,y) = euler(a,b,fun,N,y0)
    plot(t,y)
    
tex = linspace(a,b,200)
yex = exacta(tex)
plot(tex,yex)
legend(['h = 0.001', 'h = 1/600', 'h = 0.0017', 'exacta'])
#axis([0,4,-10,10])
axis([1,1.2,0,2])
show()
    

###############################################################################

print('Apartado b')

###############################################################################


def eulerimplicito(a,b,N,y0):
    
    h = (b-a)/N # paso de malla
    t = zeros(N+1) # inicializacion del vector de nodos
    y = zeros(N+1) # inicializacion del vector de resultados
    t[0] = a # nodo inicial
    y[0] = y0 # valor inicial

    # Metodo de Euler
    for k in range(N):
        t[k+1] = t[k]+h
        numerador = y[k] + h*(2000-1500*exp(-t[k+1]))
        denominador = 1 + 1200*h
        y[k+1] = numerador/denominador
          
    return (t, y)

def exacta(t):
    return 5/3. - 1495/3597*exp(-1200*t) - 1500/1199*exp(-t)

a = 0.
b = 4.
y0 = 0.

# Simplemente tomar valores dentro de la región de estabilidad para hacer pruebas, en este caso entre 0 y 1

figure('Apartado 2.b')

malla = [0.001, 0.01, 0.1, 1.]

for h in malla:
    N = int((b-a)/h) + 1
    (t,y) = eulerimplicito(a,b,N,y0)
    plot(t,y)
    
tex = linspace(a,b,200)
yex = exacta(tex)
plot(tex,yex)
legend(['h = 0.001', 'h = 0.01', 'h = 0.1', 'h = 1.', 'exacta'])
#axis([0,4,-10,10])

show()


###############################################################################

print('Apartado c')

###############################################################################


def rk4(a, b, fun, N, y0):
    """Implementacion del metodo de RK4 en el intervalo [a, b]
    usando N particiones y condicion inicial y0"""
    
    h = (b-a)/N # paso de malla
    t = zeros(N+1) # inicializacion del vector de nodos
    y = zeros(N+1) # inicializacion del vector de resultados
    t[0] = a # nodo inicial
    y[0] = y0 # valor inicial

    # Metodo de RK4
    for k in range(N):
        t[k+1] = t[k]+h
        k1=fun(t[k],y[k])
        k2=fun(t[k] + 0.5*h,y[k] + 0.5*h*k1)
        k3=fun(t[k] + 0.5*h,y[k] + 0.5*h*k2)
        k4=fun(t[k+1], y[k] + h*k3)
        y[k+1] = y[k]+(h/6)*(k1 + 2*k2 + 2*k3 + k4)
        
    return (t, y)

figure('Frontera para punto de corte')
def dRK4exp(z):
    return 1. + z + z**2/2 + z**3/6
locfronRK(dRK4exp, 4.)
show()

figure('Apartado 2.c')

# Antes hemos calculado la frontera de la región de estabilidad para el método RK4 y la hemos mostrado en pantalla.
# Vemos que el corte de la frontera con el eje x se da cuando x = -2.78 (cursor). En este problema, el autovalor lambda nos lo dan
# como df/dy y vemos que es un valor real, por tanto podemos considerar el intervalo de estabilidad (-2.78,0) como
# hemos visto en la figura. Por tanto, tenemos que -2.78 < (-1200)h < 0 y despejando vemos que 0 < h < 2.78/1200
# Entonces el h crítico va a ser h* = 2.78/1200. Como este es el h máximo, el N calculado va a ser el mínimo, ya que N = (b-a)/h

mesh = [0.002, 2.78/1200, 0.0025]    # buscamos un valor más grande y otro más pequeño que 1/600

for h in mesh:
    N = int((b-a)/h) + 1 
    (t,y) = rk4(a, b, fun, N, y0)
    plot(t,y)
    
tex = linspace(a,b,250)
plot(tex, exacta(tex))
legend(['h = 0.002', 'h = 2.78/1200', 'h = 0.0025', 'exacta'])


###############################################################################
###############################################################################

print('Ejercicio 3')

###############################################################################
###############################################################################

figure('Ejercicio 3')
def dREuler(z):
    return 1.
locfronRK(dREuler, 1)


def fun3(t,y):
    return array([y[1], -101*y[0]-20*y[1]])


def exacta3(t):
    return exp(-10*t)*cos(t)


#Calcular los autovalores de una matriz
# Importar pylab: import pylab
A = array([[0,1],[-101,-20]])
autovalores = linalg.eigvals(A) 
print("Los autovalores de la matriz son:", autovalores)


re = -10.
im = 1.

plot([re,re], [im,-im], '*')
plot([0,re], [0,im],'--',[0,re], [0,-im], '--')

hcrit = 20/101   # es el h* normalmente

plot([-10*hcrit,-10*hcrit], [-hcrit, hcrit], 'o')
# (x*, y*) es el punto de la frontera donde se alcanza hcrit
# hcrit*lambda = x* + iy*,
# hcrit(Re(lambda) + iIm(lambda)) = x* + iy*
# x* = Re(lambda)*hcrit 
# y* = Im(lambda)*hcrit  



def eulerSis(a, b, fun, N, y0):
    """Implementacion del metodo de Euler en el intervalo [a, b]
    usando N particiones y condicion inicial y0"""
    
    h = (b-a)/N # paso de malla
    t = zeros(N+1) # inicializacion del vector de nodos
    y = zeros([len(y0),N+1]) # inicializacion del vector de resultados
    t[0] = a # nodo inicial
    y[:,0] = y0 # valor inicial

    # Metodo de Euler
    for k in range(N):
        y[:, k+1] = y[:, k]+h*fun(t[k], y[:, k])
        t[k+1] = t[k]+h
    
    return (t, y)

figure('Ejercicio 3: método de Euler')
a = 0.
b = 7.
z0 = array([1,-10])

N = int((b-a)/hcrit) + 1

mesh = [N-5,N,N+5]

leyenda = []

for N in mesh:
    (t,z) = eulerSis(a,b,fun3,N,z0)
    plot(t,z[0])
    leyenda.append('N = ' + str(N))
    
tex = linspace(a,b,250)
plot(tex, exacta3(tex))
leyenda.append('exacta3')
legend(leyenda)
axis([0,7,-10,10])



###############################################################################
###############################################################################

print('Ejercicio 4')

###############################################################################
###############################################################################



# BDF2, usando como metodo para arrancar Heun, para calcular yk+1 usando algoritmo de punto fijo (el de Ck), como semilla z0=yk, y deteniendo la iteracion de punto fijo cuando |zl+1 -zl| <10^-12 o numero maximo de iteraciones=200 

def BDF2(a,b,fun, N,y0):
    iter = 0
    tol = 1.e-12
    maxiter = 200
    
    
    y = zeros(N+1) 
    t = zeros(N+1)
    f = zeros(N+1) 
    t[0] = a
    h = (b-a)/float(N) 
    y[0] = y0
    f[0] = fun(a,y[0])   
    for k in range(2):
        y[k+1] = y[k]+(h/2)*(fun(t[k], y[k])+fun(t[k+1], y[k]+h*fun(t[k],y[k])))
        t[k+1] = t[k] +h
        f[k+1] = fun(t[k+1], y[k+1])   
    for k in range(2,N):
        cK = 4/3.*y[k] -1/3.*y[k-1]#el ck contiene todo lo que hemos calculado
        t[k+1] = t[k] +h
        cont =0
        error = 1 +tol
        z = y[k] #es la semilla que elijo, 
        while (error >= tol and cont < maxiter): #error mide la distancia entre dos iteraciones sucesivas
            znew = h*2/3*fun(t[k+1],z) +cK #metemos iteraciones del metodo punto fijo
            error = abs(znew -z) #la distancia
            z = znew
            cont = cont +1
        iter = max(iter,cont)
        if cont == maxiter:
            print('EL metodo de punto fijo no converge')
        y[k+1] = z #el ultimo que me ha dado el metodo
        f[k+1] = fun(t[k+1], y[k+1])
    print('Maximo numero de iteraciones del MPF realizadas:', iter)
        
    return (t,y)


# BDF2, para sistemas,  usando como metodo para arrancar Heun, para calcular yk+1 usando algoritmo de punto fijo (el de Ck), como semilla z0=yk, y deteniendo la iteracion de punto fijo cuando |zl+1 -zl| <10^-12 o numero maximo de iteraciones=200 

def BDF2_sistemas(a,b,fun, N,y0):
    iter = 0
    tol = 1.e-12
    maxiter = 200
    
    
    y = zeros([len(y0),N+1]) 
    t = zeros(N+1)
    f = zeros([len(y0),N+1]) 
    t[0] = a
    h = (b-a)/float(N) 
    y[:,0] = y0
    f[:,0] = fun(a,y[:,0])   
    for k in range(2):
        y[:,k+1] = y[:,k]+(h/2)*(fun(t[k], y[:,k])+fun(t[k+1], y[:,k]+h*fun(t[k],y[:,k])))
        t[k+1] = t[k] +h
        f[:,k+1] = fun(t[k+1], y[:,k+1])   
    for k in range(2,N):
        cK = 4/3.*y[:,k] -1/3.*y[:,k-1]#el ck contiene todo lo que hemos calculado
        t[k+1] = t[k] +h
        cont =0
        error = 1 +tol
        z = y[:,k] #es la semilla que elijo, 
        while (error >= tol and cont < maxiter): #error mide la distancia entre dos iteraciones sucesivas
            znew = h*2/3*fun(t[k+1],z) +cK #metemos iteraciones del metodo punto fijo
            error = max(abs(znew -z)) #la distancia
            z = znew
            cont = cont +1
        iter = max(iter,cont)
        if cont == maxiter:
            print('EL metodo de punto fijo no converge')
        y[:,k+1] = z #el ultimo que me ha dado el metodo
        f[:,k+1] = fun(t[k+1], y[:,k+1])
    print('Maximo numero de iteraciones del MPF realizadas:', iter)
        
    return (t,y)



# 22.BDF4, usando como metodo para arrancar Heun, para calcular yk+1 usando algoritmo de punto fijo (el de Ck), como semilla z0=yk, y deteniendo la iteracion de punto fijo cuando |zl+1 -zl| <10^-8 o numero maximo de iteraciones=15 

def BDF4(a,b,fun, N,y0):
    iter = 0
    tol = 1.e-8
    maxiter = 15
    
    
    y = zeros(N+1) 
    t = zeros(N+1)
    f = zeros(N+1) 
    t[0] = a
    h = (b-a)/float(N) 
    y[0] = y0
    f[0] = fun(a,y[0])   
    for k in range(2):
        y[k+1] = y[k]+(h/2)*(fun(t[k], y[k])+fun(t[k+1], y[k]+h*fun(t[k],y[k])))
        t[k+1] = t[k] +h
        f[k+1] = fun(t[k+1], y[k+1])   
    for k in range(2,N):
        cK = 48/25.*y[k] -36/25.*y[k-1] +16/25.*y[k-2] -3/25.*y[k-3]#el ck contiene todo lo que hemos calculado
        t[k+1] = t[k] +h
        cont =0
        error = 1 +tol
        z = y[k] #es la semilla que elijo, 
        while (error >= tol and cont < maxiter): #error mide la distancia entre dos iteraciones sucesivas
            znew = h*12/25*fun(t[k+1],z) +cK #metemos iteraciones del metodo punto fijo
            error = abs(znew -z) #la distancia
            z = znew
            cont = cont +1
        iter = max(iter,cont)
        if cont == maxiter:
            print('EL metodo de punto fijo no converge')
        y[k+1] = z #el ultimo que me ha dado el metodo
        f[k+1] = fun(t[k+1], y[k+1])
    print('Maximo numero de iteraciones del MPF realizadas:', iter)
        
    return (t,y)


# 23.BDF4 para sistemas, usando como metodo para arrancar Heun, para calcular yk+1 usando algoritmo de punto fijo (el de Ck), como semilla z0=yk, y deteniendo la iteracion de punto fijo cuando |zl+1 -zl| <10^-8 o numero maximo de iteraciones=15 

def BDF4_sistemas(a,b,fun, N,y0):
    iter = 0
    tol = 1.e-8
    maxiter = 15
    
    
    y = zeros([len(y0),N+1]) 
    t = zeros(N+1)
    f = zeros([len(y0),N+1]) 
    t[0] = a
    h = (b-a)/float(N) 
    y[:,0] = y0
    f[:,0] = fun(a,y[:,0])   
    for k in range(2):
        y[:,k+1] = y[:,k]+(h/2)*(fun(t[k], y[:,k])+fun(t[k+1], y[:,k]+h*fun(t[k],y[:,k])))
        t[k+1] = t[k] +h
        f[:,k+1] = fun(t[k+1], y[:,k+1])   
    for k in range(2,N):
        cK = 48/25.*y[:,k] -36/25.*y[:,k-1] +16/25.*y[:,k-2] -3/25.*y[:,k-3]#el ck contiene todo lo que hemos calculado
        t[k+1] = t[k] +h
        cont =0
        error = 1 +tol
        z = y[:,k] #es la semilla que elijo, 
        while (error >= tol and cont < maxiter): #error mide la distancia entre dos iteraciones sucesivas
            znew = h*12/25*fun(t[k+1],z) +cK #metemos iteraciones del metodo punto fijo
            error = max(abs(znew -z)) #la distancia
            z = znew
            cont = cont +1
        iter = max(iter,cont)
        if cont == maxiter:
            print('EL metodo de punto fijo no converge')
        y[:,k+1] = z #el ultimo que me ha dado el metodo
        f[:,k+1] = fun(t[k+1], y[:,k+1])
    print('Maximo numero de iteraciones del MPF realizadas:', iter)
        
    return (t,y)


def DR3(a,b,fun, N,y0):
    
    y = zeros(N+1)
    t = zeros(N+1)
    f = zeros(N+1) #SE GUARDAN PORQUE NECESITAS EVALUAR LA f EN CADA PUNTO Y ES MEJOR GUARADARLA A VOLVER A CALCULARLA (MUCHO COSTE)
    t[0] = a
    h = (b-a)/float(N) 
    y[0] = y0
    f[0] = fun(a,y[0])    
    
    # Utilizamos el RK4 para aproximar las dos primeras soluciones
    for k in range(2):
        k1 = fun(t[k], y[k])
        k2 = fun(t[k] +  h/2, y[k] + 0.5 * h * k1)
        k3 = fun(t[k] +  h/2, y[k] + 0.5 * h * k2)
        k4 = fun(t[k] +  h, y[k] + h * k3)
        y [k + 1] = y [k] + h / 6 * (k1 + 2*k2 + 2*k3 + k4)
        t [k + 1] = t [k]+ h
        f[k + 1] = fun(t[k+1], y[k+1]) #si no guardamos la f se queda a 0
    
    # Empezamos en 3 porque ya hemos calculado las soluciones anteriores aproximadas
    for k in range(2,N):
        Ck = (-18/11)*y[k] + (9/11)*y[k-1] - (2/11)*y[k-2]
        divisor = 1 + (6/11)*1200*h
        t[k+1] = t[k] + h # porque lo utilizo ahora
        y[k+1] = ((6/11)*h*2000 - (6/11)*h*1500*exp(-t[k+1]) - Ck)/divisor
        f[k+1] = fun(t[k+1], y[k+1])
        
    return (t,y)










    






