{-|

Programming Languages
Fall 2024

Implementation of the Structural Operational Semantics of the WHILE Language

Author:

-}

module StructuralSemantics where

import           Aexp
import           Bexp
import           State
import           While

-- representation of configurations for WHILE

data Config = Inter Stm State  -- <S, s>
            | Final State      -- s
            | Stuck Stm State  -- <S, s>

isFinal :: Config -> Bool
isFinal (Final _) = True
isFinal _         = False

isInter :: Config -> Bool
isInter (Inter _ _) = True
isInter _           = False

isStuck :: Config -> Bool
isStuck (Stuck _ _) = True
isStuck _           = False

-- representation of the transition relation <S, s> => gamma

sosStm :: Config -> Config

-- x := a

sosStm (Inter (Ass x a) s) = Final (update s x (aVal a s))
    where 
        update s x v = \ y -> if y == x then v else s y

-- skip

sosStm (Inter skip s) = Final s

-- s1; s2

sosStm (Inter (Comp ss1 ss2) s)
    | isInter gamma = Inter (Comp ss1' ss2) s'
    where 
        gamma = sosStm (Inter ss1 s)
        Inter ss1' s' = gamma 

sosStm (Inter (Comp ss1 ss2) s)
    | isFinal gamma = Inter ss2 s'
    where 
        gamma = sosStm (Inter ss1 s)
        Final s' = gamma

sosStm (Inter (Comp ss1 ss2) s)
    | isStuck gamma = Stuck (Comp ss1 ss2) s 
    where 
        gamma = sosStm (Inter ss1 s)
        Stuck ss' s' = gamma

-- if b then s1 else s2

sosStm (Inter (If b ss1 ss2) s)
    | bVal b s = Inter ss1 s 

sosStm (Inter (If b ss1 ss2) s)
    | not (bVal b s) = Inter ss2 s

-- while b do s

sosStm (Inter (While b ss) s) = Inter (If b (Comp ss (While b ss)) Skip) s 

-- repeat s until b

sosStm (Inter (Repeat stm1 b) s) = Inter (Comp stm1 (If b Skip (Repeat stm1 b))) s

-- for x a1 to a2 s

sosStm (Inter (For x a1 a2 stm) s) = Inter (Comp (Ass x a1) (If (Leq (N (show a1Val)) (N (show a2Val))) (Comp stm (For x (N (show (a1Val + 1))) (N (show a2Val)) stm)) Skip)) s
    where
        a1Val = aVal a1 s
        a2Val = aVal a2 s

-- abort

sosStm (Inter Abort s) = Stuck Abort s
sosStm (Inter ss s) = Stuck ss s 