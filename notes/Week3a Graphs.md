# Week 3 Graphs

Vertices aka nodes (V)
Edges (E) or arcs
directed (tail and head) or undirected

## Cuts of graphs

Defn a partition of a graph (V,E) into 2 non-empty sets A & B

Defn the crossing edges of a cut A,B:
undirected: one end in A, other in B

directed, different diffs, we'll go with with tail in A, head in B

## The Minimum Cut Problem:

Input: undirected graph
parallel edges allowed
Goal: compute a cut with fewest number of crossing edges, a min cut

## Representing Graphs
(N,M) = no nodes, m edges)

number of edges is between (n-1) and nC2 ie n(n-1)/2

### data structures for sparse vs dense graphs
n= vertices, m= edges

In most applications, m is at least linear in n and is O(n^2)

sparse: m is close to O(n)
dense m is close O(n^2)
log then treat as dense

## Adjacency Matrix
G is represented by an n*n matrix A where Aij=1 <=> there's an edge

## Adjacency List
A list for each of Vs and Es
each edge points to its endpoints
each vertex points to its edges (in a directed graph store only when its the tail)

# Random Contraction Algorithm
Karger.
use random sampling to contract group

Probability of a good result?

# Analysis of Karger contraction algorithm

let K be the edges in the min cut
chance of cutting one of them (ie of screwing up) in first round is k/m
But let's work in terms of n not m (it's easier)
Note that degree of each vertex is at least k because each vertex of degree d defines a cut of degree d. 
Note that total number of edges = sum(degree v) = 2m

so m >= kn/2 
Chance of fail on first go Pr(¬S1) is k/m <= 2/n

Cond prob: Pr(¬S1 intersect ¬S2) = Pr(¬S2 ¦ ¬ S1) * Pr (¬S1)

Note: each node i in reduced graph represents a cut so has at least k edges
So edges in new graph >= 1/2 k(n-1)

Pr(¬S1 intersect ¬S2) >= 1 - (2/n-1)

... ...  Pr(Success) >= 1/n^2

Much better than the exponential growth in possibilities. Doing it repeatedly, we can get a high probability of success

Q: how many trials needed?

bouned above by, for trials= n^2 by 1/e
For trials= n^2 log n = 1/n ie tends to zero prob of failure for large graphs

## Counting minimum cuts
A graph may have more than 1 min cut
How many?

a tree has n-1 min cuts

What's largest number of min cuts that a graph with n vertices could have?

At least n-1 because a tree has that many
No more than 2^n because there are only than many cuts
Answer for undirected graphs  nC2 = n(n-1)/2 (which is polynomial)
Proof
Lower Bound: 
In general a (n.m) graph, let's take the n-cycle, has at least nC2 min-cuts

Upper Bound: in the contraction algorithm the chance of getting a min cut was 1/(nC2)
so there can't be more than nC2 of them

