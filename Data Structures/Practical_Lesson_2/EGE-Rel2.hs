-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º ETSI Informática. UMA
-- Titulación: Doble Grado en Matemáticas + Ing. Informática
--
-- Alumno: GÓMEZ ESTEBAN, EMILIO
--
-- Relación de ejercicios 2. Ejercicios resueltos:
-------------------------------------------------------------------------------

module Relacion2 where

import Test.QuickCheck

-------------------------------------------------------------------------------
-- Ejercicio 1 - relación de orden
-------------------------------------------------------------------------------

--1.a
data Direction = North | South | East | West
                    deriving (Eq, Enum, Show)

(<<) :: Direction -> Direction -> Bool
x << y = fromEnum x < fromEnum y

p_menor x y = (x < y) == (x << y)
instance Arbitrary Direction
    where 
        arbitrary = do 
            n <- choose (0,3)
            return $ toEnum n 

--1.b    --Eliminamos la clase Ord en la cláusula deriving
instance Ord Direction where
    North <= _ = True
    South <= North = False
    South <= _ = True
    East <= North = False
    East <= South = False
    East <= _ = True
    West <= North = False
    West <= South = False
    West <= East = False
    West <= _ = True


-------------------------------------------------------------------------------
-- Ejercicio 2 - máximoYResto
-------------------------------------------------------------------------------

--2.b
borra :: Eq a => a-> [a] -> [a]
borra m [] = []
borra m (y:ys)  
    | m == y = ys
    | otherwise = y:(borra m ys) 

maximoYResto' :: Ord a => [a] -> (a,[a])
maximoYResto' [] = error "lista vacía"
maximoYResto' xs = (m, borra m xs)
    where 
        m = maximum xs


--2.a
maximoYResto :: Ord a => [a] -> (a,[a])
maximoYResto [] = error "lista vacía"
maximoYResto [x] = (x,[])
maximoYResto (x:xs)
    | x > m = (x, m:rs)
    | otherwise = (m, x:rs)
        where 
           (m,rs) = maximoYResto xs


-------------------------------------------------------------------------------
-- Ejercicio 3 - reparte
-------------------------------------------------------------------------------

reparte :: [a] -> ([a],[a])
reparte ([]) = ([],[])
reparte [x] = ([x],[])
reparte (x:y:ys) = (x:lista1, y:lista2)
    where 
        (lista1,lista2) = reparte ys


-------------------------------------------------------------------------------
-- Ejercicio 4 - distintos
-------------------------------------------------------------------------------

distintos :: Eq a => [a] -> Bool
distintos [] = True
distintos (x:xs) = notElem x xs && distintos xs 

-------------------------------------------------------------------------------
-- Ejercicio 5 - replicate
-------------------------------------------------------------------------------

--5.a
replicate' :: Int -> a -> [a]
replicate' 0 _ = []
replicate' n x = x:(replicate' (n-1) x)

--5.b
p_replicate' n x = n>=0 && n<=1000 ==> 
                length (filter (==x) xs) == n && length (filter (/=x) xs) == 0 
                where xs = replicate' n x 

-------------------------------------------------------------------------------
-- Ejercicio 6 - divisores
-------------------------------------------------------------------------------

--6.a
divideA :: (Integral a) => a -> a -> Bool
divideA x y = (mod y x == 0)

divisores :: (Integral a) => a -> [a]
divisores n = [x | x <-[1..n], x `divideA` n] 

--6.b
divisores' :: (Integral a) => a -> [a]
divisores' n 
    | n>=0 = [x |x<-[-n..n], x/=0, x `divideA` n]
    | otherwise = [x |x<-[n..(-n)], x/=0, x `divideA` n]

-------------------------------------------------------------------------------
-- Ejercicio 7 - maximo comun divisor
-------------------------------------------------------------------------------

--7.a
mcd :: Integer -> Integer -> Integer
mcd x y = maximum (divisoresComunes x y)

divisoresComunes :: Integer -> Integer -> [Integer]
divisoresComunes n1 n2 = [x | x<-divisores' n1, x<-divisores' n2,
                            x `divideA` n1, x `divideA` n2]

--7.b
p_mcd x y z = x/=0 && y/=0 && z/=0 ==> mcd (z*x) (z*y) == (abs z)*mcd x y 


--7.c
mcm :: Integer -> Integer -> Integer
mcm x y 
    | x==0 || y==0 = 0
    | otherwise  = div (x*y) (mcd x y)

-------------------------------------------------------------------------------
-- Ejercicio 8 - primosHasta
-------------------------------------------------------------------------------

--8.a
esPrimo :: Integer -> Bool
esPrimo n 
    | n<=0 = error "Argumento negativo o cero"
    | n==1 = False
    | otherwise = divisores n == [1,n]


--8.b
primosHasta :: Integer -> [Integer]
primosHasta n = [x | x<-[1..n], esPrimo x]

--8.c
primosHasta' :: Integer -> [Integer]
primosHasta' n = filter (\ x -> esPrimo x) [1..n]

--8.d
p1_primos x = primosHasta x == primosHasta' x 

-------------------------------------------------------------------------------
-- Ejercicio 9 - conjetura de Goldbach
-------------------------------------------------------------------------------

--9.a
paresNumeros :: Integer -> [(Integer,Integer)]
paresNumeros n = [(x,y) | x <- [1..n-2], y <- [x..n-2], esPrimo x, esPrimo y, x+y == n]

--9.b
goldbach :: Integer -> Bool
goldbach n 
    | n>2 && even n && not (null (paresNumeros n)) = True
    | otherwise = False

--9.c
goldbachHasta :: Integer -> Bool
goldbachHasta n = and ([goldbach x | x <- [2..n], even x])