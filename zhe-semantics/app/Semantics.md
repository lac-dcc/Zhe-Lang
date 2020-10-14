Guard Application Function
- i -> [t1, t2, t3, ..., tn]
- i' -> [t2, t3, ..., tn]]
- G[tx, i] -> Succ([tx], i') if tx = t1
- G[tx, i] -> Fail([], i') if tx != t1


Recursive Application Function
M( G[tx, i] -> Succ(t, i') ) -> Succ(t, i')
M( G[tx, i] -> Fail([], i') ) -> M(G[tx, i'])
M( G[tx, i] -> Fail([], []) ) -> Fail([], [])


Combination Operator
- i -> [t1, t2, t3, ..., tn]
- i' -> [t2, t3, ..., tn]]
- i'' -> [t3, ..., tn]]
- ( G[tx, i ] -> Succ([tx], i') and 
    G[ty, i'] -> Succ([ty], i'') ) -> Succ([tx, ty], i'') 
- ( G[tx, i ] -> Fail([], i') or 
    G[ty, i'] -> Fail([], i'') ) -> Fail([], i') 


Or Operator
- i -> [t1, t2, t3, ..., tn]
- i' -> [t2, t3, ..., tn]]
- ( G[tx, i] -> Succ([tx], i') and 
    G[ty, i] -> Fail([], i') ) -> Succ([tx], i')
- ( G[tx, i] -> Fail([], i') and 
    G[ty, i] -> Succ([ty], i') ) -> Succ([ty], i')
- ( G[tx, i] -> Succ([tx], i') and 
    G[ty, i] -> Succ([ty], i') ) -> Succ([tx], i')
- ( G[tx, i] -> Fail([], i') and 
    G[ty, i] -> Fail([], i') ) -> Fail([], i')


Sequencing Operator
- i_e = [t1, t2, t3, ..., tx, tx+1, ..., tn]
- i_e' = [t2, t3, ..., tx, tx+1, ..., tn]
- i_e'' = [t3, ..., tx, tx+1, ..., tn]
- i_e''' = [tx+1, ..., tn]

- ( G[tx, i_e]  -> Succ([tx], i_e') and
    M(G[ty, i_e' ]) -> Succ([ty], i_e''') ) -> Succ([tx, ty], i_e''')

- ( G[tx, i_e]  -> Fail([], i_e') or
    M(G[ty, i_e']) -> Fail([], []) ) -> Fail([], i_e')

Contains Operator
- i -> [t1, t2, t3, ..., tn]
- i' -> [t2, t3, ..., tn]]
- (G[tx, i] -> Succ([tx], i') and 
   G[ty, [tx]] -> Succ([ty], i') ) -> Succ([ty], i')
- (G[tx, i] -> Fail([], i') or 
   G[ty, [tx]] -> Fail([], i') ) -> Succ([], i')


Machine Function:
C = Clojure
T(G[t, e], C) -> C(t) if M(G[t, e]) -> Succ(t, e')