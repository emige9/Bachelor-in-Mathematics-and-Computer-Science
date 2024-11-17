-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º ETSI Informática. UMA
-- Titulación: Doble Grado en Matemáticas + Ing. Informática
--
-- Alumno: GÓMEZ ESTEBAN, EMILIO
--
-- Relación de ejercicios 1. Ejercicios resueltos:
-------------------------------------------------------------------------------

module Relacion1 where

import Test.QuickCheck

-------------------------------------------------------------------------------
-- Ejercicio 1 - esTerna y terna
-------------------------------------------------------------------------------

--1.a
square :: Integer -> Integer
square x = x*x

esTerna :: Integer -> Integer -> Integer -> Bool
esTerna x y z = square x + square y == square z

--1.b
terna :: Integer -> Integer -> (Integer, Integer, Integer)
terna x y = (square x - square y, 2*x*y, square x + square y)

--1.c
p_ternas x y = x>0 && y>0 && x>y ==> esTerna l1 l2 h
    where 
        (l1,l2,h) = terna x y

-------------------------------------------------------------------------------
-- Ejercicio 2 - intercambia
-------------------------------------------------------------------------------

intercambia :: (a,b) -> (b,a)
intercambia (x,y) = (y,x)

-------------------------------------------------------------------------------
-- Ejercicio 3 - ordena2 y ordena3
-------------------------------------------------------------------------------

-- 3.a
ordena2 :: Ord a => (a,a) -> (a,a)
ordena2 (x,y) 
   | x<=y = (x,y)
   | otherwise = (y,x)

{-
ordena2 :: Ord a => (a,a) -> (a,a)
ordena2 (x,y) = (min x y, max x y)
-}

p1_ordena2 x y = enOrden (ordena2 (x,y)) --la salida debe estar ordenada
   where enOrden (x,y) = x <= y

p2_ordena2 x y = mismosElementos (x,y) (ordena2 (x,y))   --la salida debe ser una permutación de la entrada
   where
      mismosElementos (x,y) (x',y') =
           (x == x' && y == y') || (x == y' && y == x')

-- 3.b
ordena3 :: Ord a => (a,a,a) -> (a,a,a)
ordena3 (x,y,z) 
   | x<=u = (x,u,v)
   | x>=v = (u,v,x)
   | otherwise = (u,x,v)
   where (u,v) = ordena2(y,z) 

-- 3.c
p1_ordena3 x y z = u<=v && v<=w -- la salida debe estar ordenada
   where (u,v,w) = ordena3 (x,y,z)

p2_ordena3 x y z = x `pertenece` salida  && y `pertenece` salida && z `pertenece` salida -- completar
   where 
      salida = ordena3 (x,y,z)
      pertenece v (c1,c2,c3) = elem v [c1,c2,c3]   -- ó = v==c1 || v=c2 || v=c3


-------------------------------------------------------------------------------
-- Ejercicio 4 - max2
-------------------------------------------------------------------------------

-- 4.a
max2 :: Ord a => a -> a -> a
max2 x y 
   | x>=y = x
   | otherwise = y

-- 4.b
-- p1_max2: el máximo de dos números x e y coincide o bien con x o bien con y.

p1_max2 x y = z == x || z == y 
   where 
      z=max2 x y 

-- p2_max2: el máximo de x e y es mayor o igual que x y mayor o igual que y.

p2_max2 x y = z>=x && z>=y
   where
      z=max2 x y 

-- p3_max2: si x es mayor o igual que y, entonces el máximo de x e y es x.

p3_max2 x y = x>=y ==> max2 x y == x     -- ==> es un filtro("entonces"), de esa forma el tipo de esta función es Property

-- p4_max2: si y es mayor o igual que x, entonces el máximo de x e y es y.

p4_max2 x y = y>=x ==> max2 x y ==y -- completar

-------------------------------------------------------------------------------
-- Ejercicio 5 - entre (resuelto, se usa en el ejercicio 7)
-------------------------------------------------------------------------------

entre :: Ord a => a -> (a, a) -> Bool
entre x (inf,sup) = inf <= x && x <= sup


-------------------------------------------------------------------------------
-- Ejercicio 6 - iguales3
-------------------------------------------------------------------------------

iguales3 :: Eq a => (a,a,a) -> Bool
iguales3 (x,y,z) = x==y && y==z

-------------------------------------------------------------------------------
-- Ejercicio 7 - descomponer
-------------------------------------------------------------------------------

-- Para este ejercicio nos interesa utilizar la función predefinida:
--
--              divMod :: Integral a => a -> a -> (a, a)
--
-- que calcula simultáneamente el cociente y el resto de la división entera:
--
--   *Main> divMod 30 7
--   (4,2)

-- 7.a
type TotalSegundos = Integer
type Horas         = Integer
type Minutos       = Integer
type Segundos      = Integer       --sinónimo de tipo, no son nuevos tipos

descomponer :: TotalSegundos -> (Horas,Minutos,Segundos)
descomponer x = (h,m,s)  
   where
      (h,ms) = divMod x 3600
      (m,s) = divMod ms 60
      
-- 7.b
p_descomponer x = x>=0 ==> h*3600 + m*60 + s == x
                           && m `entre` (0,59)
                           && s `entre` (0,59)
          where (h,m,s) = descomponer x

-------------------------------------------------------------------------------
-- Ejercicio 8 - pesetasAEuros
-------------------------------------------------------------------------------

--8.a
unEuro :: Double
unEuro = 166.386

pesetasAEuros :: Double -> Double 
pesetasAEuros x = x/unEuro

--8.b
eurosAPesetas :: Double -> Double
eurosAPesetas x = x*unEuro

--8.c
-- p_inversas x = eurosAPesetas (pesetasAEuros x) == x   --(~=) para el símbolo pulsasr ALT GR + 4
p_inversas x = eurosAPesetas (pesetasAEuros x) ~= x 


-- El QuickCheck falla porque nos desentendemos de lo que pueda
-- ocurrir en los decimales más pequeños ya que es un número flotante

-------------------------------------------------------------------------------
-- Ejercicio 9 - infix
-------------------------------------------------------------------------------

infix 4 ~=
(~=) :: Double -> Double -> Bool
x ~= y = abs(x-y) < epsilon
   where
      epsilon = 1/1000

-------------------------------------------------------------------------------
-- Ejercicio 10 - raices
-------------------------------------------------------------------------------

raices :: Double -> Double -> Double -> (Double,Double)
raices a b c 
   | discriminante < 0 = error "Raíces no reales"
   | otherwise = ((-b+raizDisc)/denominador, (-b-raizDisc)/denominador)
      where
         discriminante = b*b - 4*a*c
         raizDisc = sqrt discriminante
         denominador = 2*a 


p1_raices a b c = esRaiz r1 && esRaiz r2
   where
      (r1,r2) = raices a b c
      esRaiz r = (a*r*r + b*r + c) ~= 0


-- Falla ya que suponemos que el discriminante es positivo 
-- y denominador distinto de 0

p2_raices a b c = a/=0 && b*b - 4*a*c>=0 ==> esRaiz r1 && esRaiz r2
   where
      (r1,r2) = raices a b c
      esRaiz r = (a*r^2 + b*r + c) ~= 0


-------------------------------------------------------------------------------
-- Ejercicio 11 - esMultiplo
-------------------------------------------------------------------------------

esMultiplo :: Integer -> Integer -> Bool
esMultiplo x y = x `mod` y == 0

-------------------------------------------------------------------------------
-- Ejercicio 12 - implicacion logica
-------------------------------------------------------------------------------

infix 1 ==>>
(==>>) :: Bool -> Bool -> Bool
False ==>> y = True
True ==>> y = y

-------------------------------------------------------------------------------
-- Ejercicio 13 - esBisiesto
-------------------------------------------------------------------------------

esBisiesto :: Int -> Bool
esBisiesto x 
   | (mod x 4 == 0) && not(mod x 100 == 0) = True
   | (mod x 4 == 0) && (mod x 100 == 0) = mod x 400 == 0
   | otherwise = False

-------------------------------------------------------------------------------
-- Ejercicio 14 - potencia
-------------------------------------------------------------------------------

-- 14.a
potencia :: Integer -> Integer -> Integer
potencia _ 0 = 1 
potencia b n = b * potencia b (n-1)

-- 14.b
potencia' :: Integer -> Integer -> Integer
potencia' _ 0 = 1
potencia' b n 
   | even n = pot1*pot1                       -- even n, para n par
   | otherwise = b*pot1*pot1
   where 
      pot1=potencia' b (n `div` 2)

-- 14.c
p_pot b n =
   potencia b m == sol && potencia' b m == sol
   where sol = b^m
         m = abs n


-------------------------------------------------------------------------------
-- Ejercicio 15 - factorial
-------------------------------------------------------------------------------

factorial :: Integer -> Integer
factorial 0 = 1
factorial x = factorial(x-1) * x 

-------------------------------------------------------------------------------
-- Ejercicio 16 - divideA
-------------------------------------------------------------------------------

--16.a
divideA :: Int -> Int -> Bool
divideA x y = (mod y x == 0)

--16.b
p1_divideA x y = y/=0 && y `divideA` x ==> (div x y) * y == x

--16.c
p2_divideA x y z = y/=0 && divideA y z && divideA y x ==> divideA y (x+z)


-------------------------------------------------------------------------------
-- Ejercicio 17 - mediana
-------------------------------------------------------------------------------

mediana :: Ord a => (a,a,a,a,a) -> a
mediana (x,y,z,t,u)
   | x > z = mediana (z,y,x,t,u)
   | y > z = mediana (x,z,y,t,u)
   | t < z = mediana (x,y,t,z,u)
   | u < z = mediana (x,y,u,t,z)
   | otherwise = z




