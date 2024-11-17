# -*- coding: utf-8 -*-
"""
Created on Thu Oct 13 14:55:12 2022

@author: Usuario_UMA
"""

from numpy import *
from matplotlib.pyplot import *

print('Ejercicio 1')
print('Apartado a')

def bisec(f,a,b,N):
    an=a
    bn=b
    fan=f(an)
    fbn=f(bn)
    if fan==0:
        print(a, 'es raíz de la función')
        return a
    elif fbn==0:
        print(b, 'es raíz de la función')
        return b
    elif fan*fbn>0:
        print('No hay cambio de signo: no se puede aplicar el método')
        #return
    for k in range(N):       #N iteraciones
        cn=(an+bn)/2.
        fcn=f(cn)
        print('iter', k+1, 'c=', cn, 'fc=', fcn)
        if fcn==0:
            print(cn, 'es raíz de la función')
            return cn
        elif fan*fcn<0:
            bn=cn
            fbn=fcn
        else:
            an=cn
            fan=fcn
    print('La aproximación de la raíz tras ', N, ' iteraciones es ', cn)
    return cn


print('Apartado b')

def f1(x):
    return x**5 - 5*x**3 + 1

x=linspace(-3,3)
plot(x,f1(x))
plot(x,0*x)
show()

bisec(f1,0,1,20)
bisec(f1,-3,-2,20)
bisec(f1,2,3,20)


print('Apartado c')


def f2(x):
    return cos(x) - x

x = linspace(-2,2)
plot(x,f2(x))
plot(x,0*x)
show()

bisec(f2,0.5,1.5,20)


print('Apartado d')


def bisec_mod(f,a,b,eps):
    
    N = int((log(b-a)-log(eps))/log(2)) + 1
    
    an=a
    bn=b
    fan=f(an)
    fbn=f(bn)
    if fan==0:
        print(a, 'es raíz de la función')
        return a
    elif fbn==0:
        print(b, 'es raíz de la función')
        return b
    elif fan*fbn>0:
        print('No hay cambio de signo: no se puede aplicar el método')
        #return
    for k in range(N):       #N iteraciones
        cn=(an+bn)/2.
        fcn=f(cn)
        print('iter', k+1, 'c=', cn, 'fc=', fcn)
        if fcn==0:
            print(cn, 'es raíz de la función')
            return cn
        elif fan*fcn<0:
            bn=cn
            fbn=fcn
        else:
            an=cn
            fan=fcn
    print('La aproximación de la raíz tras ', N, ' iteraciones es ', cn)
    return cn



print('Apartado e')


bisec_mod(f1,0,1,1e-7)
bisec_mod(f1,-3,-2,1e-7)
bisec_mod(f1,2,3,1e-7)
bisec_mod(f2,0.5,1.5,1e-7)



print('Ejercicio 2')

print('Apartado a')

def regula_falsi(f,a,b,eps,nmax):
    an=a
    bn=b
    fan=f(an)
    fbn=f(bn)
    if fan==0:
        print(a, 'es raíz de la función')
        return a
    elif fbn==0:
        print(b, 'es raíz de la función')
        return b
    elif fan*fbn>0:
        print('No hay cambio de signo: no se puede aplicar el método')
        #return
    it = 0
    cn = bn - ((bn-an)/(fbn-fan))*fbn
    fcn=f(cn)
        
    print('iter', it+1, 'c=', cn, 'fc=', fcn)
        
    it += 1
    cn_old = cn
    
    if fcn==0:
        print(cn, 'es raíz de la función')
        return cn
    elif fan*fcn<0:
        bn=cn
        fbn=fcn
    else:
        an=cn
        fan=fcn
    
    error = eps + 1
    
    while (error>eps and it<nmax):
    
        cn = bn - ((bn-an)/(fbn-fan))*fbn
        fcn=f(cn)
        
        print('iter', it+1, 'c=', cn, 'fc=', fcn)
        
        it += 1
        error = abs(cn-cn_old)
        cn_old = cn
        
        if fcn==0:
            print(cn, 'es raíz de la función')
            return cn
        elif fan*fcn<0:
            bn=cn
            fbn=fcn
        else:
            an=cn
            fan=fcn
    print('La aproximación de la raíz tras ', it, ' iteraciones es ', cn)
    if it<nmax:
        print('Se ha alcanzado una aproximación satisfactoria')
    else:
        print('Se ha alcanzado el número máximo de iteraciones')
        
    return cn


print('Apartado b')


regula_falsi(f1,0,1,1e-7,50)
regula_falsi(f1,-3,-2,1e-7,50)
regula_falsi(f1,2,3,1e-7,50)
regula_falsi(f2,0.5,1.5,1e-7,50)



print('Apartado c')

def regula_falsi_mod(f,a,b,eps,nmax):
    an=a
    bn=b
    fan=f(an)
    fbn=f(bn)
    if fan==0:
        print(a, 'es raíz de la función')
        return a
    elif fbn==0:
        print(b, 'es raíz de la función')
        return b
    elif fan*fbn>0:
        print('No hay cambio de signo: no se puede aplicar el método')
        #return
    it = 0
    error = eps + 1
    while (error>eps and it<nmax):
    
        cn = bn - ((bn-an)/(fbn-fan))*fbn
        fcn=f(cn)
        
        print('iter', it+1, 'c=', cn, 'fc=', fcn)
        
        it += 1
        error = abs(fcn)
        
        if fcn==0:
            print(cn, 'es raíz de la función')
            return cn
        elif fan*fcn<0:
            bn=cn
            fbn=fcn
        else:
            an=cn
            fan=fcn
    print('La aproximación de la raíz tras ', it, ' iteraciones es ', cn)
    if it<nmax:
        print('Se ha alcanzado una aproximación satisfactoria')
    else:
        print('Se ha alcanzado el número máximo de iteraciones')
        
    return cn


print('Apartado d')

regula_falsi_mod(f1,0,1,1e-7,50)
regula_falsi_mod(f1,-3,-2,1e-7,50)
regula_falsi_mod(f1,2,3,1e-7,50)
regula_falsi_mod(f2,0.5,1.5,1e-7,50)



print('Ejercicio 3')

print('Apartado a')

def secante(f,x0,x1,eps,nmax):
    fx0=f(x0)
    fx1=f(x1)
    if fx0==0:
        print(x0, 'es raíz de la función')
        return x0
    elif fx1==0:
        print(x1, 'es raíz de la función')
        return x1
    elif fx0==fx1:
        print('No se le puede aplicar el método')
        #return
    it = 0     
    error = eps + 1
    
    while (error>eps and it<nmax):
    
        x2 = x1 - ((x1-x0)/(fx1-fx0))*fx1
        fx2=f(x2)
        
        print('iter', it+1, 'x=', x2, 'fx=', fx2)
        
        it += 1
        error = abs(x2-x1)
        x0 = x1
        x1 = x2 
        fx0 = fx1
        fx1 = fx2
        
        if fx2==0:
            print(x2, 'es raíz de la función')
            return x2
        
    print('La aproximación de la raíz tras ', it, ' iteraciones es ', x2)
    if it<nmax:
        print('Se ha alcanzado una aproximación satisfactoria')
    else:
        print('Se ha alcanzado el número máximo de iteraciones')
        
    return x2


print('Apartado b')

secante(f1,0,1,1e-7,50)


print('Apartado c')




print('Apartado d')





