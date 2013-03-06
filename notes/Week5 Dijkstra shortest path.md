# Week 5 Dijkstra's shortest path
Single Source Shortest Paths

Input: Directed Graph =(V,E) (m=|E|), n=|V|)
each edge has non-negative length le
source vertext s

Output: for each v in V, compute L(v)= length of a shortest s-v path in G

Assumptions
1) For convenience, that exists a path s->v
2) non-negative paths

# The Algorithm

Generalisation of BFS. Reduces to BFS if path lengths are all 1.

Initialize:
X={S} vertices processed so far
    we'll add one vertex per loop to X
A[s]=0 shortest path so far
    for each vertext an entry in A which at the end contains the shortest path
B[s]=empty path computed shortests path //Not needed
    ** Not neeed **
    B will hold the actual path to v

Main Loop
While X <> V
    grow X by one node
    look at the vertices 1 edge away from X
        among all edges v,w in E
        with tail v in X, and head w not in X
        nb directed graph: we'll ignore incoming edges
    if all possible candidates pick the one that minimizes:
         A[v] + lvw
            => call it "Dijkstra's greedy criterion"
        call it (v*,w*)
        add w* to X
        Set A[w*]:= A[v*] + lv*w*
        Set B[w*]= B[v*] u (v*,w*)
[note:]


# Implementation with Heap to speed up algorithm

If we are doing min-computations repeatedly, can we improve running time with a data-structure that speeds up the 'find min path step'

# Heap interface: Insert(), and popMin()
-in case of duplicate, you don't know which you'll get
-expected run time for heap is log n for both insert and pop
May have:
heapify() a set O(n)
delete from middle in log time

## App: sorting
    n^2 sort: selection sort

Heap sort: insert into a heap then extract which is O(n log n)
slower than quicksort but same ballpark

## App: time ordered Event Manager, or Priority Queue
e.g. replaying events for a game simulation

## App: Median Maintenance
given a sequence of numbers, return at each step the median
constraint: your budget is O(log i)


# Implementation of Heap

2 views of a heap: one as a tree, one as an array. 
Let's go for a binary tree, ie each node has 2 children
The Heap property: 
    Key[node x] = min( keys of x's children)
Implement as array by ordering by level.
probably by setting size to 2^n
Then parent(i where i>1)
parent (i) =  { i/2 if i is even
            { floor(i/2) if i is odd
children(i)= 2i, 2i+1

## Insert and Bubble-Up

Insertion
    1. Put new entry into the first empty space, ie the next empty space
    2. Fix the heap violation if empty by swap with parent. Repeat. Still works at root.
    Runtime for insert is < log_2(n)
PeekMin() - view the top
Extract Min
    1. Move last element to the root
    2. Push node down swapping with the smaller child each time
    Again runtime is O(log_2(n))
    
