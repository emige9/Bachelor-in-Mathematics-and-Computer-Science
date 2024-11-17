# -*- coding: utf-8 -*-
"""
Created on Mon Apr 15 17:50:31 2024

@author: Usuario
"""

from pylab import *
from time import perf_counter

print('\n Práctica 1 - Bis \n')

###############################################################################

print('Ejercicio 1')

def f1(t,y):
    return -y + 2*sin(t)

def exacta1(t):
    return (pi + 1)*exp(-t) +sin(t) -cos(t)

# Datos del problema
a = 0. # extremo inferior del intervalo
b = 10. # extremo superior del intervalo
N = 50 # numero de particiones
y0 = pi # condicion inicial

###############################################################################
# Lista de métodos que vamos a usar
###############################################################################


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


def heun(a, b, fun, N, y0):
    """Implementacion del metodo de Heun en el intervalo [a, b]
    usando N particiones y condicion inicial y0"""
    
    h = (b-a)/N # paso de malla
    t = zeros(N+1) # inicializacion del vector de nodos
    y = zeros(N+1) # inicializacion del vector de resultados
    t[0] = a # nodo inicial
    y[0] = y0 # valor inicial

    # Metodo de Heun
    for k in range(N):
        t[k+1] = t[k]+h
        fk=fun(t[k],y[k])
        ykmas1p = y[k]+ h*fk
        y[k+1] = y[k]+0.5*h*(fk + fun(t[k+1],ykmas1p))
        
    return (t, y)


def mpm(a, b, fun, N, y0):
    """Implementacion del metodo de PM en el intervalo [a, b]
    usando N particiones y condicion inicial y0"""
    
    h = (b-a)/N # paso de malla
    t = zeros(N+1) # inicializacion del vector de nodos
    y = zeros(N+1) # inicializacion del vector de resultados
    t[0] = a # nodo inicial
    y[0] = y0 # valor inicial

    # Metodo MPM
    for k in range(N):
        ykmedio= y[k] + h/2 *fun(t[k],y[k])
        y[k+1] = y[k]+h*fun(t[k]+h/2, ykmedio)
        t[k+1] = t[k]+h
        
    return (t, y)


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

###############################################################################

print('Método de Euler')


tini = perf_counter()       #iniciar contador de tiempo

(t, y) = euler(a, b, f1, N, y0) # llamada al metodo de Euler

tfin=perf_counter()         #parar contador de tiempo

ye = exacta1(t) # calculo de la solucion exacta

# Dibujamos las soluciones
figure('Figura 1 - Euler')
plot(t, y, '-*') # dibuja la solucion aproximada
plot(t, ye, 'k') # dibuja la solucion exacta
xlabel('t')
ylabel('y')
legend(['Euler', 'exacta'])
grid(True)
show()

# Calculo del error cometido
error = max(abs(y-ye))

# Resultados
print('-----')
print('Tiempo CPU: ', str(tfin-tini))
print('Error: ', str(error))
print('Paso de malla: ', str((b-a)/N))
print('-----')

###############################################################################

print('Método de Heun')

tini = perf_counter()       #iniciar contador de tiempo

(t, y) = heun(a, b, f1, N, y0) # llamada al metodo de Euler

tfin=perf_counter()         #parar contador de tiempo

ye = exacta1(t) # calculo de la solucion exacta

# Dibujamos las soluciones
figure('Figura 2 - Heun')
plot(t, y, '-*') # dibuja la solucion aproximada
plot(t, ye, 'k') # dibuja la solucion exacta
xlabel('t')
ylabel('y')
legend(['Heun', 'exacta'])
grid(True)
show()

# Calculo del error cometido
error = max(abs(y-ye))

# Resultados
print('-----')
print('Tiempo CPU: ', str(tfin-tini))
print('Error: ', str(error))
print('Paso de malla: ', str((b-a)/N))
print('-----')

###############################################################################

print('Método del punto medio')

tini = perf_counter()       #iniciar contador de tiempo

(t, y) = mpm(a, b, f1, N, y0) # llamada al metodo de Euler

tfin=perf_counter()         #parar contador de tiempo

ye = exacta1(t) # calculo de la solucion exacta

# Dibujamos las soluciones
figure('Figura 3 - MPM')
plot(t, y, '-*') # dibuja la solucion aproximada
plot(t, ye, 'k') # dibuja la solucion exacta
xlabel('t')
ylabel('y')
legend(['MPM', 'exacta'])
grid(True)
show()

# Calculo del error cometido
error = max(abs(y-ye))

# Resultados
print('-----')
print('Tiempo CPU: ', str(tfin-tini))
print('Error: ', str(error))
print('Paso de malla: ', str((b-a)/N))
print('-----')

###############################################################################

print('Método RK4')

tini = perf_counter()       #iniciar contador de tiempo

(t, y) = rk4(a, b, f1, N, y0) # llamada al metodo de Euler

tfin=perf_counter()         #parar contador de tiempo

ye = exacta1(t) # calculo de la solucion exacta

# Dibujamos las soluciones
figure('Figura 4 - RK4')
plot(t, y, '-*') # dibuja la solucion aproximada
plot(t, ye, 'k') # dibuja la solucion exacta
xlabel('t')
ylabel('y')
legend(['RK4', 'exacta'])
grid(True)
show()

# Calculo del error cometido
error = max(abs(y-ye))

# Resultados
print('-----')
print('Tiempo CPU: ', str(tfin-tini))
print('Error: ', str(error))
print('Paso de malla: ', str((b-a)/N))
print('-----')

###############################################################################




print('Ejercicio 2')

def f2(t,y):
    f1 = 3*y[0] - 2*y[1]
    f2 = -y[0] + 3*y[1] - 2*y[2]
    f3 = -y[1] + 3*y[2]
    return array([f1,f2,f3])

def exacta2(t):
    f1e = -(1/4)*exp(5*t) + (3/2)*exp(3*t) - (1/4)*exp(t)
    f2e = (1/4)*exp(5*t) - (1/4)*exp(t)
    f3e = -(1/8)*exp(5*t) - (3/4)*exp(3*t) - (1/8)*exp(t)
    return array([f1e,f2e,f3e])

# Datos del problema
a = 0. # extremo inferior del intervalo
b = 1. # extremo superior del intervalo
N = 50 # numero de particiones
y0 = array([1,0,-1]) # condicion inicial

###############################################################################
# Lista de métodos que vamos a usar
###############################################################################

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


###############################################################################

print('Método de Euler')


tini = perf_counter()

(t,y) = eulerSis(a,b,f2,N,y0)

tfin=perf_counter()

ye = exacta2(t) # calculo de la solucion exacta

# Dibujamos las soluciones
figure('Figura 5 - Euler sistemas')
subplot(131)
plot(t, y[0,:],'-*', t, ye[0,:], 'k') # dibuja la solucion aproximada
xlabel('t')
ylabel('x')
legend(['Euler x', 'exacta x'])

subplot(132)
plot(t, y[1,:],'-*', t, ye[1,:], 'k') # dibuja la solucion aproximada
xlabel('t')
ylabel('y')
legend(['Euler y', 'exacta y'])

subplot(133)
plot(t, y[2,:],'-*', t, ye[2,:], 'k') # dibuja la solucion aproximada
xlabel('t')
ylabel('z')
legend(['Euler z', 'exacta z'])

grid(False)

show()

# Calculo del error cometido
errorx = max(abs(y[0]-ye[0]))
errory = max(abs(y[1]-ye[1]))
errorz = max(abs(y[2]-ye[2]))

# Resultados
print('-----')
print('Tiempo CPU: ', str(tfin-tini))
print('Error x: ', str(errorx))
print('Error y: ', str(errory))
print('Error z: ', str(errorz))
print('Paso de malla: ', str((b-a)/N))
print('-----')
###############################################################################

print('Método de Heun')

tini = perf_counter()

(t,y) = heunSis(a,b,f2,N,y0)

tfin=perf_counter()

ye = exacta2(t) # calculo de la solucion exacta

# Dibujamos las soluciones
figure('Figura 6 - Heun sistemas')
subplot(131)
plot(t, y[0,:],'-*', t, ye[0,:], 'k') # dibuja la solucion aproximada
xlabel('t')
ylabel('x')
legend(['Heun x', 'exacta x'])

subplot(132)
plot(t, y[1,:],'-*', t, ye[1,:], 'k') # dibuja la solucion aproximada
xlabel('t')
ylabel('y')
legend(['Heun y', 'exacta y'])

subplot(133)
plot(t, y[2,:],'-*', t, ye[2,:], 'k') # dibuja la solucion aproximada
xlabel('t')
ylabel('z')
legend(['Heun z', 'exacta z'])

grid(False)

show()

# Calculo del error cometido
errorx = max(abs(y[0]-ye[0]))
errory = max(abs(y[1]-ye[1]))
errorz = max(abs(y[2]-ye[2]))

# Resultados
print('-----')
print('Tiempo CPU: ', str(tfin-tini))
print('Error x: ', str(errorx))
print('Error y: ', str(errory))
print('Error z: ', str(errorz))
print('Paso de malla: ', str((b-a)/N))
print('-----')

###############################################################################

print('Método del punto medio')

tini = perf_counter()

(t,y) = mpmSis(a,b,f2,N,y0)

tfin=perf_counter()

ye = exacta2(t) # calculo de la solucion exacta

# Dibujamos las soluciones
figure('Figura 7 - MPM sistemas')
subplot(131)
plot(t, y[0,:],'-*', t, ye[0,:], 'k') # dibuja la solucion aproximada
xlabel('t')
ylabel('x')
legend(['MPM x', 'exacta x'])

subplot(132)
plot(t, y[1,:],'-*', t, ye[1,:], 'k') # dibuja la solucion aproximada
xlabel('t')
ylabel('y')
legend(['MPM y', 'exacta y'])

subplot(133)
plot(t, y[2,:],'-*', t, ye[2,:], 'k') # dibuja la solucion aproximada
xlabel('t')
ylabel('z')
legend(['MPM z', 'exacta z'])

grid(False)

show()

# Calculo del error cometido
errorx = max(abs(y[0]-ye[0]))
errory = max(abs(y[1]-ye[1]))
errorz = max(abs(y[2]-ye[2]))

# Resultados
print('-----')
print('Tiempo CPU: ', str(tfin-tini))
print('Error x: ', str(errorx))
print('Error y: ', str(errory))
print('Error z: ', str(errorz))
print('Paso de malla: ', str((b-a)/N))
print('-----')

###############################################################################

print('Método RK4')

tini = perf_counter()

(t,y) = rk4Sis(a,b,f2,N,y0)

tfin=perf_counter()

ye = exacta2(t) # calculo de la solucion exacta

# Dibujamos las soluciones
figure('Figura 8 - RK4 sistemas')
subplot(131)
plot(t, y[0,:],'-*', t, ye[0,:], 'k') # dibuja la solucion aproximada
xlabel('t')
ylabel('x')
legend(['RK4 x', 'exacta x'])

subplot(132)
plot(t, y[1,:],'-*', t, ye[1,:], 'k') # dibuja la solucion aproximada
xlabel('t')
ylabel('y')
legend(['RK4 y', 'exacta y'])

subplot(133)
plot(t, y[2,:],'-*', t, ye[2,:], 'k') # dibuja la solucion aproximada
xlabel('t')
ylabel('z')
legend(['RK4 z', 'exacta z'])

grid(False)

show()

# Calculo del error cometido
errorx = max(abs(y[0]-ye[0]))
errory = max(abs(y[1]-ye[1]))
errorz = max(abs(y[2]-ye[2]))

# Resultados
print('-----')
print('Tiempo CPU: ', str(tfin-tini))
print('Error x: ', str(errorx))
print('Error y: ', str(errory))
print('Error z: ', str(errorz))
print('Paso de malla: ', str((b-a)/N))
print('-----')

###############################################################################






