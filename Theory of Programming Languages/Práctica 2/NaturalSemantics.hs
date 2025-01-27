{-|

Programming Languages
Fall 2024

Implementation of the Natural Semantics of the WHILE Language

Author:

-}

module NaturalSemantics where

import           Aexp
import           Bexp
import           State
import           While

-- representation of configurations for WHILE

data Config = Inter Stm State  -- <S, s>
            | Final State      -- s

-- representation of the execution judgement <S, s> -> s'

nsStm :: Config -> Config

-- x := a

nsStm (Inter (Ass x a) s)      = Final (update s x (aVal a s))

-- skip

nsStm (Inter Skip s)           = Final s 

-- s1; s2

nsStm (Inter (Comp ss1 ss2) s) = Final s''   --conclusion
  where 
    Final s' = nsStm (Inter ss1 s)         --premisas
    Final s'' = nsStm (Inter ss2 s')

-- if b then s1 else s2

-- B[b]s = tt
nsStm (Inter (If b ss1 ss2) s) 
    | bVal b s = nsStm (Inter ss1 s) 

-- B[b]s = ff
nsStm (Inter (If b ss1 ss2) s) 
    | not (bVal b s) = nsStm (Inter ss2 s)

-- while b do s

-- B[b]s = ff
nsStm (Inter (While b ss) s) 
    | not (bVal b s) = Final s 

-- B[b]s = tt
nsStm (Inter (While b ss) s) 
    | bVal b s = Final s''
    where 
      Final s' = nsStm (Inter ss s)
      Final s'' = nsStm (Inter (While b ss) s')

-- repeat S until b

-- B[b]s' = tt
nsStm (Inter (Repeat ss b) s)
    | bVal b s' = Final s'
    where 
      Final s' = nsStm (Inter ss s)


-- B[b]s' = ff
nsStm (Inter (Repeat ss b) s)
    | not (bVal b s') = nsStm (Inter (Repeat ss b) s')
    where 
      Final s' = nsStm (Inter ss s)

-- for x := a1 to a2 do S

-- A[a1]s > A[a2]s
nsStm (Inter (For x a1 a2 ss) s)
    | aVal a1 s > aVal a2 s = Final s 

-- A[a1]s <= A[a2]s
nsStm (Inter (For x a1 a2 ss) s)
    | not (aVal a1 s > aVal a2 s) = nsStm (Inter (Comp (Comp (Ass x a1) ss) (For x (Add (N n1) (N "1")) (N n2) ss)) s)
    where 
      n1 = show (aVal a1 s)
      n2 = show (aVal a2 s) 

-- semantic function for natural semantics
sNs :: Stm -> State -> State
sNs ss s = s'
  where Final s' = nsStm (Inter ss s)
