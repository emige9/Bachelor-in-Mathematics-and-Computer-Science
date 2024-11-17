# -*- coding: utf-8 -*-
"""
Created on Fri Dec  9 18:24:42 2022

@author: PC
"""

from numpy import *
from matplotlib.pyplot import *
from scipy.integrate import quad

print('\n Ejercicio 0\n')

def funexp(x):
    return exp(-x*x)

iquad,equad=quad(funexp,0,1)
print('La aproximación obtenida con quad es',iquad,'y la cota de error es',equad)

print('\n Ejercicio 1\n')

def puntomedio(f,a,b):
    c=0.5*(a+b)
    ipuntomedio=(b-a)*f(c)
    return ipuntomedio

ipuntomedio=puntomedio(funexp,0,1)
epuntomedio=abs(ipuntomedio-iquad)
print('La aproximación con puntomedio es',ipuntomedio,'y el "error" es',epuntomedio)

print('\n Ejercicio 2\n')

def trapecio(f,a,b):
    itrapecio=0.5*(b-a)*(f(a)+f(b))
    return itrapecio

itrapecio=trapecio(funexp,0,1)
etrapecio=abs(itrapecio-iquad)
print('La aproximación con trapecio es',itrapecio,'y el "error" es',etrapecio)

print('\n Ejercicio 3\n')

def simpson(f,a,b):
    c=0.5*(a+b)
    isimpson=(b-a)/6*(f(a)+4*f(c)+f(b))
    return isimpson

isimpson=simpson(funexp,0,1)
esimpson=abs(isimpson-iquad)
print('La aproximación con simpson es',isimpson,'y el "error" es',esimpson)

print('\n Ejercicio 4\n')

def puntomedioc(f,a,b,N):
    x=linspace(a,b,N+1)
    c=0.5*(x[0:N]+x[1:N+1])
    #c=x[0:N]+0.5*(b-a)/N Otra opción
    ipuntomedioc=(b-a)/N*sum(f(c))
    return ipuntomedioc

ipuntomedioc10=puntomedioc(funexp,0,1,10)
epuntomedioc10=abs(ipuntomedioc10-iquad)
print('La aproximación con puntomedio compuesto con N=',10,'es',ipuntomedioc10,'y el "error" es',epuntomedioc10)

ipuntomedioc20=puntomedioc(funexp,0,1,20)
epuntomedioc20=abs(ipuntomedioc20-iquad)
print('La aproximación con puntomedio compuesto con N=',20,'es',ipuntomedioc20,'el "error" es',epuntomedioc20,'y el cociente de error es',epuntomedioc20/epuntomedioc10)

ipuntomedioc40=puntomedioc(funexp,0,1,40)
epuntomedioc40=abs(ipuntomedioc40-iquad)
print('La aproximación con puntomedio compuesto con N=',40,'es',ipuntomedioc40,'el "error" es',epuntomedioc40,'y el cociente de error es',epuntomedioc40/epuntomedioc20)

ipuntomedioc80=puntomedioc(funexp,0,1,80)
epuntomedioc80=abs(ipuntomedioc80-iquad)
print('La aproximación con puntomedio compuesto con N=',80,'es',ipuntomedioc80,'el "error" es',epuntomedioc80,'y el cociente de error es',epuntomedioc80/epuntomedioc40)

print('El cociente de errores obtenidos al dividir h=(b-a)/N por 2 es aproximadamente 1/4')
print('Esto ocurre por ser un método de orden 2')

print('\n Ejercicio 5\n')

def trapecioc(f,a,b,N):
    x=linspace(a,b,N+1)
    itrapecioc=(b-a)/(2*N)*(f(a)+f(b)+2*sum(f(x[1:N])))
    return itrapecioc

itrapecioc10=trapecioc(funexp,0,1,10)
etrapecioc10=abs(itrapecioc10-iquad)
print('La aproximación con puntomedio compuesto con N=',10,'es',itrapecioc10,'y el "error" es',etrapecioc10)

itrapecioc20=trapecioc(funexp,0,1,20)
etrapecioc20=abs(itrapecioc20-iquad)
print('La aproximación con puntomedio compuesto con N=',20,'es',itrapecioc20,'el "error" es',etrapecioc20,'y el cociente de error es',etrapecioc20/etrapecioc10)

itrapecioc40=trapecioc(funexp,0,1,40)
etrapecioc40=abs(itrapecioc40-iquad)
print('La aproximación con puntomedio compuesto con N=',40,'es',itrapecioc40,'el "error" es',etrapecioc40,'y el cociente de error es',etrapecioc40/etrapecioc20)

itrapecioc80=trapecioc(funexp,0,1,80)
etrapecioc80=abs(itrapecioc80-iquad)
print('La aproximación con puntomedio compuesto con N=',80,'es',itrapecioc80,'el "error" es',etrapecioc80,'y el cociente de error es',etrapecioc80/etrapecioc40)

print('El cociente de errores obtenidos al dividir h=(b-a)/N por 2 es aproximadamente 1/4')
print('Esto ocurre por ser un método de orden 2')

print('\n Ejercicio 6\n')

def simpsonc(f,a,b,N):
    x=linspace(a,b,N+1)
    c=0.5*(x[0:N]+x[1:N+1])
    isimpsonc=(b-a)/(6*N)*(f(a)+f(b)+2*sum(f(x[1:N]))+4*sum(f(c)))
    return isimpsonc

isimpsonc10=simpsonc(funexp,0,1,10)
esimpsonc10=abs(isimpsonc10-iquad)
print('La aproximación con puntomedio compuesto con N=',10,'es',isimpsonc10,'y el "error" es',esimpsonc10)

isimpsonc20=simpsonc(funexp,0,1,20)
esimpsonc20=abs(isimpsonc20-iquad)
print('La aproximación con puntomedio compuesto con N=',20,'es',isimpsonc20,'el "error" es',esimpsonc20,'y el cociente de error es',esimpsonc20/esimpsonc10)

isimpsonc40=simpsonc(funexp,0,1,40)
esimpsonc40=abs(isimpsonc40-iquad)
print('La aproximación con puntomedio compuesto con N=',40,'es',isimpsonc40,'el "error" es',esimpsonc40,'y el cociente de error es',esimpsonc40/esimpsonc20)

isimpsonc80=simpsonc(funexp,0,1,80)
esimpsonc80=abs(isimpsonc80-iquad)
print('La aproximación con puntomedio compuesto con N=',80,'es',isimpsonc80,'el "error" es',esimpsonc80,'y el cociente de error es',esimpsonc80/esimpsonc40)

print('El cociente de errores obtenidos al dividir h=(b-a)/N por 2 es aproximadamente 1/16')
print('Esto ocurre por ser un método de orden 4')

print('\n Ejercicio 7\n')

def gauss3(f,a,b):
    t=array([-sqrt(3/5),0,sqrt(3/5)])
    alfatilde=array([5/9,8/9,5/9])
    x=a+(b-a)/2*(t+1)
    alfa=(b-a)/2*alfatilde
    igauss3=sum(alfa*f(x))
    return igauss3

igauss3=gauss3(funexp,0,1)
egauss3=abs(igauss3-iquad)
print('La aproximación es',igauss3,'y el error es',egauss3)