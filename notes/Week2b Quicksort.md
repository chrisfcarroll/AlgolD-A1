# Week2 Quicksort
O(nlogn)
constants are low
minimal memory - mostly in place
elegant

## partition around a pivot

pick a pivot e.g. take the 1st

Rearrange so that 
all els left of pivot are less
all els right of pivot are more

nb ends with pivot in correct place
2 things:

1) Pivoting is linear time with _no_ overhead.
2) enable divide and conquer


## Partitioning around a pivot
### Merge step proof of correctness
The for loop maintains invariants :
    [p ¦ left ¦ right ¦ unsorted]

## Proof of correctness of quicksort


## Choosing a pivot

Worst case: array is already sorted and you take the 1st element
runtime  = sum n + (n-1) + (n-2) ... = theta(n^2)
Sub recursions are completely unbalanced 

Best case: if the pivot magically is the median each time. 
Then master method applies, a=2, b=2, d=1 (d step is linear)
runtime = theta(n log n)

## How to choose pivots?
big idea: randomisation
choose pivot randomly at each call.

Roughly: any near-to-median pivot will get nlogn. Claim that 25-75% is good enough.

## Quicksort theorem
average running time for random pivots for any input is theta(n log n)
although n2 is possible, nlogn (the best case) is also the average

avg is over our random choices

## Analysis of quicksort 1: Decomposition

input array A of length n

Sample space Omega= all possible pivot sequences

for sigma in Omega, let C(s) be the number of comparisons made by quicksort given the sequence

lemma: runtime of quicksort is no larger than const * no of comparisons

to prove: E[C]  is O(n log n)

decomposition: decompose the C into stuff we can calculate

Notation
zi = ith smallest element of A, i=1 being 1st

For sigma, & indices i,j let
    Xij = # of times zi,zj get compared in QS with pivot sequence sigma

Xij is 0 or 1 for all ij.

C = Sum_i,j ( Xij(s) )

(lemma linearity of expection even when Xijs not independent)

E[C] = Sum_i,j ( E[ Xij(s) ] )
E[C] = Sum_i,j () P[zi,zj get compared] )

