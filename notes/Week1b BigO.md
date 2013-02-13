# Asymptotic issues
## Big O
definition: T(n) is O( f(n) ) 
∀ n>=n0 ∃ constants, n0 s.t. T(n) <= C.f(n) for all n>n0

## Polynomials

Claim: if T(n) is a polynomial of order k then it is O(nk)
    Let n0=1, and c = sum(abs (coefficients))

## eg 2
Claim for every k>=1, n pow k is not O(n pow k-1)

## big Omega and Theta

O     : <=
Omega : >=
    T(n) is Omega(f(n)) if ∃ constants, n0 s.t. T(n) >= C.f(n) for all n>n0
Theta : =
    T(n) is Theta(f(n)) if both bigO and bigOmega
        ie sandwiched between constant multiples

# little O
little is strictly less than - growing strictly less 

For all C>0 E a constant n0 s.t. A n > n0, T(n) <= c.f(n)


## eg3 

forall f , g positive on positive integers, max(f,g) is Theta(f +g)

max(f,g) = f or g therefore < (f+g)
but max(f,g) > average(f,g)

therefore 1/2 (f+g) <= max(f,g) <= (f+g)

## Divide and conquer

1) divide
2) conquer with recursive calls
3) combine

## Array inversions

instances of ai > aj where i < j

a measure of how similar 2 ranked lists are

brute force: O(n^2)

aim for O(n log n)

How
Divide: count left invs, right invs & split invs
The divide step will not only count invs, but also do a merge sort

SortAndCountSplit


# Strassen Subcubic Matrix multiplication

zij= Sum k=1 to n of xik * ykj
the simple alogrithm is Theta(n^3)
simple recursive is also Theta(n^3)
Strassen's is O(n^2.81)?

# O(n log n) algorithm for Closest Pair I 

set P= {p1 .. pn} in R2
find the nearest pair

Brute force: Theta(n2)

1-d: sort then find nearest, so O(n logn + n) 

2-d
1) sort twice,once by x once by y (Px, Py)
2) & 3) let  Qxy,Rxy=Pxy
Find the closet pair in each subset. Then find closest split pair

Aim for ClosestSplitPair in O(n)
Note that we only need to solve the 'unlucky case'
4) let delta= min { minQxy, minRxy }
5) ClosestSplitPair (Px, Py, delta)

let xb = biggest in LHS Px
let strip Sy= 2delta wide strip in the middle, sorted by y-coord

Init best= delta, bestpair=null
for i=1 to |Sy| -1
    For j=1 to min(7, |Sy|-i)
        Examine Pair i,j for bestness
return best pair. Interpret null as 'nothing better than delta'

Note! the inner loop we look at max constant 7 pairs.

Correctness:
let p in Q, q in R be a split pair with distance < delta
We show:
(A) p & q are in the strip Sy
(B) p and q are 'almost next to each other' - within 7 positions of each other

Corollary 1 : ClosestSplitPair will find it.
Corollary 2 : Closest Pair is correct and runs in O(nlogn)

2^f = O ( g +  f)
cases:

first merge step has: 2 ops * n = 2n ops
ith step: 2 ops * in = 2in ops
last: 2kn ops
2n * sigma i=1..k ( i) = k^2

nk^2


-----

a=0.5
b=e
c=1.5
d= e 0.5 (log)
e= 1.66
daceb

let fx= e^sqrt(logn) - sqrt(n)
f'x = e^(1/2 (x^-1/2)) 
d/dx sqrt(log n) 
-1/(2 * sqrt(log n)) * 1/x
= e^sqrt(logn) / (2xsqrt(log x))
-=
2 sqrt(x)
---------------
a= 2log
b= e
c= e^e
d= 


