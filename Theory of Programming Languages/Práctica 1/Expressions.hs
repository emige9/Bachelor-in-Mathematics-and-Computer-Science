{-

Programming Languages
Fall 2023

Semantics of Expressions

-}

module Expressions where

import           Aexp
import           Bexp
import           State

import Data.List


import           Test.HUnit hiding (State)

-- |----------------------------------------------------------------------
-- | Exercise 1 - Semantics of binary numerals
-- |----------------------------------------------------------------------
-- | Given the algebraic data type 'Bin' for the binary numerals:

data Bit = O
         | I
         deriving (Eq, Show)

data Bin = MSB Bit
         | B Bin Bit
         deriving (Eq, Show)

-- | and the following values of type 'Bin':

zero :: Bin
zero = MSB O

one :: Bin
one = MSB I

three :: Bin
three = B (B (MSB O) I) I

six :: Bin
six = B (B (MSB I) I) O

-- | define a semantic function 'binVal' that associates
-- | a number (in the decimal system) to each binary numeral.

binVal :: Bin -> Z
binVal (MSB bit) = bitVal bit
binVal (B bits bit) = 2*binVal bits + bitVal bit
-- binVal (MSB bit) = f bit  -- ######## mi solucion #########
--     where 
--         f O = 0
--         f I = 1
-- binVal (MSB bit) = bitVal bit
-- binVal (B b x) = fn x b
--     where
--         fn O b = 2*binVal b
--         fn I b = 2*binVal b + 1


bitVal :: Bit -> Z
bitVal O = 0
bitVal I = 1

-- | Test your function with HUnit.

testBinVal :: Test
testBinVal = test ["value of zero"  ~: 0 ~=? binVal zero,
                   "value of one"   ~: 1 ~=? binVal one,
                   "value of three" ~: 3 ~=? binVal three,
                   "value of six"   ~: 6 ~=? binVal six]

-- | Define a function 'foldBin' to fold a value of type 'Bin'

foldBin :: (a -> Bit -> a) -> (Bit -> a) -> Bin -> a
foldBin b msb bits = plegar bits
    where 
        plegar (MSB bit) = msb bit
        plegar (B bits bit) = b (plegar bits) bit

-- foldBin :: (Bit -> b -> b) -> (Bit -> b) -> Bin -> b  -- #### mi solucion ####
-- foldBin fn f bin = plegado bin 
--     where
--         plegado (MSB x) = f x 
--         plegado (B b x) = fn x (plegado b)

-- | and use 'foldBin' to define a function 'binVal''  equivalent to 'binVal'.

binVal' :: Bin -> Integer
binVal' bits = foldBin (\ n bit -> 2 * n + bitVal bit) bitVal bits
-- binVal' x = foldBin fn f x           --- #### mi solucion ####
--     where
--         f O = 0
--         f I = 1
--         fn b O = 2*b
--         fn b I = 2*b + 1  

-- | Test your function with HUnit.

testBinVal' :: Test
testBinVal' = test ["value of zero"  ~: 0 ~=? binVal' zero,
                   "value of one"   ~: 1 ~=? binVal' one,
                   "value of three" ~: 3 ~=? binVal' three,
                   "value of six"   ~: 6 ~=? binVal' six]

-- | Define a function 'normalize' that given a binary numeral trims leading zeroes.

normalize :: Bin -> Bin
normalize msb@(MSB _) = msb                             -- Con el @ creamos un alias para no tener que volver a escribir lo mismo
normalize (B bits bit) = addBit (normalize bits) bit

addBit :: Bin -> Bit -> Bin
addBit (MSB O) bit = MSB bit
addBit bits bit = B bits bit

-- | and use 'foldBin' to define a function 'normalize''  equivalent to 'normalize'.

normalize' :: Bin -> Bin
normalize' bits = foldBin addBit MSB bits

-- | Test your functions with HUnit.

-- todo

-- |----------------------------------------------------------------------
-- | Exercise 2 - Free variables of expressions
-- |----------------------------------------------------------------------
-- | Define the function 'fvAexp' that computes the set of free variables
-- | occurring in an arithmetic expression. Ensure that each free variable
-- | occurs once in the resulting list.

-- data  Aexp  =  N NumLit
--             |  V Var
--             |  Add Aexp Aexp
--             |  Mult Aexp Aexp
--             |  Sub Aexp Aexp
--             deriving (Show, Eq)

fvAexp :: Aexp -> [Var]
fvAexp (N _) = []
fvAexp (V var) = [var]
fvAexp (Add a1 a2) = union (fvAexp a1) (fvAexp a2)
fvAexp (Mult a1 a2) = union (fvAexp a1) (fvAexp a2)
fvAexp (Sub a1 a2) = union (fvAexp a1) (fvAexp a2)

-- | Test your function with HUnit.

-- todo

-- | Define the function 'fvBexp' that computes the set of free variables
-- | occurring in a Boolean expression.

-- data  Bexp  =  TRUE
--             |  FALSE
--             |  Equ Aexp Aexp
--             |  Leq Aexp Aexp
--             |  Neg Bexp
--             |  And Bexp Bexp
--             deriving (Show, Eq)

fvBexp :: Bexp -> [Var]
fvBexp TRUE = []
fvBexp FALSE = []
fvBexp (Equ a1 a2) = fvAexp a1 `union` fvAexp a2
fvBexp (Leq a1 a2) = fvAexp a1 `union` fvAexp a2
fvBexp (Neg b1) = fvBexp b1
fvBexp (And b1 b2) = fvBexp b1 `union` fvBexp b2

-- | Test your function with HUnit.

testFvBexp :: Test
testFvBexp = test[
    "algo igual a algo" ~: 3 ~=? 3]

-- |----------------------------------------------------------------------
-- | Exercise 3 - Substitution of variables in expressions
-- |----------------------------------------------------------------------
-- | Given the algebraic data type 'Subst' for representing substitutions:

data Subst = Var :->: Aexp

-- | define a function 'substAexp' that takes an arithmetic expression
-- | 'a' and a substitution 'y:->:a0' and returns the substitution a [y:->:a0];
-- | i.e., replaces every occurrence of 'y' in 'a' by 'a0'.

substAexp :: Aexp -> Subst -> Aexp
substAexp a@(N n) _ = a
substAexp (V var) (y:->:b) = (substitute var y b)
substAexp (Add a1 a2) sub = Add (substAexp a1 sub) (substAexp a2 sub)
substAexp (Mult a1 a2) sub = Mult (substAexp a1 sub) (substAexp a2 sub)
substAexp (Sub a1 a2) sub = Sub (substAexp a1 sub) (substAexp a2 sub)

substitute :: Var -> Var -> Aexp -> Aexp
substitute x y a0 
    | x == y = a0
    | otherwise = V y 

-- | Test your function with HUnit.

-- todo

-- | Define a function 'substBexp' that implements substitution for
-- | Boolean expressions.

substBexp :: Bexp -> Subst -> Bexp
substBexp TRUE subs = TRUE
substBexp FALSE subs = FALSE
substBexp (Equ a1 a2) subs = Equ (substAexp a1 subs) (substAexp a2 subs)
substBexp (Leq a1 a2) subs = Leq (substAexp a1 subs) (substAexp a2 subs)
substBexp (Neg b1) subs = Neg (substBexp b1 subs)
substBexp (And b1 b2) subs = And (substBexp b1 subs) (substBexp b2 subs)

-- | Test your function with HUnit.

-- todo

-- |----------------------------------------------------------------------
-- | Exercise 4 - Update of state
-- |----------------------------------------------------------------------
-- | Given the algebraic data type 'Update' for state updates:

data Update = Var :=>: Z

-- | define a function 'update' that takes a state 's' and an update 'x :=> v'
-- | and returns the updated state 's [x :=> v]'

update :: State -> Update -> State
update s (y:=>:v) = s' 
    where 
        s' x
            | x == y = v 
            | otherwise = s x

-- | Test your function with HUnit.

-- todo

-- | Define a function 'updates' that takes a state 's' and a list of updates
-- | 'us' and returns the updated states resulting from applying the updates
-- | in 'us' from head to tail. For example:
-- |
-- |    updates s ["x" :=>: 1, "y" :=>: 2, "x" :=>: 3]
-- |
-- | returns a state that binds "x" to 3 (the most recent update for "x").

updates :: State ->  [Update] -> State
updates s [] = s
updates s (x:xs) = updates (update s x) xs

-- | Test your function with HUnit.

-- todo

-- |----------------------------------------------------------------------
-- | Exercise 5 - Folding expressions
-- |----------------------------------------------------------------------
-- | Define a function 'foldAexp' to fold an arithmetic expression

foldAexp :: (NumLit -> b) -> (Var -> b) -> (b -> b -> b) -> (b -> b -> b) -> (b -> b -> b) -> Aexp -> b
foldAexp fn fv fAdd fMul fSub exp = plegar exp
    where 
        plegar (N n) = fn n 
        plegar (V x) = fv x 
        plegar (Add a1 a2) = fAdd (plegar a1) (plegar a2)
        plegar (Mult a1 a2) = fMul (plegar a1) (plegar a2)
        plegar (Sub a1 a2) = fSub (plegar a1) (plegar a2)

-- | Use 'foldAexp' to define the functions 'aVal'', 'fvAexp'', and 'substAexp''.

aVal' :: Aexp -> State -> Z
aVal' exp s = foldAexp numLit s (+) (*) (-) exp

fvAexp' :: Aexp -> [Var]
fvAexp' exp = foldAexp (\_ -> []) (\x -> [x]) union union union exp 

substAexp' :: Aexp -> Subst -> Aexp
substAexp' exp (y:->:b) = foldAexp N (\x -> substitute x y b) Add Mult Sub exp 

-- | Test your functions with HUnit.

-- todo

-- | Define a function 'foldBexp' to fold a Boolean expression and use it
-- | to define the functions 'bVal'', 'fvBexp'', and 'substAexp''.

foldBexp :: b -> b -> (Aexp -> Aexp -> b) -> (Aexp -> Aexp -> b) -> (b -> b) -> (b -> b -> b) -> Bexp -> b
foldBexp tt ff fEq fLeq fNeg fAnd exp = plegar exp
    where 
        plegar TRUE = tt
        plegar FALSE = ff
        plegar (Equ a1 a2) = fEq a1 a2
        plegar (Leq a1 a2) = fLeq a1 a2
        plegar (Neg b)     = fNeg (plegar b)
        plegar (And b1 b2) = fAnd (plegar b1) (plegar b2)

bVal' :: Bexp -> State -> Bool
bVal' exp s = foldBexp True False (\ a1 a2 -> (aVal' a1 s) == (aVal' a2 s)) (\ a1 a2 -> (aVal' a1 s) <= (aVal' a2 s)) (not) (&&) exp

fvBexp' :: Bexp -> [Var]
fvBexp' exp = foldBexp [] [] (\a1 a2 -> union (fvAexp' a1) (fvAexp' a2)) (\a1 a2 -> union (fvAexp' a1) (fvAexp' a2)) id union exp

substBexp' :: Bexp -> Subst -> Bexp
substBexp' = undefined

-- | Test your functions with HUnit.

-- todo
