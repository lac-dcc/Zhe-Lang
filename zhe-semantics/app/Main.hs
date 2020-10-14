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
  UnSatisfied rest -> UnSatisfied rest
  Satisfied t1 r1 -> case c2 r1 of
    UnSatisfied _ -> UnSatisfied r1
    Satisfied t2 r2 -> Satisfied (t1 ++ t2) r2

    
-- |
orOperator :: Guard -> Guard -> Guard
orOperator c1 c2 input = case c1 input of
  Satisfied t1 r1 -> Satisfied t1 r1
  UnSatisfied _    -> c2 input


-- e1(e2)
containsOperator :: Guard -> Guard -> Guard -> Guard
containsOperator c1 c2 c3 = c1 `combinatorOperator` c2 `combinatorOperator` c3


-- >
sequenceOperator :: Guard -> Guard -> Guard
sequenceOperator c1 c2 input = case c1 input of
  UnSatisfied rest -> UnSatisfied rest
  Satisfied t1 r1 -> case checkIfCanSatisfyGuard c2 r1 of
      UnSatisfied _ -> UnSatisfied r1
      Satisfied t2 r2 -> Satisfied (t1 ++ t2) r2


checkIfCanSatisfyGuard:: Guard -> [Integer] -> GuardOutput
checkIfCanSatisfyGuard _ [] = UnSatisfied []
checkIfCanSatisfyGuard guard input = case guard input of
    UnSatisfied remain -> checkIfCanSatisfyGuard guard remain
    Satisfied tokens remain -> Satisfied tokens remain

---------- Balanced Parenthesis Language Example --------------

oneGuard :: [Integer] -> GuardOutput
oneGuard (token:rest) = if token == 1 then
    Satisfied [1] rest
  else
    UnSatisfied rest

zeroGuard :: [Integer] -> GuardOutput
zeroGuard (token:rest) = if token == 0 then
    Satisfied [0] rest
  else
    UnSatisfied rest

epsilon :: [Integer] -> GuardOutput
epsilon _ = Satisfied [] []

b :: [Integer] -> GuardOutput
b input = orOperator  ( combinatorOperator b b ) 
                      ( orOperator  epsilon
                                    ( combinatorOperator 
                                      oneGuard 
                                      ( combinatorOperator b zeroGuard ) )
                      ) input

-- >>> orOperator 
-- Satisfied [1,0] []
--

main :: IO()
main = print ""
