# -*- coding: utf-8 -*-
"""
Created on Thu Apr  4 09:16:04 2024

@author: Usuario_UMA
"""

from pylab import *
from time import perf_counter

print('\n Práctica 1 \n')

print('\n Ejercicio 4 - Euler \n')


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

print('\n Ejemplo de uso del método de Euler para sistemas \n')

def f4(t,y):
    """Función que define el sistema diferencial"""
    dx=0.25*y[0]-0.01*y[0]*y[1]
    dy=-y[1]+0.01*y[0]*y[1]
    return array([dx,dy])


print('\n Ejericio 4 - Euler ejemplo \n')

a=0.
b=20.
N=20
y0=array([80,30])

tini = perf_counter()

(t,y) = eulerSis(a,b,f4,N,y0)

tfin=perf_counter()

# Dibujamos las soluciones
figure('Figura 4 - Euler ejemplo')
subplot(121)
plot(t, y[0,:], t, y[1,:]) # dibuja la solucion aproximada
xlabel('t')
ylabel('x,y')
legend(['Presa', 'Depredador'])

subplot(122)
plot(y[0,:], y[1,:])
xlabel('x')
ylabel('y')
legend(['Trayectorias'])

grid(False)

show()

print('-----')
print('Tiempo CPU: ', str(tfin-tini))
print('Paso de malla: ', str((b-a)/N))
print('-----')


print('\n Ejericio 4 - Euler Varios \n')

a = 0.
b = 20.
y0=array([80,30])


Nvarios = [20,40,80,160, 320, 640]

figure('Figura 4 - Euler Varios')

for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    
    (t, y) = eulerSis(a, b, f4, N, y0) # llamada al metodo de Euler para sistemas
    
    tfin=perf_counter()         #parar contador de tiempo
    
    # Dibujamos las soluciones
    plot(y[0,:], y[1,:]) # dibuja las trayectorias aproximadas

    
    # Resultados
    print('-----')
    print('Tiempo CPU: ',tfin-tini)
    print('Paso de malla: ', (b-a)/N)
    print('-----')

    
xlabel('x')
ylabel('y')
legend(['N=' + str(N) for N in Nvarios])
grid(False)

show()

###############################################################################

print('\n Ejericio 4 - RK4 \n')

def rk4Sis(a, b, fun, N, y0):
    """Implementacion del metodo de RK4 en el intervalo [a, b]
    usando N particiones y condicion inicial y0"""
    
    h = (b-a)/N # paso de malla
    t = zeros(N+1) # inicializacion del vector de nodos
    y = zeros([len(y0), N+1]) # inicializacion del vector de resultados
    t[0] = a # nodo inicial
    y[:, 0] = y0 # valor inicial

    # Metodo de RK4
    for k in range(N):
        t[k+1] = t[k]+h
        k1=fun(t[k],y[:,k])
        k2=fun(t[k] + 0.5*h,y[:,k] + 0.5*h*k1)
        k3=fun(t[k] + 0.5*h,y[:,k] + 0.5*h*k2)
        k4=fun(t[k+1], y[:,k] + h*k3)
        y[:,k+1] = y[:,k]+(h/6)*(k1 + 2*k2 + 2*k3 + k4)
        
    return (t, y)

print('\n Ejemplo de uso del método RK4 para sistemas \n')

def f4(t,y):
    """Función que define el sistema diferencial"""
    dx=0.25*y[0]-0.01*y[0]*y[1]
    dy=-y[1]+0.01*y[0]*y[1]
    return array([dx,dy])


print('\n Ejericio 4 - RK4 ejemplo \n')

a=0.
b=20.
N=20
y0=array([80,30])

tini = perf_counter()

(t,y) = rk4Sis(a,b,f4,N,y0)

tfin=perf_counter()

# Dibujamos las soluciones
figure('Figura 4 - RK4 ejemplo')
subplot(121)
plot(t, y[0,:], t, y[1,:]) # dibuja la solucion aproximada
xlabel('t')
ylabel('x,y')
legend(['Presa', 'Depredador'])

subplot(122)
plot(y[0,:], y[1,:])
xlabel('x')
ylabel('y')
legend(['Trayectorias'])

grid(False)

show()

print('-----')
print('Tiempo CPU: ', str(tfin-tini))
print('Paso de malla: ', str((b-a)/N))
print('-----')


###############################################################################

print('\n Ejericio 4 - RK4 Varios \n')

a = 0.
b = 20.
y0=array([80,30])


Nvarios = [20,40,80,160, 320, 640]

figure('Figura 4 - RK4 Varios')

for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    
    (t, y) = rk4Sis(a, b, f4, N, y0) # llamada al metodo de Euler para sistemas
    
    tfin=perf_counter()         #parar contador de tiempo
    
    # Dibujamos las soluciones
    plot(y[0,:], y[1,:]) # dibuja las trayectorias aproximadas

    
    # Resultados
    print('-----')
    print('Tiempo CPU: ',tfin-tini)
    print('Paso de malla: ', (b-a)/N)
    print('-----')

    
xlabel('x')
ylabel('y')
legend(['N=' + str(N) for N in Nvarios])
grid(False)

show()

###############################################################################

print('\n Ejericio 4 - MPM \n')  

def mpmSis(a, b, fun, N, y0):
    """Implementacion del metodo de MPM en el intervalo [a, b]
    usando N particiones y condicion inicial y0"""
    
    h = (b-a)/N # paso de malla
    t = zeros(N+1) # inicializacion del vector de nodos
    y = zeros([len(y0), N+1]) # inicializacion del vector de resultados
    t[0] = a # nodo inicial
    y[:, 0] = y0 # valor inicial

    # Metodo MPM
    for k in range(N):
        ykmedio= y[:,k] + h/2 *fun(t[k],y[:,k])
        y[:,k+1] = y[:,k]+h*fun(t[k]+h/2, ykmedio)
        t[k+1] = t[k]+h
        
    return (t, y)

print('\n Ejemplo de uso del método MPM para sistemas \n')

def f4(t,y):
    """Función que define el sistema diferencial"""
    dx=0.25*y[0]-0.01*y[0]*y[1]
    dy=-y[1]+0.01*y[0]*y[1]
    return array([dx,dy])


print('\n Ejericio 4 - MPM ejemplo \n')

a=0.
b=20.
N=20
y0=array([80,30])

tini = perf_counter()

(t,y) = mpmSis(a,b,f4,N,y0)

tfin=perf_counter()

# Dibujamos las soluciones
figure('Figura 4 - MPM ejemplo')
subplot(121)
plot(t, y[0,:], t, y[1,:]) # dibuja la solucion aproximada
xlabel('t')
ylabel('x,y')
legend(['Presa', 'Depredador'])

subplot(122)
plot(y[0,:], y[1,:])
xlabel('x')
ylabel('y')
legend(['Trayectorias'])

grid(False)

show()

print('-----')
print('Tiempo CPU: ', str(tfin-tini))
print('Paso de malla: ', str((b-a)/N))
print('-----')


###############################################################################

print('\n Ejericio 4 - MPM Varios \n')

a = 0.
b = 20.
y0=array([80,30])


Nvarios = [20,40,80,160, 320, 640]

figure('Figura 4 - MPM Varios')

for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    
    (t, y) = mpmSis(a, b, f4, N, y0) # llamada al metodo de Euler para sistemas
    
    tfin=perf_counter()         #parar contador de tiempo
    
    # Dibujamos las soluciones
    plot(y[0,:], y[1,:]) # dibuja las trayectorias aproximadas

    
    # Resultados
    print('-----')
    print('Tiempo CPU: ',tfin-tini)
    print('Paso de malla: ', (b-a)/N)
    print('-----')

    
xlabel('x')
ylabel('y')
legend(['N=' + str(N) for N in Nvarios])
grid(False)

show()

###############################################################################


print('\n Ejericio 4 - Heun \n')  

def heunSis(a, b, fun, N, y0):
    """Implementacion del metodo de Heun en el intervalo [a, b]
    usando N particiones y condicion inicial y0"""
    
    h = (b-a)/N # paso de malla
    t = zeros(N+1) # inicializacion del vector de nodos
    y = zeros([len(y0), N+1]) # inicializacion del vector de resultados
    t[0] = a # nodo inicial
    y[:, 0] = y0 # valor inicial

    # Metodo de Heun
    for k in range(N):
        t[k+1] = t[k]+h
        fk=fun(t[k],y[:,k])
        ykmas1p = y[:,k]+ h*fk
        y[:,k+1] = y[:,k]+0.5*h*(fk + fun(t[k+1],ykmas1p))
        
    return (t, y)

print('\n Ejemplo de uso del método de Heun para sistemas \n')

def f4(t,y):
    """Función que define el sistema diferencial"""
    dx=0.25*y[0]-0.01*y[0]*y[1]
    dy=-y[1]+0.01*y[0]*y[1]
    return array([dx,dy])


print('\n Ejericio 4 - Heun ejemplo \n')

a=0.
b=20.
N=20
y0=array([80,30])

tini = perf_counter()

(t,y) = heunSis(a,b,f4,N,y0)

tfin=perf_counter()

# Dibujamos las soluciones
figure('Figura 4 - Heun ejemplo')
subplot(121)
plot(t, y[0,:], t, y[1,:]) # dibuja la solucion aproximada
xlabel('t')
ylabel('x,y')
legend(['Presa', 'Depredador'])

subplot(122)
plot(y[0,:], y[1,:])
xlabel('x')
ylabel('y')
legend(['Trayectorias'])

grid(False)

show()

print('-----')
print('Tiempo CPU: ', str(tfin-tini))
print('Paso de malla: ', str((b-a)/N))
print('-----')


###############################################################################

print('\n Ejericio 4 - Heun Varios \n')

a = 0.
b = 20.
y0=array([80,30])


Nvarios = [20,40,80,160, 320, 640]

figure('Figura 4 - Heun Varios')

for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    
    (t, y) = heunSis(a, b, f4, N, y0) # llamada al metodo de Euler para sistemas
    
    tfin=perf_counter()         #parar contador de tiempo
    
    # Dibujamos las soluciones
    plot(y[0,:], y[1,:]) # dibuja las trayectorias aproximadas

    
    # Resultados
    print('-----')
    print('Tiempo CPU: ',tfin-tini)
    print('Paso de malla: ', (b-a)/N)
    print('-----')

    
xlabel('x')
ylabel('y')
legend(['N=' + str(N) for N in Nvarios])
grid(False)

show()

###############################################################################



print('\n Ejericio 5 \n')



def f5(t,y):
    """Función que define el sistema diferencial"""
    f0=y[1]
    f1= -20.*y[1] - 101.*y[0]
    return array([f0,f1])

def exacta5(t):
    """Solucion exacta del problema de valor inicial"""
    return exp(-10*t)*cos(t)


print('\n Ejericio 5 - Euler \n')

a=0.
b=7.
N=20
y0=array([1,-10])


Nvarios = [20,40,80,160, 320, 640]

figure('Figura 5 - Euler')

for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    
    (t, y) = eulerSis(a, b, f5, N, y0) # llamada al metodo de Euler para sistemas
    
    tfin=perf_counter()         #parar contador de tiempo
    
    # Dibujamos las soluciones
    plot(t, y[0,:]) # dibuja las trayectorias aproximadas
    
    #Calculamos la solución exacta
    ye = exacta5(t)
    
    
    #Calculamos el error cometido
    error = max(abs(y[0,:]-ye))

    
    # Resultados
    print('-----')
    print('Tiempo CPU: ',tfin-tini)
    print('Error: '+ str(error))
    print('Paso de malla: ', (b-a)/N)
    print('-----')

plot(t, ye, 'k')
xlabel('t')
ylabel('y')
leyenda = ['N=' + str(N) for N in Nvarios]
leyenda.append('Exacta')
legend(leyenda)
grid(False)

show()

###############################################################################

print('\n Ejericio 5 - RK4 \n')

a=0.
b=7.
N=20
y0=array([1,-10])


Nvarios = [20,40,80,160, 320, 640]

figure('Figura 5 - RK4')

for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    
    (t, y) = rk4Sis(a, b, f5, N, y0) # llamada al metodo de Euler para sistemas
    
    tfin=perf_counter()         #parar contador de tiempo
    
    # Dibujamos las soluciones
    plot(t, y[0,:]) # dibuja las trayectorias aproximadas
    
    #Calculamos la solución exacta
    ye = exacta5(t)
    
    
    #Calculamos el error cometido
    error = max(abs(y[0,:]-ye))

    
    # Resultados
    print('-----')
    print('Tiempo CPU: ',tfin-tini)
    print('Error: '+ str(error))
    print('Paso de malla: ', (b-a)/N)
    print('-----')

plot(t, ye, 'k')
xlabel('t')
ylabel('y')
leyenda = ['N=' + str(N) for N in Nvarios]
leyenda.append('Exacta')
legend(leyenda)
grid(False)

show()

###############################################################################

print('\n Ejercicio 6 \n')

def rk4Sis(a, b, fun, N, y0):
    """Implementacion del metodo de RK4 en el intervalo [a, b]
    usando N particiones y condicion inicial y0"""
    
    h = (b-a)/N # paso de malla
    t = zeros(N+1) # inicializacion del vector de nodos
    y = zeros([len(y0), N+1]) # inicializacion del vector de resultados
    t[0] = a # nodo inicial
    y[:, 0] = y0 # valor inicial

    # Metodo de RK4
    for k in range(N):
        t[k+1] = t[k]+h
        k1=fun(t[k],y[:,k])
        k2=fun(t[k] + 0.5*h,y[:,k] + 0.5*h*k1)
        k3=fun(t[k] + 0.5*h,y[:,k] + 0.5*h*k2)
        k4=fun(t[k+1], y[:,k] + h*k3)
        y[:,k+1] = y[:,k]+(h/6)*(k1 + 2*k2 + 2*k3 + k4)
        
    return (t, y)

print('\n Uso del método RK4 para sistemas, en este caso el sistema del ejercicio 6\n')

#Empezamos definiendo el sistema con el que vamos a trabajar, asi como las variables que intervienen en ellos
#y[0]: numero de individuos susceptibles
#y[1]: numero de individuos infectados
#y[2]: numero de individuos en aislamiento, recuperacion(luego inmunidad) o muerte

#Intervienen las siguientes constantes tambien
#k1 = tasa de contagios
#k2 = tasa de eliminacion de infectados por diversas causas posibles
#k3 = tasa de mortalidad de individuos sanos

print('\n Ejercicio 6 - Apartado a \n')

k1= 2.e-3
k2= 5.e-3
k3= 1.e-5

def f6(t,y):
    f1 = -k1*y[0]*y[1] -k3*y[0]
    f2 = k1*y[0]*y[1] -k2*y[1]
    f3 = k3*y[0] +k2*y[1]
    return array([f1,f2,f3])

#Damos los datos necesarios para resolver el sistema de manera aproximada mediante metodo RK4
y0=array([99,1,0])
h=0.05
a=0
b=60
N=int((b-a)/h)

tini = perf_counter()

(t,y) = rk4Sis(a,b,f6,N,y0)

tfin=perf_counter()

# Dibujamos las soluciones
figure('Figura 6.a - RK4 SIR')
subplot(121)
plot(t, y[0,:], t, y[1,:], t, y[2,:]) # dibuja la solucion aproximada
xlabel('t')
ylabel('x,y,z')
legend(['Susceptibles', 'Infectados', 'Otros'])

grid(False)

show()

print("La solucion aproximada por metodo rk4 es susceptibles,",y[0],"infectados,",y[1],"sanos",y[2])

###############################################################################

print('\n Ejercicio 6 - Apartado b \n')

k1= 2.e-3
k2= 5.e-2
k3= 1.e-5

def f6(t,y):
    f1 = -k1*y[0]*y[1] -k3*y[0]
    f2 = k1*y[0]*y[1] -k2*y[1]
    f3 = k3*y[0] +k2*y[1]
    return array([f1,f2,f3])

#Damos los datos necesarios para resolver el sistema de manera aproximada mediante metodo RK4
y0=array([99,1,0])
h=0.05
a=0
b=60
N=int((b-a)/h)

tini = perf_counter()

(t,y) = rk4Sis(a,b,f6,N,y0)

tfin=perf_counter()

# Dibujamos las soluciones
figure('Figura 6.b - RK4 SIR')
subplot(121)
plot(t, y[0,:], t, y[1,:], t, y[2,:]) # dibuja la solucion aproximada
xlabel('t')
ylabel('x,y,z')
legend(['Susceptibles', 'Infectados', 'Otros'])

grid(False)

show()

print("La solucion aproximada por metodo rk4 es susceptibles,",y[0],"infectados,",y[1],"sanos",y[2])


###############################################################################

print('\n Ejercicio 6 - Apartado c \n')

k1= 2.e-3
k2= 5.e-3
k3= 1.e-5
k4=0.5*k2

def f6(t,y):
    f1 = -k1*y[0]*y[1] -k3*y[0] + k4*y[1]
    f2 = k1*y[0]*y[1] -k2*y[1] - k4*y[1]
    f3 = k3*y[0] +k2*y[1]
    return array([f1,f2,f3])

#Damos los datos necesarios para resolver el sistema de manera aproximada mediante metodo RK4
y0=array([99,1,0])
h=0.05
a=0
b=60
N=int((b-a)/h)

tini = perf_counter()

(t,y) = rk4Sis(a,b,f6,N,y0)

tfin=perf_counter()

# Dibujamos las soluciones
figure('Figura 6.c - RK4 SIR')
subplot(121)
plot(t, y[0,:], t, y[1,:], t, y[2,:]) # dibuja la solucion aproximada
xlabel('t')
ylabel('x,y,z')
legend(['Susceptibles', 'Infectados', 'Otros'])

grid(False)

show()

print("La solucion aproximada por metodo rk4 es susceptibles,",y[0],"infectados,",y[1],"sanos",y[2])


###############################################################################


print('\n Ejericio 7 previo \n')


def rk4Sis(a, b, fun, N, y0):
    """Implementacion del metodo de RK4 en el intervalo [a, b]
    usando N particiones y condicion inicial y0"""
    
    h = (b-a)/N # paso de malla
    t = zeros(N+1) # inicializacion del vector de nodos
    y = zeros([len(y0), N+1]) # inicializacion del vector de resultados
    t[0] = a # nodo inicial
    y[:, 0] = y0 # valor inicial

    # Metodo de RK4
    for k in range(N):
        t[k+1] = t[k]+h
        k1=fun(t[k],y[:,k])
        k2=fun(t[k] + 0.5*h,y[:,k] + 0.5*h*k1)
        k3=fun(t[k] + 0.5*h,y[:,k] + 0.5*h*k2)
        k4=fun(t[k+1], y[:,k] + h*k3)
        y[:,k+1] = y[:,k]+(h/6)*(k1 + 2*k2 + 2*k3 + k4)
        
    return (t, y)

print('\n Uso del método RK4 para sistemas, en este caso el sistema del ejercicio 7\n')

g = 9.81
M = 7.5
alpha = 0.02


def f7(t,y):
    """Función que define el sistema diferencial"""
    #y[0] = z, y[1] = v, y[2] = mf
    
    m = M + y[2]
    T = T0*(y[2]>0)
    
    f1 = y[1]
    f2 = -g + T/m - (C/m)*y[1]*abs(y[1]) + alpha*(T/m)*y[1]
    f3 = -alpha*T
    return array([f1,f2,f3])


#Datos del problema
a=0.
b=20.
h = 0.05
N=int((b-a)/h)
y0=array([0,50,7.5])


#Usamos el método rk4Sis

#Sin motor ni rozamiento
T0 = 0
C=0.

tini = perf_counter()

(ti,yi) = rk4Sis(a,b,f7,N,y0)

tfin = perf_counter()


#Sin motor y con rozamiento
T0 = 0
C=0.02

tini = perf_counter()

(tii,yii) = rk4Sis(a,b,f7,N,y0)

tfin = perf_counter()


#Con motor y rozamiento
T0 = 50
C=0.02

tini = perf_counter()

(tiii,yiii) = rk4Sis(a,b,f7,N,y0)

tfin = perf_counter()



# Dibujamos las soluciones
figure('Figura 7 previo - Cohete (con valores negativos)')
plot(ti, yi[0], tii, yii[0], tiii, yiii[0]) # dibuja la solucion aproximada
xlabel('t')
ylabel('z')
legend(['T0 = 0, C = 0','T0 = 0, C = 0.02','T0 = 50, C = 0.02'])

grid(False)

show()

###############################################################################

print('\n Ejericio 7 \n')

#Implementamos un método Runge-Kutta 4 con un bucle while

print('\n Ejercicio 7 - Apartado a')


def rk4SisW(a, fun, h, y0):
    
    
    t = zeros(1) # inicializacion del vector de nodos
    y = zeros([len(y0), 1]) # inicializacion del vector de resultados
    t[0] = a # nodo inicial
    y[:, 0] = y0 # valor inicial

    # Metodo de RK4
    k=0
    
    while (y[0,k]>=0):
        
        k1=fun(t[k],y[:,k])
        k2=fun(t[k] + 0.5*h,y[:,k] + 0.5*h*k1)
        k3=fun(t[k] + 0.5*h,y[:,k] + 0.5*h*k2)
        k4=fun(t[k] + h, y[:,k] + h*k3)
        
        ykmas1 = y[:,k]+(h/6)*(k1 + 2*k2 + 2*k3 + k4)
        
        t = append(t, t[k]+h)
        y = column_stack((y, ykmas1))
        
        
        k = k+1
        
        
        
    return (t, y)
#print(y)
print('\n Uso del método RK4 para sistemas, en este caso el sistema del ejercicio 7\n')




g = 9.81
M = 7.5
alpha = 0.02


def f7(t,y):
    """Función que define el sistema diferencial"""
    #y[0] = z, y[1] = v, y[2] = mf
    
    m = M + y[2]
    T = T0*(y[2]>0)
    
    f1 = y[1]
    f2 = -g + T/m - (C/m)*y[1]*abs(y[1]) + alpha*(T/m)*y[1]
    f3 = -alpha*T
    return array([f1,f2,f3])


print('\n Ejercicio 7 - Apartado b')

#Datos del problema
a=0.
h = 0.05
y0=array([0,50,7.5])


#Usamos el método rk4Sis

#Sin motor ni rozamiento
T0 = 0
C=0.

tini = perf_counter()

(ti,yi) = rk4SisW(a,f7,h,y0)

tfin = perf_counter()


#Sin motor y con rozamiento
T0 = 0
C=0.02

tini = perf_counter()

(tii,yii) = rk4SisW(a,f7,h,y0)

tfin = perf_counter()


#Con motor y rozamiento
T0 = 50
C=0.02

tini = perf_counter()

(tiii,yiii) = rk4SisW(a,f7,h,y0)

tfin = perf_counter()

print('\n Ejercicio 7 - Apartado c')

# Dibujamos las soluciones
figure('Figura 7 - Cohete')
plot(ti, yi[0], tii, yii[0], tiii, yiii[0]) # dibuja la solucion aproximada
xlabel('t')
ylabel('z')
legend(['T0 = 0, C = 0','T0 = 0, C = 0.02','T0 = 50, C = 0.02'])

grid(False)

show()

#Calculamos la altura máxima en cada caso

print('C = 0, T0 = 0')
print('Máxima altura: ' + str(max(yi[0,:])))
print('Tiempo de caída: ' + str(ti[-1]))
print('\n')


print('C = 0.02, T0 = 0')
print('Máxima altura: ' + str(max(yii[0,:])))
print('Tiempo de caída: ' + str(tii[-1]))
print('\n')


print('C = 00.2, T0 = 50')
print('Máxima altura: ' + str(max(yiii[0,:])))
print('Tiempo de caída: ' + str(tiii[-1]))
print('\n')


figure('Figura 7 - Final')
plot(tiii, yiii[2]) 
xlabel('t')
ylabel('mf')
legend(['T0 = 50, C = 0.02'])

grid(False)

show()

