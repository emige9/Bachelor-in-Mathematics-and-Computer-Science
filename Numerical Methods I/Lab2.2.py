# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

from numpy import *
from matplotlib.pyplot import *

print('Práctica 2.2')

print('\n Ejercicio 1 \n')

def puntofijo(g, x0, eps, nmax):
    error = 1 + eps
    k=0
    
    while(error>eps and k<nmax):
       x1 = g(x0)
       error = abs(x1-x0)
       x0 = x1
       k+=1
    if k==nmax:
        print('Se alcanza el número máximo de iteraciones ', nmax)
        print('El último valor obtenido es ', x1)
    else:
        print('Se alcanza la precisión requerida en ', k, ' iteraciones')
        print('La aproximación obtenida es ', x1)
    return x1

print('\n Ejercicio 2 \n')

print('\n Aparatado a \n')

def g2a(x):
    return exp(-x)

puntofijo(g2a, 0.5, 1e-7, 100)

print('\n Aparatado b \n')

def g2b(x):
    return x-(x-exp(-x))/(1+exp(-x))

puntofijo(g2b,0.5,1e-7,100)

print('\n Ejercicio 3 \n')

print('\n Aparatado a \n')



def g3a(x):
    k=2/3
    alpha = 0.093
    return k + alpha*sin(x)

puntofijo(g3a,0.5,1e-7,100) 


print('\n Aparatado b \n')

def g3b(x):
    k=2/3
    alpha = 0.093
    return x - (x-k-alpha*sin(x))/(1-alpha*cos(x))

puntofijo(g3b,0.5,1e-7,100)


print('\n Ejercicio 4 \n')

print('\n Aparatado a \n')

def g4a(x):
    return cos(x)

puntofijo(g4a,0.5,1e-7,100)

print('\n Aparatado b \n')

def g4b(x):
    return x - (cos(x) - x)/(-sin(x) - 1)

puntofijo(g4b,0.5,1e-7,100)


print('\n Ejercicio 5 \n')

print('\n Aparatado a \n')

def f5a(x):
    return x**5 -5*x**3 + 1

def g5a(x):
    return x - (f5a(x))/(5*x**4 - 15*x**2)

puntofijo(g5a, 0.5, 1e-7,100)


x = linspace(0.3,0.7)
plot(x,g5a(x))
plot(x, 0*x)
plot(x,x)
show()


print('\n Aparatado b \n')

def g5b0(x):
    return x + f5a(x)

#puntofijo(g5b0,0.5,1e-7,100) 
#Este método produce overflow


x = linspace(0,1,100)
y = g5b0(x)
plot(x,y)
plot(x,x)
show()

def g5b1(x):
    return x + 0.2*(f5a(x))

puntofijo(g5b1, 0.5,1e-7,100)

x = linspace(0,1,100)
y = g5b1(x)
plot(x,y)
plot(x,x)
show()

def g5b2(x):
    return (5*x**3-1)**(1/5)

puntofijo(g5b2, 0.5,1e-7,100)

x = linspace(0,1,100)
y = g5b2(x)
plot(x,y)
plot(x,x)
show()

def g5b3(x):
    return ((x**5+1)/5)**(1/3)

puntofijo(g5b3, 0.5,1e-7,100)

x = linspace(0,1,100)
y = g5b3(x)
plot(x,y)
plot(x,x)
show()

print('\n Ejercicio 6 \n')

print('\n Aparatado a \n')

def puntofijo2(f, g, x0, eps, nmax):
    #error = 1 + eps
    error = abs(f(x0))
    k=0
    
    while(error>eps and k<nmax):
       x1 = g(x0)
       error = abs(f(x1))
       x0 = x1
       k+=1
    if k==nmax:
        print('Se alcanza el número máximo de iteraciones ', nmax)
        print('El último valor obtenido es ', x1)
    else:
        print('Se alcanza la precisión requerida en ', k, ' iteraciones')
        print('La aproximación obtenida es ', x1)
    return x1

print('\n Aparatado b \n')

x = linspace(0,1,100)
y = x + (x-1)*exp(x)
plot(x,y)
plot(x,0*x)
show()

print('\n Aparatado c \n')

def fun(x):
    return x + (x-1)*exp(x)

def g6c(x):
    return x - (x + (x-1)*exp(x))/(1 + x*exp(x))

puntofijo2(fun, g6c, 0.7, 1e-8, 100)


print('\n Aparatado d \n')







     