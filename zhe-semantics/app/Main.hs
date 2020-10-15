module Main where

data GuardOutput = Satisfied [Integer] [Integer]
                 | UnSatisfied [Integer] deriving Show

type Guard = [Integer] -> GuardOutput
type Action = [Integer] -> ()
type Event = [Integer] -> ()


event :: Guard -> Action -> Event
event guard action input = case guard input of
  Satisfied tokens remainingInput -> do
    let _ = action tokens
    event guard action remainingInput
  UnSatisfied remainingInput -> event guard action remainingInput


eval :: [Event] -> [Integer] -> [()]
eval events input = map (\e -> e input) events


-- +
combinatorOperator :: Guard -> Guard -> Guard
combinatorOperator c1 c2 input = case c1 input of
  UnSatisfied rest1 -> UnSatisfied rest1
  Satisfied t1 r1 -> case c2 r1 of
    UnSatisfied rest2 -> UnSatisfied rest2
    Satisfied t2 r2 -> Satisfied (t1 ++ t2) r2

    
-- |
orOperator :: Guard -> Guard -> Guard
orOperator c1 c2 input = case c1 input of
  Satisfied t1 r1 -> Satisfied t1 r1
  UnSatisfied _ -> c2 input


-- e1(e2)e3
containsOperator :: Guard -> Guard -> Guard -> Guard
containsOperator c1 c2 c3 = c1 `combinatorOperator` c2 `combinatorOperator` c3


-- >
sequenceOperator :: Guard -> Guard -> Guard
sequenceOperator c1 c2 input = case c1 input of
  UnSatisfied rest -> UnSatisfied rest
  Satisfied t1 r1 -> case checkIfCanSatisfyGuard c2 r1 of
      UnSatisfied _ -> UnSatisfied r1
      Satisfied t2 r2 -> Satisfied (t1 ++ t2) (drop (length t2) r1) 


checkIfCanSatisfyGuard:: Guard -> [Integer] -> GuardOutput
checkIfCanSatisfyGuard g [] = g []
checkIfCanSatisfyGuard guard input = case guard input of
    UnSatisfied remain -> checkIfCanSatisfyGuard guard remain
    Satisfied tokens remain -> Satisfied tokens remain

---------- Balanced Parenthesis Language Example --------------

oneGuard :: [Integer] -> GuardOutput
oneGuard [] = UnSatisfied []
oneGuard (0:rest) = UnSatisfied rest
oneGuard (1:rest) = Satisfied [1] rest

zeroGuard :: [Integer] -> GuardOutput
zeroGuard [] = UnSatisfied []
zeroGuard (0:rest) = Satisfied [0] rest
zeroGuard (1:rest) = UnSatisfied rest

acceptsEpsilon :: Guard -> Guard
acceptsEpsilon g input = case g input of
  Satisfied t r -> Satisfied t r
  UnSatisfied _ -> Satisfied [] input

  
auxB :: [Integer] -> GuardOutput
auxB = containsOperator zeroGuard (acceptsEpsilon b) oneGuard

b :: Guard
b = (auxB `combinatorOperator` auxB) `orOperator` auxB    


-- >>> b [0, 0, 1, 0, 1, 1]
-- Satisfied [0,0,1,0,1,1] []
--

main :: IO()
main = print ""
