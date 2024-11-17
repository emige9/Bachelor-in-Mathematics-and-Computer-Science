module Seq where 

import Data.List (intercalate)
import Test.QuickCheck

data Seq a = Empty
            | Node a (Seq a)

testseq1 :: Seq Int 
testseq1 = Node 10 (Node 40 (Node 30 Empty))

empty :: Seq a
empty = Empty

isEmpty :: Seq a -> Bool
isEmpty Empty = True
isEmpty _ = False

size :: Seq a -> Int 
size Empty = 0
size (Node _ xs) = 1 + size xs 

insert :: (Ord a) => Int -> a -> Seq a -> Seq a
insert 0 x xs = Node x xs
insert k x (Node y ys) 
    | k<0 || k > size (Node y ys) = error "wrong index"
    | otherwise = (Node y (insert (k-1) x ys))

get :: Int -> Seq a -> a 
get 0 (Node x xs) = x 
get k (Node y ys) 
    | k<0 || (k > size(Node y ys)-1) = error "wrong index"
    | otherwise = get (k-1) ys

--invert :: Seq a -> Seq a 



instance (Show a) => Show (Seq a) where
    show q ="LinearSeq(" ++ intercalate "," (aux q) ++ ")"
     where
         aux Empty = []
         aux (Node x q) = show x : aux q 

