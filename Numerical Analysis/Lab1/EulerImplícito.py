# -*- coding: utf-8 -*-
"""
Created on Tue Apr 16 23:41:04 2024

@author: Usuario
"""

from pylab import *
from time import perf_counter

print('\n Ejercicio 1 \n')

print('\n Apartado a \n')

def eulerImplicito(a, b, N, y0):
    
    h = (b-a)/N  #paso de malla
    t = zeros(N+1) #inicializacion del vector de nodos
    y = zeros(N+1) #inicializacion del vector de resultados
    t[0] = a #nodo inicial
    y[0] = y0 #valor inicial
    
    # Método RK con tablero de Butcher
    for k in range(N):
        t[k+1] = t[k] + h
        y[k+1] = (y[k] + h)/(1 + 2.*h)
    
    return (t,y)


print('\n Apartado b \n')


def exacta(t):
    return 0.5*(exp(-2.*t)+1)

a = 0.
b = 2.
y0 = 1.

Nvarios = [10,20,40,80,160]

figure('Figura 1 - Euler Implícito')
for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    (t, y) = eulerImplicito(a, b, N, y0) # llamada al metodo de EulerRK4
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