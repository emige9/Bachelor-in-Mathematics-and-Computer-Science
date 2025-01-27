-- -------------------------------------------------------------------
-- Structural Operational Semantics for WHILE 2021.
-- Examen de Lenguajes de Programación. UMA.
-- 1 de febrero de 2021
--
-- Apellidos, Nombre:
-- -------------------------------------------------------------------

module StructuralSemantics where

-- En este fichero solo necesitas completar:
--
--   - 2.b la definición semántica de la sentencia case
--   - 2.b la implementación de la sentencia case
--
-- No modifiques el resto del código. Puedes probar
-- tu implementación con la función run, definida al final.

----------------------------------------------------------------------
-- NO MODIFICAR EL CODIGO DE ABAJO
----------------------------------------------------------------------

import           Data.List.HT  (takeUntil)

import           While21
import           While21Parser
import While21 (LabelledStms(EndLabelledStms))

-- representation of configurations for While

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

-- representation of the transition relation <S, s> -> s'

sosStm :: Config -> Config

-- x := a

sosStm (Inter (Ass x a) s) = Final (update s x (aVal a s))
  where
    update s x v y = if x == y then v else s y

-- skip

sosStm (Inter Skip s) = Final s

-- s1; s2

sosStm (Inter (Comp ss1 ss2) s)
  | isFinal next = Inter ss2 s'
  where
    next = sosStm (Inter ss1 s)
    Final s' = next

sosStm (Inter (Comp ss1 ss2) s)
  | isStuck next = Stuck (Comp stm ss2) s'
  where
    next = sosStm (Inter ss1 s)
    Stuck stm s' = next

sosStm (Inter (Comp ss1 ss2) s)
  | isInter next = Inter (Comp ss1' ss2) s'
  where
    next = sosStm (Inter ss1 s)
    Inter ss1' s' = next

-- if b then s1 else s2

sosStm (Inter (If b ss1 ss2) s)
  | bVal b s = Inter ss1 s

sosStm (Inter (If b ss1 ss2) s)
  | not (bVal b s) = Inter ss2 s

----------------------------------------------------------------------
-- NO MODIFICAR EL CODIGO DE ARRIBA
----------------------------------------------------------------------

-- case a of

-- |----------------------------------------------------------------------
-- | Exercise 2.b
-- |----------------------------------------------------------------------

-- | Define the Structural Operational Semantics of the case statement.

{-
    Completa la definición semántica de la sentencia case.


  [case LC tt]  ----------------------------------     LC = (LL : S LC') and (A[a]s isElem lista) = tt
                  <case a of LC end, s> => <S, s>
                                    
  [case LC ff]  ------------------------------------------------     LC = (LL : S LC') and (A[a]s isElem lista) = ff
                  <case a of LC end, s> => <case a of LC' end, s>
                               
  [case default]  -----------------------------------     LC = default:S
                   <case a of LC end, s> => < S, s>

  [case endL]  -----------------------------------------     LC = End
                    <case a of LC end, s> => <Stuck,s>

-}

-- |----------------------------------------------------------------------
-- | Exercise 2.b
-- |----------------------------------------------------------------------

-- | Implement in Haskell the Structural Semantics of the case statement.

sosStm (Inter (Case a (LabelledStm lista ss lb')) s)
  | elem (aVal a s) lista = Inter ss s 

sosStm (Inter (Case a (LabelledStm lista ss lb')) s)
  | not (elem (aVal a s) lista) = Inter (Case a lb') s 


sosStm (Inter (Case a (Default ss)) s) = Inter ss s 

sosStm (Inter (Case a EndLabelledStms) s) = Stuck (Case a EndLabelledStms) s



-- |----------------------------------------------------------------------
-- | Exercise 2.b SEPTIEMBRE 2020
-- |----------------------------------------------------------------------

-- | Define the Structural Semantics of the swap statement.

{-

    Completa la definición semántica de la sentencia swap.

    [swap]  < swap x y, s > => (s[x -> s y])[y -> s x]
                
  
-}

sosStm (Inter (Swap x y) s) = Final (updateState(updateState s x (s y)) y (s x))

-- |----------------------------------------------------------------------
-- | Exercise 3.b SEPTIEMBRE 2020
-- |----------------------------------------------------------------------

-- | Define the Natural Semantics of the for statement.

{-

    Completa la definición semántica de la sentencia for.

    [for]  < for S1 b S2 S3, s > => < S1; if b then (S3;S2; for skip b S2 S3) else skip, s>
  
-}

sosStm (Inter (For ss1 b ss2 ss3) s) = Inter (Comp ss1 (If b (Comp ss3 (Comp ss2 (For Skip b ss2 ss3))) Skip)) s 

----------------------------------------------------------------------
-- NO MODIFICAR EL CODIGO DE ABAJO
----------------------------------------------------------------------

-- | Run the While program stored in filename and show final values of variables

run :: String -> IO()
run filename =
  do
     (_, vars, stm) <- parser filename
     let  dseq = derivSeq stm (const 0)
     putStr $ showDerivSeq vars dseq
     where
      derivSeq st ini = takeUntil (not . isInter) (iterate sosStm (Inter st ini))
      showDerivSeq vars dseq = unlines (map showConfig dseq)
         where
           showConfig (Final s) = "Final state:\n" ++ unlines (showVars s vars)
           showConfig (Stuck stm s) = "Stuck state:\n" ++ show stm ++ "\n" ++ unlines (showVars s vars)
           showConfig (Inter ss s) = show ss ++ "\n" ++ unlines (showVars s vars)
           showVars s vs = map (showVal s) vs
           showVal s x = " s(" ++ x ++ ")= " ++ show (s x)
