module Main where

data GuardOutput = Satisfied [Integer] [Integer]
                 | UnSatisfied [Integer] deriving Show

type Guard = [Integer] -> GuardOutput
type Action = [Integer] -> ()
type Event = [Integer] -> ()


engine :: ([Integer] -> GuardOutput)  -> [Integer] -> ([Integer] -> ()) -> ()
engine c input f = case loopOp c input of
  Satisfied t r -> seq (f t) (engine c r f)
  UnSatisfied _ -> ()


atomOp :: Integer -> Guard
atomOp _ [] = UnSatisfied []
atomOp t (h:r) = if t == h then 
  Satisfied [t] r
  else
  UnSatisfied r

-- +
combOp :: Guard -> Guard -> Guard
combOp c1 c2 input = case c1 input of
  UnSatisfied rest -> UnSatisfied rest
  Satisfied t1 r1 -> case c2 r1 of
    UnSatisfied _ -> UnSatisfied r1
    Satisfied t2 r2 -> Satisfied (t1 ++ t2) r2

    
-- |
orOp :: Guard -> Guard -> Guard
orOp c1 c2 input = case c1 input of
  Satisfied t1 r1 -> Satisfied t1 r1
  UnSatisfied _ -> c2 input

-- ?
optOp :: Guard -> Guard
optOp c1 input = case c1 input of
  Satisfied t r -> Satisfied t r
  UnSatisfied _ -> Satisfied [] input

-- >
seqOp :: Guard -> Guard -> Guard
seqOp c1 c2 input = case c1 input of
  UnSatisfied rest -> UnSatisfied rest
  Satisfied t1 r1 -> case loopOp c2 r1 of
      UnSatisfied [] -> UnSatisfied []
      Satisfied t2 rest -> Satisfied (t1 ++ t2) rest 


loopOp:: Guard -> [Integer] -> GuardOutput
loopOp c1 [] = c1 []
loopOp c1 input = case c1 input of
    UnSatisfied rest -> loopOp c1 rest
    Satisfied t1 r1 -> Satisfied t1 r1

---------- Balanced Parenthesis Language Example --------------

  
-- auxB :: [Integer] -> GuardOutput
-- auxB = zeroGuard `combinatorOperator` acceptsEpsilon b `combinatorOperator` oneGuard

-- b :: Guard
-- b = auxB `combinatorOperator` acceptsEpsilon auxB 

-- c = oneGuard `orOperator` zeroGuard


-- >>> b [0,0,1,0,1,1]
-- (Error while loading modules for evaluation)
-- [1 of 1] Compiling Engine           ( /Users/joaosaffran/Zhe/zhe-semantics/app/Engine.hs, interpreted )
-- <BLANKLINE>
-- /Users/joaosaffran/Zhe/zhe-semantics/app/Engine.hs:27:1-6: error:
--     The type signature for ‘combOp’ lacks an accompanying binding
-- Failed, no modules loaded.
--

main :: IO()
main = print ""
