{-
Estructura de Datos
2º Matemáticas + Informática

Práctica Evaluable - noviembre 2020

Apellidos, Nombre: Gómez Esteban, Emilio

-}

module SetMultiMap (SetMultiMap
                    , empty
                    , isEmpty
                    , isDefinedAt
                    , size
                    , insert
                    , deleteKey
                    , deleteKeyValue
                    , valuesOf
                    , filterValues
                    , fold
                    ) where

import          Data.List    (intercalate)
import          Test.QuickCheck

import qualified DataStructures.Set.LinearSet as S 

-- Invariante de representación:
--  - Los nodos están ordenados por clave
--  - No hay claves repetidas
--  - No hay claves que tengan asociado un conjunto vacío

data SetMultiMap a b = Empty
                     | Node a (S.Set b) (SetMultiMap a b)
                     deriving Eq 

-- Ejemplo de SetMultiMap para probar las funciones

m1 :: SetMultiMap String Int
m1 = Node "alfredo" (mkSet [9])(
     Node "juan" (mkSet [0, 1, 8])(
     Node "maria" (mkSet [4, -6, 8]) 
     Empty))

mkSet :: Eq a => [a] -> S.Set a
mkSet xs = foldr S.insert S.empty xs

-- | Ejercicio 1 - Definición de operaciones y complejidad
--------------------------------------------------------------------------

-- 0,25 puntos
-- |
-- >>> empty
-- {}

-- Complejidad: 0(1)
empty :: SetMultiMap a b
empty = Empty

-- 0,25 puntos
-- |
-- >>> isEmpty m1
-- False

-- Complejidad: O(1)
isEmpty :: SetMultiMap a b -> Bool
isEmpty Empty = True
isEmpty _ = False

-- 1 punto
-- |
-- >>> size m1
-- 3

--Complejidad: O(1)
size :: SetMultiMap a b -> Integer
size Empty = 0
size (Node _ _ ym) = 1 + size ym

-- 1 punto
-- |
-- >>> isDefinedAt "maria" m1
-- True
--
-- >>> isDefinedAt "eva" m1
-- False

-- Complejidad: O(n)
isDefinedAt :: (Ord a, Eq a) => a -> SetMultiMap a b -> Bool
isDefinedAt _ Empty = False
isDefinedAt x (Node y _ ym) = (x == y) || isDefinedAt x ym

-- 1 punto
-- |
-- >>> insert "alfredo" 5 m1
-- "alfredo" --> LinearSet (9,5)
-- "juan" --> LinearSet(8,1,0)
-- "maria" --> LinearSet(8,-6,4)
--
-- >>> insert "carmen" 20 m1
-- "alfredo" --> LinearSet (9)
-- "carmen" --> LinearSet (20)
-- "juan" --> LinearSet(8,1,0)
-- "maria" --> LinearSet(8,-6,4)

-- Complejidad: O(n)
insert :: (Ord a, Eq b) => a -> b -> SetMultiMap a b -> SetMultiMap a b 
insert k v Empty = Node k (S.insert v S.empty) Empty
insert k v (Node x  xs ys) 
    | k < x = Node k (S.insert v S.empty) (Node x xs ys)
    | k == x = Node k (S.insert v xs) ys
    | otherwise = Node x xs (insert k v ys)


-- 1 punto
-- |
-- >>> deleteKey "juan" m1
-- "alfredo" --> LinearSet(9)
-- "maria" --> LinearSet(8,-6,4)

-- Complejidad: O(n)
deleteKey :: (Ord a, Eq b) => a -> SetMultiMap a b -> SetMultiMap a b 
deleteKey k Empty = Empty
deleteKey k (Node x xs ys) 
    | k == x = ys
    | otherwise = Node x xs (deleteKey k ys) 

-- 1 punto
-- |
-- >>> deleteKeyValue "maria" 4 m1
-- "alfredo" --> LinearSet(9)
-- "juan" --> LinearSet(8,1,0)
-- "maria" --> LinearSet(8,-6)

-- Complejidad: O(n)
deleteKeyValue :: (Ord a, Eq b) => a -> b -> SetMultiMap a b -> SetMultiMap a b 
deleteKeyValue _ _ Empty = Empty
deleteKeyValue k v (Node x xs ys)
    | k == x = if(S.size(S.delete v xs) == 0) then ys else Node x (S.delete v xs) ys 
    | otherwise = Node x xs (deleteKeyValue k v ys)

-- 1 punto
-- |
-- >>> valuesOf "maria" m1
-- Just LinearSet(8,-6,4)
--
-- >>> valuesOf "paco" m1
-- Nothing

-- Complejidad: O(n)
valuesOf :: (Ord a, Eq b) => a -> SetMultiMap a b -> Maybe(S.Set b)
valuesOf _ Empty = Nothing
valuesOf k (Node x xs ys)
    | k == x = Just xs
    | otherwise = valuesOf k ys 

-- 1,25 puntos
-- | 
-- >>> filterValues (> 0) m1
-- "alfredo" --> LinearSet(9)
-- "juan" --> LinearSet(8,1)
-- "maria" --> LinearSet(8,4)

-- Complejidad: O(n)
filterValues :: (Ord a, Eq b) => (b -> Bool) -> SetMultiMap a b -> SetMultiMap a b 
filterValues _ Empty = Empty
filterValues p (Node x xs ys) 
    | funcional == S.empty = filterValues p ys 
    | otherwise = Node x funcional (filterValues p ys)  
    where 
        funcional = (S.fold (\ s q -> if p s then S.insert s q else q) S.empty xs)
-- S.insert devuelve el Set con los valores de las claves ordenados

-- | Ejercicio 2 - Axiomas del TAD
--------------------------------------------------------------

-- 1 punto
-- | completa los axiomas que definen deleteKeyValue

ax_deleteKeyValue_empty x y = deleteKeyValue x y empty == empty 
ax_deleteKeyValue_1 k v q = not(isDefinedAt k q) ==> deleteKeyValue k v q == q 

----------------- NO EDITAR EL CÓDIGO DE ABAJO ---------------

fold :: (Ord a, Eq b) => (a -> b -> c -> c) -> c -> SetMultiMap a b -> c 
fold f z ms = recSetMultiMap ms
    where
        recSetMultiMap Empty = z 
        recSetMultiMap (Node k s ms)
            | S.isEmpty s = recSetMultiMap ms 
            | otherwise = f k v (recSetMultiMap(Node k s' ms))
                where 
                    (v, s') = pickOne s 
                    pickOne s = (v, S.delete v s)
                        where 
                            v = head $ S.fold (:) [] s 

instance (Show a, Show b) => Show(SetMultiMap a b) where
    show Empty = "{}"
    show ms = intercalate "\n" (showKeyValues ms)
        where
            showKeyValues Empty = []
            showKeyValues (Node k s ms) = (show k ++ " --> " ++ show s) : showKeyValues ms 

instance (Ord a, Arbitrary a, Eq b, Arbitrary b) => Arbitrary (SetMultiMap a b)
    where
        arbitrary = do
        xs <- listOf arbitrary
        ys <- listOf arbitrary
        return (foldr (uncurry insert) empty (zip xs ys))