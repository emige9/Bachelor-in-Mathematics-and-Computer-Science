
from pylab import *
from time import perf_counter

###############################################################################

print('\n Práctica 2 \n')

###############################################################################

def fun(t,y):
    return -y+exp(-t)*cos(t);


def exacta(t):
    return exp(-t)*sin(t);



def AB2(a,b,fun, N,y0):
    
    y = zeros(N+1)
    t = zeros(N+1)
    f = zeros(N+1)
    t[0] = a
    h = (b-a)/float(N) 
    y[0] = y0
    f[0] = fun(a,y[0])    
    y[1] = y[0] + h*f[0]                 # Inicio del método de Euler
    t[1] = a+h
    f[1] = fun(t[1], y[1])
    for k in range(1,N):
        y[k+1] = y[k]+0.5*h*(3.0*f[k] - f[k-1])
        t[k+1] = t[k] + h
        f[k+1] = fun(t[k+1], y[k+1])
        
    return (t,y)

###############################################################################
###############################################################################

print('\n Ejercicio 0 - Prueba \n')

###############################################################################
###############################################################################

y0 = 0.0
a = 0.0
b = 5.0
N = 30
figure('Figura 0 - Prueba AB2')
tini = perf_counter()
(t,y) = AB2(a,b,fun, N,y0)
tfin = perf_counter()
ye = exacta(t)
clf()
plot(t,y, '*')
plot(t,ye)
h = (b - a)/float(N)
error = max(abs(y-ye))
tcpu = tfin-tini
print('---------------')
print('h = '+ str(h))
print('Error= '+str(error))
print('Tiempo CPU= '+str(tcpu))
print('---------------')
show()

###############################################################################
###############################################################################

print('\n Ejercicio 1 \n')

###############################################################################
###############################################################################


###############################################################################

print('\n Ejericio 1.a \n')

###############################################################################


y0 = 0.0
a = 0.0
b = 5.0
Nvarios = [10,20,40,80,160]

figure('Figura 1.a - AB2')
for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    (t, y) = AB2(a, b, fun, N, y0) # llamada al metodo 
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
        #print('Cociente de errores: ', errorold/error)
        print('Aproximación del orden: ', (log(errorold)-log(error))/log(2))
        
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

print('\n Ejercicio 1.b \n')

###############################################################################
# En este caso, como AB2 es de orden 2, necesitamos un metodo unipaso de 
# arranque de orden mayor o igual que 2.
# Para este ejercicio nos piden calcular y1 e y2 con un metodo 
# unipaso de orden 2, podemos tomar heun o punto medio, pero
# en este caso vamos a usar punto medio.
###############################################################################

def AB3(a,b,fun, N,y0):
    
    y = zeros(N+1)
    t = zeros(N+1)
    f = zeros(N+1)
    t[0] = a
    h = (b-a)/float(N) 
    y[0] = y0
    f[0] = fun(a,y[0])    
    
    for k in range(2):                               #metodo del punto medio (orden 2)
        ykmas1p = y[k] + 0.5*h*fun(t[k], y[k])
        y[k+1] = y[k] + h*fun(t[k] + 0.5*h, ykmas1p)
        t[k+1] = t[k] + h
        f[k+1] = fun(t[k+1], y[k+1])
    
    for k in range(2,N):
        y[k+1] = y[k]+(h/12)*(23.0*f[k] - 16.0*f[k-1] + 5.0*f[k-2])
        t[k+1] = t[k] + h
        f[k+1] = fun(t[k+1], y[k+1])
        
    return (t,y)

###############################################################################

y0 = 0.0
a = 0.0
b = 5.0
Nvarios = [10,20,40,80,160]

figure('Figura 1.b - AB3')
for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    (t, y) = AB3(a, b, fun, N, y0) # llamada al metodo 
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
        #print('Cociente de errores: ', errorold/error)
        print('Aproximación del orden: ', (log(errorold)-log(error))/log(2))
        
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

print('\n Ejercicio 1.c \n')

###############################################################################

def MPM(a, b, fun, N, y0):
    """Implementacion del metodo del punto medio en el intervalo [a, b]
    usando N particiones y condicion inicial y0"""
    
    h = (b-a)/N # paso de malla
    t = zeros(N+1) # inicializacion del vector de nodos
    y = zeros(N+1) # inicializacion del vector de resultados
    t[0] = a # nodo inicial
    y[0] = y0 # valor inicial

    # Metodo del punto medio
    for k in range(N):
        ykmas1p = y[k] + 0.5*h*fun(t[k], y[k])
        y[k+1] = y[k] + h*fun(t[k] + 0.5*h, ykmas1p)
        t[k+1] = t[k] + h
        
        
    return (t, y)

###############################################################################

print('\n Resolución con el método del punto medio \n')
y0 = 0.0
a = 0.0
b = 5.0
N = 3000
figure('Figura 1.c - Método unipaso')
tini = perf_counter()
(t,y) = MPM(a,b,fun, N,y0)
tfin = perf_counter()
ye = exacta(t)
clf()
plot(t,y, '*')
plot(t,ye)
h = (b - a)/float(N)
error = max(abs(y-ye))
tcpu = tfin-tini
print('---------------')
print('h = '+ str(h))
print('Error= '+str(error))
print('Tiempo CPU= '+str(tcpu))
print('---------------')
show()



print('\n Resolución con el método AB3 \n')
y0 = 0.0
a = 0.0
b = 5.0
N = 3000
figure('Figura 1.c - Método AB3')
tini = perf_counter()
(t,y) = AB3(a,b,fun, N,y0)
tfin = perf_counter()
ye = exacta(t)
clf()
plot(t,y, '*')
plot(t,ye)
h = (b - a)/float(N)
error = max(abs(y-ye))
tcpu = tfin-tini
print('---------------')
print('h = '+ str(h))
print('Error= '+str(error))
print('Tiempo CPU= '+str(tcpu))
print('---------------')
show()

###############################################################################
###############################################################################

print('\n Ejercicio 2 \n')

###############################################################################
###############################################################################

print('\n Ejericio 2.a \n')

###############################################################################
# Este es un método implicito, luego tenemos que despejar la ecuación.

# En este caso, como AM3 es de orden 4, necesitamos un metodo unipaso de 
# arranque de orden mayor o igual que 3.
# Como no hemos hecho ningún método de orden 3, usaremos el método rk4,
# que es de orden 4

# Un error muy frecuente en ejercicios es usar el tk+1 antes de darle valores
###############################################################################


def AM3(a,b,fun, N,y0):
    
    y = zeros(N+1)
    t = zeros(N+1)
    f = zeros(N+1)
    t[0] = a
    h = (b-a)/float(N) 
    y[0] = y0
    f[0] = fun(a,y[0])    
    
    for k in range(2):                               #metodo rk4 (orden 4)
        t[k+1] = t[k]+h
        k1=fun(t[k],y[k])
        k2=fun(t[k] + 0.5*h,y[k] + 0.5*h*k1)
        k3=fun(t[k] + 0.5*h,y[k] + 0.5*h*k2)
        k4=fun(t[k+1], y[k] + h*k3)
        y[k+1] = y[k]+(h/6)*(k1 + 2*k2 + 2*k3 + k4)  
        f[k+1] = fun(t[k+1], y[k+1])
    
    for k in range(2,N):
        t[k+1] = t[k] + h
        Ck = y[k] + (h/24)*(19*f[k] - 5*f[k-1] + f[k-2])
        y[k+1] = ((9*h/24)*(exp(-t[k+1])*cos(t[k+1])) + Ck)/(1 + (9*h)/24)
        f[k+1] = fun(t[k+1], y[k+1])
        
    return (t,y)

###############################################################################

y0 = 0.0
a = 0.0
b = 5.0
Nvarios = [10,20,40,80,160]

figure('Figura 2.a - AM3')
for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    (t, y) = AM3(a, b, fun, N, y0) # llamada al metodo 
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
        #print('Cociente de errores: ', errorold/error)
        print('Aproximación del orden: ', (log(errorold)-log(error))/log(2))
        
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

print('\n Ejericio 2.b \n')

###############################################################################

def AM3pf(a,b,fun, N,y0):
    
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
        
        epsilon = 1.e-12
        error = epsilon + 1
       
        
        
        iteraciones = 0
        z = y[k]
        
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

###############################################################################


y0 = 0.0
a = 0.0
b = 5.0
Nvarios = [10,20,40,80,160]

figure('Figura 2.b - AM3 punto fijo')
for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    (t, y, maxiter) = AM3pf(a, b, fun, N, y0) # llamada al metodo 
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
        #print('Cociente de errores: ', errorold/error)
        print('Aproximación del orden: ', (log(errorold)-log(error))/log(2))
        
    print('Paso de malla: ', (b-a)/N)
    print('Máximo número de iteraciones en el punto fijo: ', maxiter)
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

print('\n Ejericio 2.c \n')

###############################################################################

def AM3newton(a,b,fun, dyfun, N,y0):
    
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
        
        
    # Método AM3 con Newton para resolver la ecuación
    for k in range(2,N):
        t[k+1] = t[k] + h
        Ck = y[k] + (h/24)*(19*f[k] - 5*f[k-1] + f[k-2])
        
        epsilon = 1.e-12
        error = epsilon + 1
       
        
        
        iteraciones = 0
        z = y[k]
        
        while(error >= epsilon and iteraciones<200):
            
            F = z - (9*h/24)*fun(t[k+1],z) - Ck
            dF = 1 - (9*h/24)*dyfun(t[k+1],z)
            
            znew = z - F/dF
            error = abs(z - znew)
            z = znew
            iteraciones = iteraciones + 1 
            
        if(iteraciones==200):
            print('El método de punto fijo no ha convergido, se ha alcanzado el número máximo de iteraciones')
            
        maxiter = max(maxiter, iteraciones)
            
        y[k+1] = z
        f[k+1] = fun(t[k+1], y[k+1])
        
    return (t,y,maxiter)

def dyfun(t,y):
    return -1


y0 = 0.0
a = 0.0
b = 5.0
Nvarios = [10,20,40,80,160]

figure('Figura 2.c - AM3 Newton')
for N in Nvarios:
    
    tini = perf_counter()       #iniciar contador de tiempo
    (t, y, maxiter) = AM3newton(a, b, fun, dyfun, N, y0) # llamada al metodo 
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
        #print('Cociente de errores: ', errorold/error)
        print('Aproximación del orden: ', (log(errorold)-log(error))/log(2))
        
    print('Paso de malla: ', (b-a)/N)
    print('Máximo número de iteraciones en newton: ', maxiter)
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

print('\n Ejericio 2.d \n')

###############################################################################

#Definimos las nuevas funciones

def f2(t,y):
    return 1 + y**2

def exacta2(t):
    return tan(t)

def dyfun2(t,y):
    return 2*y

###############################################################################

print('Aplicando AM3 para punto fijo')

y0 = 0.
a = 0.
b = 1.
N = 320
figure('Figura 2.d - AM3 punto fijo')
tini = perf_counter()
(t,y,maxiter) = AM3pf(a,b,f2, N,y0)
tfin = perf_counter()
ye = exacta2(t)
clf()
plot(t,y, '*')
plot(t,ye)
h = (b - a)/float(N)
error = max(abs(y-ye))
tcpu = tfin-tini
print('---------------')
print('h = '+ str(h))
print('Error= '+str(error))
print('Tiempo CPU= '+str(tcpu))
print('Máximo número de iteraciones en el punto fijo: ', maxiter)
print('---------------')
show()

print('\n Aplicando AM3 Newton \n')

y0 = 0.0
a = 0.0
b = 1.0
N = 320
figure('Figura 2.d - AM3 Newton')
tini = perf_counter()
(t,y,maxiter) = AM3newton(a,b,f2, dyfun2, N,y0)
tfin = perf_counter()
ye = exacta2(t)
clf()
plot(t,y, '*')
plot(t,ye)
h = (b - a)/float(N)
error = max(abs(y-ye))
tcpu = tfin-tini
print('---------------')
print('h = '+ str(h))
print('Error= '+str(error))
print('Tiempo CPU= '+str(tcpu))
print('Máximo número de iteraciones en newton: ', maxiter)
print('---------------')
show()

###############################################################################

print('\n Ejericio 2.e \n')

###############################################################################

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





def AM3newton2(a,b,fun, dyfun, N,y0):
    
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
        
        
    # Método AM3 con Newton para resolver la ecuación
    for k in range(2,N):
        t[k+1] = t[k] + h
        Ck = y[k] + (h/24)*(19*f[k] - 5*f[k-1] + f[k-2])

        
        epsilon = 1.e-12
        error = epsilon + 1
       
        
        
        iteraciones = 0
        z = y[k] + h/12*(23*f[k] - 16*f[k-1] + 5*f[k-2])
        
        while(error >= epsilon and iteraciones<200):
            
            F = z - (9*h/24)*fun(t[k+1],z) - Ck
            dF = 1 - (9*h/24)*dyfun(t[k+1],z)
            
            znew = z - F/dF
            error = abs(z - znew)
            z = znew
            iteraciones = iteraciones + 1 
            
        if(iteraciones==200):
            print('El método de punto fijo no ha convergido, se ha alcanzado el número máximo de iteraciones')
            
        maxiter = max(maxiter, iteraciones)
            
        y[k+1] = z
        f[k+1] = fun(t[k+1], y[k+1])
        
    return (t,y,maxiter)

###############################################################################

y0 = 0.
a = 0.
b = 1.
N = 320
print('\n Aplicando AM3 punto fijo nuevo \n')
figure('Figura 2.e - AM3 punto fijo nuevo')
tini = perf_counter()
(t,y,maxiter) = AM3pf2(a,b,f2, N,y0)
tfin = perf_counter()
ye = exacta2(t)
clf()
plot(t,y, '*')
plot(t,ye)
h = (b - a)/float(N)
error = max(abs(y-ye))
tcpu = tfin-tini
print('---------------')
print('h = '+ str(h))
print('Error= '+str(error))
print('Tiempo CPU= '+str(tcpu))
print('Máximo número de iteraciones en el punto fijo: ', maxiter)
print('---------------')
show()

print('\n Aplicando AM3 Newton nuevo\n')

y0 = 0.0
a = 0.0
b = 1.0
N = 320
figure('Figura 2.e - AM3 Newton nuevo')
tini = perf_counter()
(t,y,maxiter) = AM3newton2(a,b,f2, dyfun2, N,y0)
tfin = perf_counter()
ye = exacta2(t)
clf()
plot(t,y, '*')
plot(t,ye)
h = (b - a)/float(N)
error = max(abs(y-ye))
tcpu = tfin-tini
print('---------------')
print('h = '+ str(h))
print('Error= '+str(error))
print('Tiempo CPU= '+str(tcpu))
print('Máximo número de iteraciones en newton: ', maxiter)
print('---------------')
show()

###############################################################################

print('\n Ejericio 3 \n')

###############################################################################

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

figure('Figura 3 - Método Predictor Corrector')
Nvarios=[10,20,40,80,160]

for N in Nvarios:
    tini = perf_counter() # pone el contador a cero
    (t, y) = ABM3(a, b,fun, N, y0) # llamada al metodo de Euler
    
    tfin=perf_counter()
    
    ye = exacta(t) 
    
    plot(t, y, '-*') 


    # Calculo del error cometido
    error = max(abs(y-ye))

    # Resultados
    print('-----')
    print('Tiempo CPU: ', str(tfin-tini))
    print('Error: ', str(error))
    if N!=Nvarios[0]:
        print('Orden del método aproximado:', (log(errorold)-log(error))/log(2))  
    print('Paso de malla: ' + str((b-a)/N)) 
    print('-----')
    
    errorold = error



###############################################################################

print('\n Ejericio 4 \n')

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



###############################################################################

# Solo Problema de Cauchy del problema 4 de la practica 1
def f(t,y):
    return array([0.25*y[0] - 0.01*y[0]*y[1],-y[1] + 0.01*y[0]*y[1]])





print('\n Ejercicio 4 - AM3 con punto fijo para sistemas \n')

# Comenzamos escribiendo los valores de la N

a = 0. # extremo inferior del intervalo
b = 20. # extremo superior del intervalo
y0 = array([80,30]) # condicion inicial
malla = array([20,40,80,160,320,640])

figure('Figura 4 - AM3 con punto fijo para sistemas')

for N in malla:
    
    tini = perf_counter()
    (t, y, maxiter) = AM3pfsis(a, b, f, N, y0)
    tfin=perf_counter()
    plot(y[0,:],y[1,:] )#dibuja las trayectorias aproximadas
    
    print('-----')
    print('Tiempo CPU:', tfin-tini) 
    print('Paso de malla: ' + str((b-a)/N))
    print('-----')


xlabel('t')
ylabel('y') 
legend(['N = ' + str(N) for N in malla])
show()



print('\n Ejercicio 4 - AB3 para sistemas \n')

# Comenzamos escribiendo los valores de la N

a = 0. # extremo inferior del intervalo
b = 20. # extremo superior del intervalo
y0 = array([80,30]) # condicion inicial
malla = array([20,40,80,160,320,640])

figure('Figura 4 - AB3 para sistemas')

for N in malla:
    
    tini = perf_counter()
    (t, y) = AB3Sis(a, b, f, N, y0)
    tfin=perf_counter()
    plot(y[0,:],y[1,:] )#dibuja las trayectorias aproximadas
    
    print('-----')
    print('Tiempo CPU:', tfin-tini) 
    print('Paso de malla: ' + str((b-a)/N))
    print('-----')


xlabel('t')
ylabel('y') 
legend(['N = ' + str(N) for N in malla])
show()


print('\n Ejercicio 4 - ABM para sistemas \n')

# Comenzamos escribiendo los valores de la N

a = 0. # extremo inferior del intervalo
b = 20. # extremo superior del intervalo
y0 = array([80,30]) # condicion inicial
malla = array([20,40,80,160,320,640])

figure('Figura 4 - ABM para sistemas')

for N in malla:
    
    tini = perf_counter()
    (t, y) = ABMSis(a, b, f, N, y0)
    tfin=perf_counter()
    plot(y[0,:],y[1,:] )#dibuja las trayectorias aproximadas
    
    print('-----')
    print('Tiempo CPU:', tfin-tini) 
    print('Paso de malla: ' + str((b-a)/N))
    print('-----')


xlabel('t')
ylabel('y') 
legend(['N = ' + str(N) for N in malla])
show()





