-------------------------------------------------------------------
-- Estructuras de Datos
-- Grados en Ingeniería Informática, del Software y de Computadores
-- Tema 2. Características de la Programación Funcional
-- Pablo López
-------------------------------------------------------------------

module RecursividadListas where

{-

   * Ejercicios de recursividad sobre listas:

   En los siguientes ejercicios tienes que aplicar recursividad sobre
   listas. Utiliza definiciones con patrones tal y como hemos hecho en
   clase. Intenta utilizar el número mínimo de ecuaciones posibles.

-}

solCasoBase = undefined
añadir cabeza solCola = undefined


-- |
-- >>> producto [1,2,3,4,5]
-- 120
producto :: [Int] -> Int -- predefinida como product
producto [] = 1
producto (x:xs) = x * (producto xs)

-- |
-- >>> conjunción [True, 1 < 2, 'a' == 'a']
-- True
-- conjunción [True, 1 < 2, 'a' == 'b']
-- False
conjunción :: [Bool] -> Bool -- predefinida como and
conjunción [] = True
conjunción (x:xs) = x && (conjunción xs)  

-- |
-- aplana [ [1,2], [3], [], [4,5,6]]
-- [1,2,3,4,5,6]
aplana :: [[a]] -> [a] -- predefinida como concat
aplana [] = []
aplana (xs:xss) = xs ++ (aplana xss)

-- |
-- >>> pertenece 4 [1,2,3,4,5]
-- True
-- >>> pertenece 't' "Haskell"
-- False
pertenece :: Eq a => a -> [a] -> Bool -- predefinida como elem
pertenece _ [] = False
pertenece x (y:ys) = x==y || (pertenece x ys)    -- ó es la cabeza ó es la cola

-- |
-- >>> borraTodas 1 [1,2,1,3,3,1,2,1]
-- [2,3,3,2]
-- >>> borraTodas 't' "Haskell"
-- "Haskell"
borraTodas :: Eq a => a -> [a] -> [a]
borraTodas _ [] = []
borraTodas x (y:ys) = f y (borraTodas x ys)
   where 
      f cabeza solCola
         | cabeza == x = solCola
         | otherwise = y : solCola

-- |
-- >>> paresImpares [1,2,3,4,5,6,7,8,9]
-- ([2,4,6,8],[1,3,5,7,9])
paresImpares :: [Int] -> ([Int], [Int])
paresImpares [] = ([], [])
paresImpares (x:xs)
   | even x = (x:ps,is)
   | otherwise = (ps,x:is)
   where
      (ps,is) = paresImpares xs

