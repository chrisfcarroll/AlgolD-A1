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



