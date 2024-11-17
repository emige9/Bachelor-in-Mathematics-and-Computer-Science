# -*- coding: utf-8 -*-
"""
Created on Wed May 29 23:52:57 2024

@author: Usuario
"""

from pylab import *
from time import perf_counter

print('\n Práctica 1 - Bis \n')

###############################################################################

print('Ejercicio 1')

###############################################################################

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


def AB3(a,b,fun, N,y0):
    
    y = zeros(N+1)
    t = zeros(N+1)
    f = zeros(N+1)
    t[0] = a
    h = (b-a)/float(N) 
    y[0] = y0
    f[0] = fun(a,y[0])    
    
    for k in range(2):                               #metodo rk4
        t[k+1] = t[k]+h
        k1=fun(t[k],y[k])
        k2=fun(t[k] + 0.5*h,y[k] + 0.5*h*k1)
        k3=fun(t[k] + 0.5*h,y[k] + 0.5*h*k2)
        k4=fun(t[k+1], y[k] + h*k3)
        y[k+1] = y[k]+(h/6)*(k1 + 2*k2 + 2*k3 + k4)  
        f[k+1] = fun(t[k+1], y[k+1])
    
    for k in range(2,N):
        y[k+1] = y[k]+(h/12)*(23.0*f[k] - 16.0*f[k-1] + 5.0*f[k-2])
        t[k+1] = t[k] + h
        f[k+1] = fun(t[k+1], y[k+1])
        
    return (t,y)



def AM3pf2(a,b,fun, N,y0):
    
    y = zeros(N+1)
    t = zeros(N+1)
    f = zeros(N+1)
    t[0] = a
    h = (b-a)/float(N) 
    y[0] = y0
    f[0] = fun(t[0],y[0])  
    
    maxiter = 0
    
    for k in range(2):                               #metodo rk4 (orden 4)
        t[k+1] = t[k]+h
        k1=fun(t[k],y[k])
        k2=fun(t[k] + 0.5*h,y[k] + 0.5*h*k1)
        k3=fun(t[k] + 0.5*h,y[k] + 0.5*h*k2)
        k4=fun(t[k+1], y[k] + h*k3)
        y[k+1] = y[k]+(h/6)*(k1 + 2*k2 + 2*k3 + k4)  
        f[k+1] = fun(t[k+1], y[k+1])
        
        
    # Método AM3 con punto fijo para resolver la ecuación
    for k in range(2,N):
        t[k+1] = t[k] + h
        Ck = y[k] + (h/24)*(19*f[k] - 5*f[k-1] + f[k-2])
        z = y[k] + h/12*(23*f[k] - 16*f[k-1] + 5*f[k-2])
        epsilon = 1.e-12
        error = epsilon + 1
        iteraciones = 0
        
        while(error >= epsilon and iteraciones<200):
            znew = (9/24)*h*fun(t[k+1],z) + Ck 
            error = abs(z - znew)
            z = znew
            iteraciones = iteraciones + 1 
            
        if(iteraciones==200):
            print('El método de punto fijo no ha convergido, se ha alcanzado el número máximo de iteraciones')
            
        maxiter = max(maxiter, iteraciones)
            
        y[k+1] = z
        f[k+1] = fun(t[k+1], y[k+1])
        
    return (t,y,maxiter)


def ABM3(a,b,fun, N,y0):
    
    y = zeros(N+1)
    t = zeros(N+1)
    f = zeros(N+1)
    t[0] = a
    h = (b-a)/float(N) # el float no hace falta, puede quitarse y escribirse como hemos hecho siempre
    y[0] = y0
    f[0] = fun(a,y[0])    

    
    for k in range(2): # aqui es donde usamos el metodo rk4
        t[k+1] = t[k]+h
        k1 = fun(t[k], y[k])
        k2 = fun(t[k]+0.5*h, y[k]+0.5*h*k1)
        k3 = fun(t[k]+0.5*h, y[k]+0.5*h*k2)
        k4 = fun(t[k+1], y[k] + h*k3)
        y[k+1] = y[k] + h/6*(k1+2*k2+2*k3+k4)
        f[k+1] = fun(t[k+1],y[k+1])
 
    
    for k in range(2,N):
        t[k+1] = t[k] + h  #si lo ponemos en la cuarta linea y la cuarta en la tercera, el ejercicio NO SALE
        ykmas1p = y[k] + h/12*(23*f[k] - 16*f[k-1] + 5*f[k-2])
        fkmas1p = fun(t[k+1], ykmas1p)
        y[k+1] = y[k] + h/24*(9*fkmas1p + 19*f[k] - 5*f[k-1] + f[k-2])
        f[k+1] = fun(t[k+1], y[k+1])
        
    return (t,y)


###############################################################################
print('AB3')


figure('Ejercicio 1')
#Aplicamos el metodo de AB3 a nuestra ecuacion
(t,y) = AB3(a,b,f1,N,y0)
ye= exacta1(t)

#El error viene definido de la siguiente manera
error= max(abs(ye-y))

subplot(311)
print('El error del metodo AB3 es',error)
#Pintamos en una misma tabla las graficas de la aproximacion y solucion exacta
plot (t, y, label='Aproximacion por AB3')
plot(t, ye, label='Solucion exacta')
legend()
title('AB3')
show()


print('AM3')
#Aplicamos el metodo de AM3 a nuestra ecuacion
(t,y, maxiter) = AM3pf2(a,b,f1,N,y0)
ye= exacta1(t)

#El error viene definido de la siguiente manera
error= max(abs(ye-y))



subplot(312)
print('El error del metodo AM3 es',error)
#Pintamos en una misma tabla las graficas de la aproximacion y solucion exacta
plot (t, y, label='Aproximacion por AM3')
plot(t, ye, label='Solucion exacta')
legend()
title('AM3')
show()


print('ABM3')
#Aplicamos el metodo de ABM3 a nuestra ecuacion
(t,y) = ABM3(a,b,f1,N,y0)
ye= exacta1(t)

#El error viene definido de la siguiente manera
error= max(abs(ye-y))

subplot(313)
print('El error del metodo ABM3 es',error)
#Pintamos en una misma tabla las graficas de la aproximacion y solucion exacta
plot (t, y, label='Aproximacion por ABM3')
plot(t, ye, label='Solucion exacta')
legend()
title('ABM3')
show()


###############################################################################

print('Ejercicio 2')

###############################################################################

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
N = 100 # numero de particiones
y0 = array([1,0,-1]) # condicion inicial

###############################################################################
# Lista de métodos que vamos a usar
###############################################################################


def AB3Sis(a,b,fun, N,y0):
    
    #como tengo un metodo de tres pasos necesito un unipaso para y_1, y_2 e y_3:
    y = zeros([len(y0), N+1])
    t = zeros(N+1)
    f = zeros([len(y0), N+1])
    t[0] = a
    h = (b-a)/float(N) 
    
    #metodo unipaso inicial: RK4
    y[:,0] = y0
    f[:,0] = fun(a,y[:,0])    
    
    for k in range(2):        
       t[k+1] = t[k] + h
       
       k1 = fun(t[k], y[:,k])
       k2 = fun(t[k]+0.5*h, y[:,k] + 0.5*h*k1)
       k3 = fun(t[k]+0.5*h, y[:,k] + 0.5*h*k2)
       k4 = fun(t[k+1], y[:,k] + h*k3)

       y[:,k+1] = y[:,k] + h/6*(k1+2*k2+2*k3+k4)
       
       f[:,k+1] = fun(t[k+1],y[:,k+1])
        
    for k in range(2,N): #metodo multipaso (bipaso):
        y[:,k+1] = y[:,k]+(1/12)*h*(23*f[:,k] - 16*f[:,k-1] + 5*f[:,k-2])
        t[k+1] = t[k] + h
        f[:,k+1] = fun(t[k+1], y[:,k+1])
        
    return (t,y)


def AM3pfsis(a,b,fun, N,y0):
    
    y = zeros([len(y0),N+1])
    t = zeros(N+1)
    f = zeros([len(y0),N+1])
    t[0] = a
    h = (b-a)/N
    y[:,0] = y0
    f[:,0] = fun(a,y[:,0])
    
    maxiter = 0
    
    for k in range (2):
        
        t[k+1] = t[k] + h
        k1 = fun(t[k],y[:,k])
        k2 = fun(t[k] + h*0.5,y[:,k] +h*0.5*k1)
        k3 = fun(t[k] + h*0.5,y[:,k] +h*0.5*k2)
        k4 = fun(t[k]+ h ,y[:,k] + h*k3)
        y[:,k+1] = y[:,k] + h/6*(k1 + 2*k2 + 2*k3 + k4)
        f[:,k+1] = fun(t[k+1],y[:,k+1])
        
        
    #Método AM3 con punto fijo para resolver la ecuación
    for k in range(2,N):
        
        t[k+1] = t[k] + h
        ck = y[:,k] + h/24*(19*f[:,k] -5*f[:,k-1] +f[:,k-2])
        eps = 1.e-12
        error = eps + 1
        iter = 0
        z = y[:,k]

        while(error > eps and iter < 200):
            
            znew = 9/24*h*fun(t[k+1],z) + ck
            error = max(abs(z - znew))
            z = znew
            
            iter += 1
        
        if iter == 200:
            print('El método del punto fijo no ha convergido')
            
        maxiter = max(iter, maxiter)
        
        y[:,k+1] = z
        f[:,k+1] = fun(t[k+1], y[:,k+1])
        
    return (t,y, maxiter)


def AM3pf2sis(a,b,fun, N,y0):
    
    y = zeros([len(y0),N+1])
    t = zeros(N+1)
    f = zeros([len(y0),N+1])
    t[0] = a
    h = (b-a)/float(N) 
    y[:,0] = y0
    f[:,0] = fun(t[0],y[:,0])  
    
    maxiter = 0
    
    for k in range(2):                               #metodo rk4 (orden 4)
        t[k+1] = t[k] + h
        k1 = fun(t[k],y[:,k])
        k2 = fun(t[k] + h*0.5,y[:,k] +h*0.5*k1)
        k3 = fun(t[k] + h*0.5,y[:,k] +h*0.5*k2)
        k4 = fun(t[k]+ h ,y[:,k] + h*k3)
        y[:,k+1] = y[:,k] + h/6*(k1 + 2*k2 + 2*k3 + k4)
        f[:,k+1] = fun(t[k+1],y[:,k+1])
        
        
    # Método AM3 con punto fijo para resolver la ecuación
    for k in range(2,N):
        t[k+1] = t[k] + h
        Ck = y[:,k] + (h/24)*(19*f[:,k] - 5*f[:,k-1] + f[:,k-2])
        z = y[:,k] + h/12*(23*f[:,k] - 16*f[:,k-1] + 5*f[:,k-2])
        epsilon = 1.e-12
        error = epsilon + 1
        iteraciones = 0
        
        while(error >= epsilon and iteraciones<200):
            znew = (9/24)*h*fun(t[k+1],z) + Ck 
            error = max(abs(z - znew))
            z = znew
            iteraciones = iteraciones + 1 
            
        if(iteraciones==200):
            print('El método de punto fijo no ha convergido, se ha alcanzado el número máximo de iteraciones')
            
        maxiter = max(maxiter, iteraciones)
            
        y[:,k+1] = z
        f[:,k+1] = fun(t[k+1], y[:,k+1])
        
    return (t,y,maxiter)




def ABM3Sis(a,b,fun, N,y0):
    
    #metodo predictor corrector que usa como predictor el metodo de AB3 y corrector AM3
    y = zeros([len(y0), N+1])
    t = zeros(N+1)
    f = zeros([len(y0), N+1])
    t[0] = a
    h = (b-a)/float(N) 
    
    #metodo unipaso inicial: RK4
    y[:,0] = y0
    f[:,0] = fun(a,y[:,0])    
    
    for k in range(2):        
       t[k+1] = t[k] + h
       
       k1 = fun(t[k], y[:,k])
       k2 = fun(t[k]+0.5*h, y[:,k] + 0.5*h*k1)
       k3 = fun(t[k]+0.5*h, y[:,k] + 0.5*h*k2)
       k4 = fun(t[k+1], y[:,k] + h*k3)

       y[:,k+1] = y[:,k] + h/6*(k1+2*k2+2*k3+k4)
       
       f[:,k+1] = fun(t[k+1],y[:,k+1])
        
    for k in range(2,N): #metodo multipaso (tri):
        t[k+1] = t[k] + h
        ykmas1p = y[:,k] + h/12*(23*f[:,k]-16*f[:,k-1]+5*f[:,k-2])#AB3 PREDICTOR
        fkmas1p = fun(t[k+1],ykmas1p)
        y[:,k+1] = y[:,k] + h/24*(9*fkmas1p + 19*f[:,k] - 5*f[:,k-1] + f[:,k-2])
        f[:,k+1] = fun(t[k+1], y[:,k+1])
        
    return (t,y)


print("AB3 para sistemas")
#Aplicamos el metodo de AB3 a nuestro sistema de ecuaciones
(t,y) = AB3Sis(a,b,f2,N,y0)
ye= exacta2(t)

#El error viene definido de la siguiente manera
error = zeros(len(y))
for k in range (len(y)):
    error[k]= max(abs(y[k]-ye[k]))

print('El error del metodo AB3 para sistemas es',error)

print("AM3 para sistemas")
#Aplicamos el metodo de AM3 a nuestro sistema de ecuaciones
(t,y, maxiter) = AM3pf2sis(a,b,f2,N,y0)
ye= exacta2(t)

#El error viene definido de la siguiente manera
error = zeros(len(y))
for k in range (len(y)):
    error[k]= max(abs(y[k]-ye[k]))

print('El error del metodo AM3 para sistemas es',error)

print("ABM3 para sistemas")
#Aplicamos el metodo de ABM3 a nuestro sistema de ecuaciones
(t,y) = ABM3Sis(a,b,f2,N,y0)
ye= exacta2(t)

#El error viene definido de la siguiente manera
error = zeros(len(y))
for k in range (len(y)):
    error[k]= max(abs(y[k]-ye[k]))

print('El error del metodo ABM3 para sistemas es',error)















