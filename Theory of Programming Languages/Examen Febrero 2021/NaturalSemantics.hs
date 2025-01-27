-- -------------------------------------------------------------------
-- Natural Semantics for WHILE 2021.
-- Examen de Lenguajes de Programación. UMA.
-- 1 de febrero de 2021
--
-- Apellidos, Nombre:
-- -------------------------------------------------------------------

module NaturalSemantics where

-- En este fichero solo necesitas completar:
--
--   - 2.a la definición semántica de la sentencia case
--   - 2.a la implementación de la sentencia case
--
-- No modifiques el resto del código. Puedes probar
-- tu implementación con la función run, definida al final.

----------------------------------------------------------------------
-- NO MODIFICAR EL CODIGO DE ABAJO
----------------------------------------------------------------------

import           While21
import           While21Parser

updateState :: State -> Var -> Z -> State
updateState s x v y = if x == y then v else s y

-- representation of configurations for While

data Config = Inter Stm State  -- <S, s>
            | Final State      -- s

-- representation of the transition relation <S, s> -> s'

nsStm :: Config -> Config

-- x := a

nsStm (Inter (Ass x a) s) = Final (updateState s x (aVal a s))

-- skip

nsStm (Inter Skip s) = Final s

-- s1; s2

nsStm (Inter (Comp ss1 ss2) s) = Final s''
  where
    Final s'  = nsStm (Inter ss1 s)
    Final s'' = nsStm (Inter ss2 s')

-- if b then s1 else s2

nsStm (Inter (If b ss1 ss2) s)
  | bVal b s = Final s'
  where
    Final s' = nsStm (Inter ss1 s)

nsStm (Inter (If b ss1 ss2) s)
  | not(bVal b s) = Final s'
  where
    Final s' = nsStm (Inter ss2 s)

----------------------------------------------------------------------
-- NO MODIFICAR EL CODIGO DE ARRIBA
----------------------------------------------------------------------

-- case a of

-- |----------------------------------------------------------------------
-- | Exercise 2.a
-- |----------------------------------------------------------------------

-- | Define the Natural Semantics of the case statement.

{-

    Completa la definición semántica de la sentencia case.

                         <S,s> -> s'
  [case LC tt] -----------------------------------  LC = (LL : S LC') and (A[a]s isElem lista) = tt
                  <case a of LC end, s> -> s'

                  <case a of LC' end, s> -> s'
  [case LC ff]  --------------------------------     LC = (LL : S LC') and (A[a]s isElem lista) = ff
                  <case a of LC end, s> -> s'

  [case endL]  ---------------------------------     LC = End
                <case a of LC end, s> -> error

                            < S, s> -> s'
  [case default]  ------------------------------     LC = default:S
                   <case a of LC end, s> -> s'
-}

-- |----------------------------------------------------------------------
-- | Exercise 2.a
-- |----------------------------------------------------------------------

-- | Implement the Natural Semantics of the case statement.
-- Case Aexp LabelledStms 

-- LabelledStms = LabelledStm [Integer] Stm LabelledStms
--                   | Default Stm
--                   | EndLabelledStms


nsStm (Inter (Case a (LabelledStm lista ss lb')) s)
  | elem (aVal a s) lista = nsStm (Inter ss s)

nsStm (Inter (Case a (LabelledStm lista ss lb')) s)
  | not (elem (aVal a s) lista) = nsStm (Inter (Case a lb') s) 


nsStm (Inter (Case a (Default ss)) s) = nsStm (Inter ss s)

nsStm (Inter (Case a EndLabelledStms) s) = error ("Ninguna etiqueta coincide")


-- |----------------------------------------------------------------------
-- | Exercise 2.a SEPTIEMBRE 2020
-- |----------------------------------------------------------------------

-- | Define the Natural Semantics of the swap statement.

{-

    Completa la definición semántica de la sentencia swap.

  [swap]  -------------------------------------------------------
             < swap x y, s > -> (s[x -> s y])[y -> s x]
  
-}

-- data  Stm   =  Ass Var Aexp
--             | ...
--             | Swap Var Var

nsStm (Inter (Swap x y) s) = Final (updateState(updateState s x (s y)) y (s x))

-- |----------------------------------------------------------------------
-- | Exercise 3.a SEPTIEMBRE 2020
-- |----------------------------------------------------------------------

-- | Define the Natural Semantics of the for statement.

{-

    Completa la definición semántica de la sentencia for.

                 <S1,s> -> s'
    [for1] ---------------------------------------  if B[b]s = ff
               <for S1 b S2 S3, s> -> s'


              <S1,s> -> s'  <S3,s'> -> s''  <S2,s''> -> s'''  < for skip b S2 S3,s'''> -> s''''
    [for2] ------------------------------------------------------------------------------------------ if B[b]s = tt
                  <for S1 b S2 S3, s> -> s''''
-}

nsStm (Inter (For ss1 b ss2 ss3) s) 
    | bVal b s = Final s'
  where
    Final s' = nsStm (Inter ss1 s)

nsStm (Inter (For ss1 b ss2 ss3) s)
    | not(bVal b s) = Final s''''
    where 
      Final s' = nsStm (Inter ss1 s)
      Final s'' = nsStm (Inter ss3 s')
      Final s''' = nsStm (Inter ss2 s'')
      Final s'''' = nsStm (Inter (For Skip b ss2 ss3) s''')


-- |----------------------------------------------------------------------
-- | Exercise 5. SEPTIEMBRE 2020
-- |----------------------------------------------------------------------

-- | Define the Natural Semantics of the do od statement.

{-

    Completa la definición semántica de la sentencia for.

                     <S,s> -> s'   <do GS od, s'> -> s'' 
    [dood1] ----------------------------------------------  GS = b -> S;GS' and B[b]s = tt
                            <do GS od, s> -> s''

                         <do GS' od, s> -> s'
    [dood2] ----------------------------------------------  GS = b -> S;GS' and B[b]s = tt
                            <do GS od, s> -> s'


                  <do GS' od, s> -> s'
    [dood3] ------------------------------------- GS = b -> S;GS' and B[b]s = ff
                  <do GS od, s> -> s'


    [dood4] --------------------------------------- GS = epsilon
                    <do GS od, s> -> s


-}

----------------------------------------------------------------------
-- NO MODIFICAR EL CODIGO DE ABAJO
----------------------------------------------------------------------

-- | Run the While program stored in filename and show final values of variables.
--   For example: run "TestCase.w"

run :: String -> IO()
run filename =
  do
     (_, vars, stm) <- parser filename
     let Final s = nsStm (Inter stm (const 0))
     print $ showState s vars
     where
       showState s = map (\ v -> v ++ "->" ++ show (s v))
