module Main where

data Result = Ok [Integer] [Integer]
            | Err [Integer]
            deriving Show

type Condition = [Integer] -> Result
type Action = [Integer] -> ()
type Event = Condition -> Action -> ()


-- modPredicate :: Integer -> Condition
-- modPredicate _   [] = Err []
-- modPredicate num (head : tail) =
--   if (head `mod` num) == 0 then Ok [head] tail else Err tail


-- doNothingAction :: Action
-- doNothingAction x = ()


eval :: [Integer] -> Event
eval []    _         _      = ()
eval input condition action = eval remaining condition action where 
    remaining = case condition input of
        Err rest -> rest
        Ok tokens rest -> do 
          let _ = action tokens
          rest


-- +
combinatorOperator :: Condition -> Condition -> Condition
combinatorOperator c1 c2 input = case c1 input of
  Err rest -> Err rest
  Ok t1 r1 -> case c2 r1 of
    Err rest -> Err r1
    Ok t2 r2 -> Ok (t1 ++ t2) r2

    
-- |
orOperator :: Condition -> Condition -> Condition
orOperator c1 c2 input = case c1 input of
  Ok t1 r1 -> Ok t1 r1
  Err _    -> c2 input


-- e1(e2)
containsOperator :: Condition -> Condition -> Condition
containsOperator c1 c2 input = case c1 input of
  Err rest -> Err rest
  Ok t1 r1 -> case checkIfCanSatisfyCondition c2 t1 of
    Err _   -> Err r1
    Ok t2 _ -> Ok t2 r1


-- >
sequenceOperator :: Condition -> Condition -> Condition
sequenceOperator c1 c2 input = case c1 input of
  Err rest -> Err rest
  Ok t1 r1 -> case checkIfCanSatisfyCondition c2 r1 of
      Err _ -> Err r1
      Ok t2 r2 -> Ok (t1 ++ t2) r2


checkIfCanSatisfyCondition:: Condition -> [Integer] -> Result
checkIfCanSatisfyCondition condition [] = Err []
checkIfCanSatisfyCondition condition input = case condition input of
    Err remain -> checkIfCanSatisfyCondition condition remain
    Ok tokens remain -> Ok tokens remain

-- >>> sequenceOperator (modPredicate 3) (modPredicate 4) [15, 46, 16, 24]
-- Ok [15,16] [24]
--


main = print ""
