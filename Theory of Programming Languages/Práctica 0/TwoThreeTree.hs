{-# LANGUAGE DeriveFoldable #-}
{-# LANGUAGE DeriveFunctor  #-}

module TwoThreeTree where
--import Seq (plegar)
import Distribution.Simple.Utils (xargs)


data TwoThreeTree a = Empty
                    | Leaf a
                    | Two a (TwoThreeTree a) (TwoThreeTree a)
                    | Three a a (TwoThreeTree a)  (TwoThreeTree a) (TwoThreeTree a)
                   deriving (Show, Functor, Foldable)


tree :: TwoThreeTree Int
tree = Three 1 10
             (Two 2
                  (Leaf 3)
                  (Two 4
                       Empty
                       (Leaf 4)))
             (Three 5 50
                 (Leaf 6)
                 (Leaf 7)
                 (Leaf 8))
             (Two 9
                 (Leaf 10)
                 Empty
             )

aplica :: (a -> b) -> TwoThreeTree a -> TwoThreeTree b
aplica _ Empty = Empty
aplica f (Leaf x) = Leaf (f x)
aplica f (Two x l r) = Two (f x) (aplica f l) (aplica f r)
aplica f (Three x y l m r) = Three (f x) (f y) (aplica f l) (aplica f m) (aplica f r)

plegar :: (a -> a-> b-> b -> b -> b) -> (a -> b -> b -> b) -> (a -> b) -> b -> TwoThreeTree a -> b
plegar f3 f2 fl solEmpty t = rec23Tree t
    where 
        rec23Tree Empty = solEmpty
        rec23Tree (Leaf x) = fl x
        rec23Tree (Two x l r) = f2 x (rec23Tree l) (rec23Tree r)
        rec23Tree (Three x y l m r) = f3 x y (rec23Tree l) (rec23Tree m) (rec23Tree r)

sumar :: TwoThreeTree Int -> Int
sumar  = plegar (\ x y sl sm sr -> x + y + sl + sm + sr) (\ x sl sr -> x + sl + sr) id 0