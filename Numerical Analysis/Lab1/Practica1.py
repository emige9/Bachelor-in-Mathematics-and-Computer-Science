# -*- coding: utf-8 -*-
"""
Created on Sun Mar  8 12:09:15 2020

@author: Usuario
"""

from pylab import *
from time import perf_counter

print('\n Práctica 1 \n')

###############################################################################


print('\n Ejercicio 1 - Él método de Euler \n')

# En este apartado vamos a realizar una implementación del método de Euler
# en Python para resolver el problema de valor inicial

# (P) { y'(t) = f(t,y); y(0) = 1}

def f(t, y):
    """Funcion que define la ecuacion diferencial"""
    return 0.5*(t**2 - y)

# La solución exacta del problema de Cauchy planteado es:

def exacta(t):
    """Solucion exacta del problema de valor inicial"""
    return t**2 - 4*t + 8 - 7*exp(-0.5*t)


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

###############################################################################

print('\n Primera prueba con Euler \n')

# Datos del problema
a = 0. # extremo inferior del intervalo
b = 10. # extremo superior del intervalo
N = 20 # numero de particiones
y0 = 1. # condicion inicial

tini = perf_counter()       #iniciar contador de tiempo

(t, y) = euler(a, b, f, N, y0) # llamada al metodo de Euler

tfin=perf_counter()         #parar contador de tiempo

ye = exacta(t) # calculo de la solucion exacta

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

print('\n Ejericio 1.a \n')

# Compruebe que, para el problema considerado, el orden del método de Euler es uno. Para
# ello, realice una serie de ejecuciones del programa con N = 10, 20, 40, 80, 160. Obtenga en
# cada caso el error eN que se comete y calcule los cocientes eN/e2N, N =10, 20, 40, 80. Dibuje
# en una gráfica las aproximaciones obtenidas con los distintos valores de N junto con la
# solución exacta.

Nvarios = [10,20,40,80,160]

figure('Figura 1.a - Euler')
for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    (t, y) = euler(a, b, f, N, y0) # llamada al metodo de Euler
    tfin=perf_counter()         #parar contador de tiempo
    ye = exacta(t) # calculo de la solucion exacta
    # Dibujamos las soluciones
    plot(t, y, '-*') # dibuja la solucion aproximada
    # Calculo del error cometido
    error = max(abs(y-ye))
    
    # Resultados
    print('-----')
    print('Tiempo CPU: ',tfin-tini)
    print('Error: ', error)
    
    if N!=Nvarios[0]:
        print('Cociente de errores: ', errorold/error)
        
    print('Paso de malla: ', (b-a)/N)
    print('-----')
    errorold = error
    
plot(t, ye, 'k') # dibuja la solucion exacta
xlabel('t')
ylabel('y')
leyenda=['N=' + str(N) for N in Nvarios]
leyenda.append('Exacta')
legend(leyenda)
grid(True)
show()

###############################################################################

print('\n Ejericio 1.b \n')

# En un depósito de 20 l, que inicialmente está lleno de agua pura, empieza a entrar, desde el
# instante t = 0, agua con una concentración de 3 grs/l de sal a razón de 2 l por segundo. Al
# mismo tiempo, empieza a salir agua con la misma velocidad. En clase se vio que la cantidad
# de sal S(t) que hay en el depósito en el instante t sigue aproximadamente la siguiente
# ecuación diferencial:
    
# (Q) { S'(t) = f(t,y); S(0) = 0}

def f1b(t, y):
    """Funcion que define la ecuacion diferencial"""
    return 6 - y/10

# La solución exacta del problema de Cauchy planteado es:
    
def exacta1b(t):
    """Solucion exacta del problema de valor inicial"""
    return 60*(1 - exp(-t/10))

###############################################################################

print('\n Primera prueba con Euler en 1.b \n')

# Datos del problema
a = 0. # extremo inferior del intervalo
b = 20. # extremo superior del intervalo
N = 20 # numero de particiones
y0 = 0. # condicion inicial

tini = perf_counter()       #iniciar contador de tiempo

(t, y) = euler(a, b, f1b, N, y0) # llamada al metodo de Euler

tfin=perf_counter()         #parar contador de tiempo

ye1b = exacta1b(t) # calculo de la solucion exacta

# Dibujamos las soluciones
figure('Figura 1.b - Euler')
plot(t, y, '-*') # dibuja la solucion aproximada
plot(t, ye1b, 'k') # dibuja la solucion exacta
xlabel('t')
ylabel('y')
legend(['Euler', 'exacta'])
grid(True)
show()

# Calculo del error cometido
error = max(abs(y-ye1b))

# Resultados
print('-----')
print('Tiempo CPU: ', str(tfin-tini))
print('Error: ', str(error))
print('Paso de malla: ', str((b-a)/N))
print('-----')

###############################################################################

print('\n Varias pruebas con Euler en 1.b \n')

Nvarios = [10,20,40,80,160]

figure('Figura 1.b - Euler varios')
for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    (t, y) = euler(a, b, f1b, N, y0) # llamada al metodo de Euler
    tfin=perf_counter()         #parar contador de tiempo
    ye1b = exacta1b(t) # calculo de la solucion exacta
    # Dibujamos las soluciones
    plot(t, y, '-*') # dibuja la solucion aproximada
    # Calculo del error cometido
    error = max(abs(y-ye1b))
    
    # Resultados
    print('-----')
    print('Tiempo CPU: ',tfin-tini)
    print('Error: ', error)
    
    if N!=Nvarios[0]:
        print('Cociente de errores: ', errorold/error)
        
    print('Paso de malla: ', (b-a)/N)
    print('-----')
    errorold = error
    
plot(t, ye1b, 'k') # dibuja la solucion exacta
xlabel('t')
ylabel('y')
leyenda=['N=' + str(N) for N in Nvarios]
leyenda.append('Exacta')
legend(leyenda)
grid(True)
show()

###############################################################################

print('\n Ejericio 1.c \n')

# Suponga que, en el problema anterior, a partir del instante t = 0 en vez de salir agua a razón
# de 2 l/seg lo hace con una velocidad de 3 l/seg, permaneciendo iguales todos los demás
# datos del problema. Modifique la ecuación para tener en cuenta esta variación y aplique el
# método de Euler con N = 2000 a la ecuación resultante en el máximo intervalo de tiempo
# en el que tenga sentido la ecuación. Cuál es, aproximadamente, el máximo de la cantidad
# de sal en el depósito? En qué instante de tiempo se alcanza?

# (Q) { S'(t) = f(t,y); S(0) = 0}

def f1c(t, y):
    """Funcion que define la ecuacion diferencial"""
    return 6 - (3*y)/20

# La solución exacta del problema de Cauchy planteado es:
    
def exacta1c(t):
    """Solucion exacta del problema de valor inicial"""
    return 40*(1 - exp(-(3*t)/20))

###############################################################################

print('\n Primera prueba con Euler en 1.c \n')

# Datos del problema
a = 0. # extremo inferior del intervalo
b = 20. # extremo superior del intervalo
N = 2000 # numero de particiones
y0 = 0. # condicion inicial

tini = perf_counter()       #iniciar contador de tiempo

(t, y) = euler(a, b, f1c, N, y0) # llamada al metodo de Euler

tfin=perf_counter()         #parar contador de tiempo

ye1c = exacta1c(t) # calculo de la solucion exacta

# Dibujamos las soluciones
figure('Figura 1.c - Euler')
plot(t, y, '-*') # dibuja la solucion aproximada
plot(t, ye1c, 'k') # dibuja la solucion exacta
xlabel('t')
ylabel('y')
legend(['Euler', 'exacta'])
grid(True)
show()

# Calculo del error cometido
error = max(abs(y-ye1c))

# Resultados
print('-----')
print('Tiempo CPU: ', str(tfin-tini))
print('Error: ', str(error))
print('Paso de malla: ', str((b-a)/N))
print('-----')

#print('Máxima de cantidad de sal en el depósito: ' + str(max()) )



###############################################################################

print('\n Ejericio 2 - Taylor 2 \n')

# Tomando como modelo el programa anterior, escriba la implementación en Python de los métodos
# de Taylor de orden 2 y 3 para el problema de Cauchy (P). Compruebe el orden repitiendo el
# apartado (a) del ejercicio anterior.


def taylor2(a, b, N, y0):
    
    h = (b-a)/N # paso de malla
    t = zeros(N+1) # inicializacion del vector de nodos
    y = zeros(N+1) # inicializacion del vector de resultados
    t[0] = a # nodo inicial
    y[0] = y0 # valor inicial

    # Metodo de Taylor de orden 2
    for k in range(N):
        fk = 0.5*(t[k]**2 - y[k])
        dfk = t[k] - 0.5*fk
        
        y[k+1] = y[k]+h*fk + 0.5*h**2*dfk
        t[k+1] = t[k]+h
    
    return (t, y)

# Datos del problema
a = 0. # extremo inferior del intervalo
b = 10. # extremo superior del intervalo
y0 = 1. # condicion inicial

Nvarios = [10,20,40,80,160]

figure('Figura 2 - Taylor 2')
for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    (t, y) = taylor2(a, b, N, y0) # llamada al metodo de Euler
    tfin=perf_counter()         #parar contador de tiempo
    ye = exacta(t) # calculo de la solucion exacta
    # Dibujamos las soluciones
    plot(t, y, '-*') # dibuja la solucion aproximada
    # Calculo del error cometido
    error = max(abs(y-ye))
    
    # Resultados
    print('-----')
    print('Tiempo CPU: ',tfin-tini)
    print('Error: ', error)
    
    if N!=Nvarios[0]:
        print('Cociente de errores: ', errorold/error)
        
    print('Paso de malla: ', (b-a)/N)
    print('-----')
    errorold = error
    
plot(t, ye, 'k') # dibuja la solucion exacta
xlabel('t')
ylabel('y')
leyenda=['N=' + str(N) for N in Nvarios]
leyenda.append('Exacta')
legend(leyenda)
grid(True)
show()

###############################################################################

print('\n Ejericio 2 - Taylor 3 \n')


def taylor3(a, b, N, y0):
    
    h = (b-a)/N # paso de malla
    t = zeros(N+1) # inicializacion del vector de nodos
    y = zeros(N+1) # inicializacion del vector de resultados
    t[0] = a # nodo inicial
    y[0] = y0 # valor inicial

    # Metodo de Taylor de orden 3
    for k in range(N):
        fk = 0.5*(t[k]**2 - y[k])
        dfk = t[k] - 0.5*fk
        d2fk = 1 - 0.5*dfk
        
        y[k+1] = y[k]+h*fk + 0.5*h**2*dfk + h**3/6*d2fk 
        t[k+1] = t[k]+h
    
    return (t, y)



Nvarios = [10,20,40,80,160]

figure('Figura 2 - Taylor 3')
for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    (t, y) = taylor3(a, b, N, y0) # llamada al metodo de Euler
    tfin=perf_counter()         #parar contador de tiempo
    ye = exacta(t) # calculo de la solucion exacta
    # Dibujamos las soluciones
    plot(t, y, '-*') # dibuja la solucion aproximada
    # Calculo del error cometido
    error = max(abs(y-ye))
    
    # Resultados
    print('-----')
    print('Tiempo CPU: ',tfin-tini)
    print('Error: ', error)
    
    if N!=Nvarios[0]:
        print('Cociente de errores: ', errorold/error)
        
    print('Paso de malla: ', (b-a)/N)
    print('-----')
    errorold = error
    
plot(t, ye, 'k') # dibuja la solucion exacta
xlabel('t')
ylabel('y')
leyenda=['N=' + str(N) for N in Nvarios]
leyenda.append('Exacta')
legend(leyenda)
grid(True)
show()



###############################################################################

print('\n Ejericio 3 \n')

#   Método de Heun

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

print('\n Ejercicio 3.a (Heun) \n')

Nvarios = [10,20,40,80,160]

figure('Figura 3a - Heun')
for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    (t, y) = heun(a, b, f, N, y0) # llamada al metodo de Euler
    tfin=perf_counter()         #parar contador de tiempo
    ye = exacta(t) # calculo de la solucion exacta
    # Dibujamos las soluciones
    plot(t, y, '-*') # dibuja la solucion aproximada
    # Calculo del error cometido
    error = max(abs(y-ye))
    
    # Resultados
    print('-----')
    print('Tiempo CPU: ',tfin-tini)
    print('Error: ', error)
    
    if N!=Nvarios[0]:
        print('Cociente de errores: ', errorold/error)
        
    print('Paso de malla: ', (b-a)/N)
    print('-----')
    errorold = error
    
plot(t, ye, 'k') # dibuja la solucion exacta
xlabel('t')
ylabel('y')
leyenda=['N=' + str(N) for N in Nvarios]
leyenda.append('Exacta')
legend(leyenda)
grid(True)
show()


# Al dividir h por 2, el error se divide aproximadamente por 4
# Esto corresponde al comportamiento de un método de orden 2

###############################################################################

#   Método RK4

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

print('\n Ejercicio 3.b (RK4) \n')

Nvarios = [10,20,40,80,160]

figure('Figura 3b - RK4')
for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    (t, y) = rk4(a, b, f, N, y0) # llamada al metodo de EulerRK4
    tfin=perf_counter()         #parar contador de tiempo
    ye = exacta(t) # calculo de la solucion exacta
    # Dibujamos las soluciones
    plot(t, y, '-*') # dibuja la solucion aproximada
    # Calculo del error cometido
    error = max(abs(y-ye))
    
    # Resultados
    print('-----')
    print('Tiempo CPU: ',tfin-tini)
    print('Error: ', error)
    
    if N!=Nvarios[0]:
        print('Cociente de errores: ', errorold/error)
        
    print('Paso de malla: ', (b-a)/N)
    print('-----')
    errorold = error
    
plot(t, ye, 'k') # dibuja la solucion exacta
xlabel('t')
ylabel('y')
leyenda=['N=' + str(N) for N in Nvarios]
leyenda.append('Exacta')
legend(leyenda)
grid(True)
show()

# Al dividir h por 2, el error se divide aproximadamente por 16
# Esto corresponde al comportamiento de un método de orden 4

###############################################################################

#   Método del punto medio MPM

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

print('\n Ejercicio 3.c (MPM) \n')

Nvarios = [10,20,40,80,160]

figure('Figura 3c - MPM')
for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    (t, y) = mpm(a, b, f, N, y0) # llamada al metodo de EulerRK4
    tfin=perf_counter()         #parar contador de tiempo
    ye = exacta(t) # calculo de la solucion exacta
    # Dibujamos las soluciones
    plot(t, y, '-*') # dibuja la solucion aproximada
    # Calculo del error cometido
    error = max(abs(y-ye))
    
    # Resultados
    print('-----')
    print('Tiempo CPU: ',tfin-tini)
    print('Error: ', error)
    
    if N!=Nvarios[0]:
        print('Cociente de errores: ', errorold/error)
        
    print('Paso de malla: ', (b-a)/N)
    print('-----')
    errorold = error
    
plot(t, ye, 'k') # dibuja la solucion exacta
xlabel('t')
ylabel('y')
leyenda=['N=' + str(N) for N in Nvarios]
leyenda.append('Exacta')
legend(leyenda)
grid(True)
show()

###############################################################################


# PRUEBAS CON ARRAYS

x=[1,2,3]
y=[4,5,6]
x+y

xx=array([1,2,3])
yy=array([4,5,6])
xx+yy

def fsis(t,y):
    fsis1=y[0]*cos(t)
    fsis2=y[1]*sin(t)
    return array([fsis1,fsis2])


fsis(pi, array([1,1]))

A=array([[1,2,3],[4,5,6]])
print(A[0,0])
print(A[:,1])
print(A[0,:])
