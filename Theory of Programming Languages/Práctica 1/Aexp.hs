{-

Programming Languages
Fall 2024

Semantics of Arithmetic Expressions

-}



module Aexp where

import Data.Set as Set


-- |----------------------------------------------------------------------
-- | Exercise 1 - Abstract Syntax and Semantics of Aexp
-- |----------------------------------------------------------------------
-- | Define the algebraic data type 'Aexp' for representing arithmetic
-- | expressions.

type VarId = String
type LitNum = String   

data  Aexp  =  NumLit LitNum 
            |  Var VarId  
            |  Add Aexp Aexp 
            |  Mul Aexp Aexp 
            |  Sub Aexp Aexp
            deriving Show 

exp0 :: Aexp 
exp0 = Add (NumLit "3") (Var "x")


-- (x * 3) + (y - 5)
exp1 :: Aexp 
exp1 = Add (Mul (Var "x") (NumLit "3")) (Sub (Var "y") (NumLit "5"))


-- | Define the function 'aval' that computes the value of an arithmetic
-- | expression in a given state.


type Z = Integer 

type State = VarId -> Z 


-- s0 = { x -> 5, y -> 7, z -> -2 }
s0 :: State
s0 "x" = 5 
s0 "y" = 7
s0 "z" = -2
s0 _ = 0 

nVal :: LitNum -> Z 
nVal n = read n                   -- :: Integer               funciona como Integer.ParseInt(String)

aVal :: Aexp -> State -> Z
aVal (NumLit n) s = nVal n
aVal (Var x) s = s x 
aVal (Add a1 a2) s = (aVal a1 s) + (aVal a2 s)
aVal (Mul a1 a2) s = (aVal a1 s) * (aVal a2 s)
aVal (Sub a1 a2) s = (aVal a1 s) - (aVal a2 s) 

-- |----------------------------------------------------------------------
-- | Exercise 2 - Free variables of expressions
-- |----------------------------------------------------------------------
-- | Define the function 'fvAexp' that computes the set of free variables
-- | occurring in an arithmetic expression. Ensure that each free variable
-- | occurs only once in the resulting list.

fvAexp :: Aexp -> Set VarId 
fvAexp (NumLit n) = Set.empty 
fvAexp (Var x) = Set.insert x Set.empty  
fvAexp (Add a1 a2) = Set.union (fvAexp a1) (fvAexp a2)
fvAexp (Mul a1 a2) = Set.union (fvAexp a1) (fvAexp a2)
fvAexp (Sub a1 a2) = Set.union (fvAexp a1) (fvAexp a2) 






-- |----------------------------------------------------------------------
-- | Exercise 3 - Substitution of variables in expressions
-- |----------------------------------------------------------------------
-- | Define the algebraic data type 'Subst' for representing substitutions.

data Subst = VarId :->: Aexp

-- | Define a function 'substAexp' that takes an arithmetic expression
-- | 'a' and a substitution 'y -> a0' and returns the substitution 'a [y -> a0]';
-- | i.e., replaces every occurrence of 'y' in 'a' by 'a0'.

substAexp :: Aexp -> Subst -> Aexp
substAexp (NumLit a) (v :->: a1) = NumLit a 
substAexp (Var x) (v :->: a1) = if x == v then a1 else Var x 
substAexp (Add a1 a2) sus = Add (substAexp a1 sus) (substAexp a2 sus)
substAexp (Mul a1 a2) sus = Mul (substAexp a1 sus) (substAexp a2 sus)
substAexp (Sub a1 a2) sus = Sub (substAexp a1 sus) (substAexp a2 sus)

substitute :: VarId -> VarId -> Aexp -> Aexp
substitute x y a0 
    | x == y = a0
    | otherwise = Var y




-- |----------------------------------------------------------------------
-- | Exercise 4 - Update of state
-- |----------------------------------------------------------------------
-- | Define the algebraic data type 'Update' for representing state updates.

data Update = VarId :=>: Z

-- | Define a function 'update' that takes a state 's' and an update 'x -> v'
-- | and returns the updated state 's [x -> v]'

update :: State -> Update -> State 
update s (y :=>: v) = (\ x -> if x == y then v else s x)
-- update s (y :=>: v) x = if x == y then v else s x 

--  s1 = update s0 ("x" :=>: (-17)) "x"

-- | Define a function 'updates' that takes a state 's' and a list of updates
-- | 'us' and returns the updated states resulting from applying the updates
-- | in 'us' from head to tail. For example:
-- |
-- |    updates s {x -> 1, y > 2, x -> 3}
-- |
-- | returns a state that binds 'x' to 3 (the most recent update for 'x').


updates' :: State ->  [Update] -> State
updates' s [] = s
updates' s (x:xs) = updates' (update s x) xs

-- |----------------------------------------------------------------------
-- | Exercise 5 - Folding expressions
-- |----------------------------------------------------------------------
-- | Define a function 'foldAexp' to fold an arithmetic expression.

foldAexp :: (LitNum -> b) -> (VarId -> b) -> (b -> b -> b) -> (b -> b -> b) -> (b -> b -> b) -> Aexp -> b 
foldAexp nl v add mul sub a = recAexp a 
    where 
        recAexp (NumLit n) = nl n 
        recAexp (Var x) = v x 
        recAexp (Add a1 a2) = add (recAexp a1) (recAexp a2) 
        recAexp (Mul a1 a2) = mul (recAexp a1) (recAexp a2) 
        recAexp (Sub a1 a2) = sub (recAexp a1) (recAexp a2)  

-- | Use 'foldAexp' to define the functions 'aVal', 'fvAexp', and 'substAexp'.

aVal' :: Aexp -> State -> Z
aVal' a s = foldAexp nVal s (+) (*) (-) a 

fvAexp' :: Aexp -> [VarId]
fvAexp' exp = foldAexp (\_ -> []) (\x -> [x]) (++) (++) (++) exp 

substAexp' :: Aexp -> Subst -> Aexp 
substAexp' a (x:->:a0) = foldAexp NumLit substVar Add Mul Sub a 
    where 
        -- substVar :: VarId -> Aexp 
        substVar y = if y == x then a0 else Var y 


-- |----------------------------------------------------------------------
-- | Exercise 6 - Semantics of binary numerals
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