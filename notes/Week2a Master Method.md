# Week 2

## The master method
for analysing divide and conquer algorithms

### eg mult 2 n-digit numbers

T(n)= worst number of ops

Recurrence: express T in terms of recursive calls

base case: T is const ie T(1) <= C

T(n) = 4 T(n/2) + 4C

compute 
    ac
    bd
    (a+b)(c+d)

new recurrence:

Merge: T(n) <= 2 T (n/2) 

### Formal statement of Master Method

Calculates T() for an algorithm
Assumption: 
subproblems are equal size

format of recurrence problems to which this version applies:

1) T(n) < a constant for some small enough n
2) T(n) <= a * T(n/b) + O( n d)

    a= no of subproblems >= 1
    b= factor by which input size shrinks >= 1
    d=exp. in running time of merge step (d=0 > constant merge size)

nb a,b,d are constants here

Master Theorem:
T(n) = 3 cases
    { if a= b^d
        O( n^d * log n)

    { if a< b^d
        O( n^d   )

    { if a > b^d
        O(  n^ (logb a)  )


Examples

1) Merge sort
    a=2, b=2, d=1

2) Binary search
    a=1, b=2, d=0, case 1:  O(logn)

3) Simple recursive multi
    a=4, b=2, d=1
    case 3
    = O( n^(log2 4)) = ... = O (n^2)

4) Karatsuba mult
    a=3, b=2, d=1
    => O(n^log2(3) ) = O(n^1.59)

5) Strassen
    a= 7
    b= 2
    d= 2
    O(n^log2(7)) = O(n^.281)

6) T(n) <= 2T(n/2) + O(n^2)


# Approximate Proof of Master Method

Assume 
T(1) <= C
T(n) <= aT(n/b)) + Cn^d

n is a power of b

Idea: generalise merge sort analysis
what's the pattern: at level j there are ... subproblems?
a^j subproblems of size n/b^j
Draw the tree ...

work done at each level is just the (small) merge step done a^j times:
a^j   *   C * (n/b^j)^d

= C * n^d * (a/b^d)^j

Sum over all levels:

Cn^d * sigma0..logbn( (a/b^d)^j )
3 cases: converge/diverge/constant
