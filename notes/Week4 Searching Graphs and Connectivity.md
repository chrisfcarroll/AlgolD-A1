# Week 4 Searching Graphs and Connectivity

A telephone network needs a path between nodes
Kevin Bacon game is a shortest path question
Driving route usually shortest path

# Abstractedly think of path as a series of decisions
Filling in a sudoku puzzle
    a directed graph where the nodes correspond to partially completed puzzles
A plan for a robotic hand to grab an object

# Connectivity
Clustering heuristics
Directed graphs - the notion of connectivity is a bit more subtle

A number of algorithms are linear time, there are 'for-free' primitives

# Approaches to searching
Depth first, Breadth first

Usually have a source vertex, where you start
Goals
1. Find everything findable from a vertex
"Findable from a vertex" implies you can get there
2. Do it efficiently ie search each bit only once or small constant number. Looking for O(n+m)

For dense graphs M dominates

#Generic Algorithm for graph search
Graph G, Vertex S
initially S is explored , the rest is unexplored
while possible:
    look for edge with one node inside explored set and one node V not explored
     (if none, halt)
    Bring V into the set

Claim: this finds everything from S, and doesn't explore anything twice.
ie
    1) Everything in the set at the end is everything for which there is a path to it from S
    1a) by induction 
    1b) By contradiction: suppose there's a node V which is connected to S by a path P but we didn't find it. then find the place on the path where boundary explored-unexplored is. Then the algorithm can't have terminated with that edge unexplored.

# Two particular Instantiations
BFS vs DFS
Choice of which edge to explore next result in different properties of the algorithm

## Breadth First:
Think of layers: S in layer 0, neighbours of S in layer 1, layer is neighbours of layer 2 not in layer 1 etc.
(Layers relate to shortest path: if a node is in layer i then the path length is i)
- Can compute connected components in linear time with a queue structure

Depth-First
Go as deep as poss frist
not useful for shortest path, but in directed graphs it does a lot
In acyclic graphs it gives topological ordering e.g. respecting pre-requisites

BFS & DFS both linear with small constsnts O(n+m) if you use the right data structure in each case

# Breadth First Graph Search
explore nodes by layers
Compute shortest paths
Runtime= O(n+m)

Graph (G, s)
keep a boolean at each node for explored y/n
mark s only as explored
Initialise Q.enqueue (s)
While Q is not empty
    remove the first node of Q, call it v
    foreach edge (v,w)
        -if w unexplored mark and add to queue
note the nodes are visited in distance-from-s order

* Claim 1 v is explored <=> C has a path from s to v
Reason: it's a special case of the generic algorithm

* Claim 2 running time of main loop
is O(Ns+Ms) where ns,ms= # of nodes,edges reachable from S
in each loop: a vertex transitions from unexplored to explored only once.
each edge can be processed maximum 2 times, once for each end.

# BFS Shortest Path
Goal compute dist(v) = shortest path length from s
Extra code on top of basic BFS
initialise all dist=0 if v-s, +infinity if v<>s
When considering unexplored nodes:
at each unexplored node w, set dist(w) = dist(v)+1

# BFS Undirected Connectivity
(connnectivity is the case where directed is quite different to undirected)
Let G=(V,E) not necessarily connected
Connected components of a graph of the maximal connected regions.
Equivalence relation: u~v iff E a u-v path in G

Applications:
clustering, given a pairwise similarity relationship draw edges for cloes connected objects

#To compute connected components

Forloop all nodes
    if i not yet explored
        Use BFS(G,i)loop mark
Answer= Count how many times we invoked the BFS loop

Linear time because we do BFS-time in each connected component on the size of that component

#Depth First Search
The next node to visit is a neighbour of the last node visited, until that's impossibile. Then backtrack

Applications:
Compute topological ordering of directed acyclic graphs
compute strongly connected components of directed graphs
Runtime O(n+m)

Algorithm
1) Same as BFS but with stack instead of queue
2) Recursive version
DFS(G,s)
mark s explored
    recursively call DFS for each unexplored node of s
pClaim #1: V marked as explored iff E path s-v in G
special case of generic one
Claim #2 We look at each node only once, and each edge at most twice

Hence DFS will do instead of BFS for finding connected components

# Topological Sort
Defn A topological ordering of a directed graph G is a labelling f of G's nodes st: 
1) The range of f is the set 1..n
2) If (tail,head) is a directed edge in G then f(tail) < f(head)

If the graph has a directed cycle, then no topological ordering is possible.

## Applications
Ordering a sequence of taks with dependencies

# Theorem: If there is no directed cycle then there is a topological ordering and it can be found in linear time via a DFS search

# Simple Solution

Lemma: Every directed graph has a sink vertex (a vertex with no outgoing arc) or else a directed cycle.

Suppose the graph has topological ordering
The only candidates for the last position are sinks.
Let v be the last vertex.
Delete v and recurse on the rest of the graph (which has n-1 vertices)
Runs in linear time

Topological Sort via DFS

Initialize current_label= n
Mark s explored
For loop over nodes 1..n
    DFS(G, s){
        for every edge (s,v)
        if v not yet explored
            DFS(G,v)
        set f( s )= current_label
        --current_label
    }

* Running time 
    O(m+n)
* Correctedness
    Consider edge u->v.
    Case 1: u is visited first then DFS will mark v before u
    Case 2: v is visited first. There is no directed cycle therefore can't find u from v. Therefore v is done before u.


# Computing Strong Components in Directed Graphs
Linear time, based on DFS, for connectivity for directed graph
Def: Strongly connected= you can get from any point to any other point

Equivalence classes of:
u~v if there is a path from u to v and v to u

Worst case: each node on its own
The SCCs also represent a graph

# SCC Algorithm on DFS
DFS on a node can be adapted to identify a SCC
If you start a DFS in the right place you get a SCC, if you start in the wrong place you don't
Use a preprocessing step: 
## Kosaraju's 2 pass Algorithm

1 Reverse all the arcs on the path
2 Do DFS-Loop on reversed graph
    - compute the 'magical ordering of nodes'
    Let f(v) = 'finishing time of each v in V'
3 Do DFS-Loop on first graph
    process in the decreasing order of finishing times
    - discover the SCCs one by one

### DFS-Loop Pass 1
DFS-Loop(graph G)
    global t= count-of-nodes-totally-finished-with=0
    global S= current source vertext = empty set ;;
    Assume nodes labelled 1 to n
For i=n downto to 1
    if i not yet explored
        s:= i 
        DFS(G, i)

DFS(graph G, node i)
    Mark i as explored
    Set leader(i):= node s
    for each arc (ij) in G
        if j not yet explored
            DFS(G,j)
    t++
    Set f(i):= t

### DFS-Loop Pass 2
Start with the slowest node, find the cycle to itself and that's the SCC
Running time: 2*DFS= O(m+n)

# Kosaraju - Proof of correctness and Time

## Observation: every directed graph has 2 levels of granularity, the zoom-out being the SCCs
The SCCs of a directed graph induce an acyclic 'meta-graph'
Meta-nodes= SCCs
Meta-arcs= the arcs between SCCs in original graph

## Key Lemma:
Consider 2 adjacent SCCs, C1, C2 in G ie exists an arc C1 to C2. Then max f(v) for v in C1 < max f(v) for v in C2 the finishing time.

Case 1. in the first pass, we process a node in C1 before any node in C2
        Then all of C1 gets explored before _any_ of C2
Case 2. in the first pass, we process a node in C2 before any node in C1
        Then the outgoing arc to C1 will be found.
        Lemma: under DFS, a node is numbered after all the nodes that can be reached from it.
        Then all of C1 gets explored before _all_ of C2


## Corollary:
Max f(v) in G will be in a 'sink SCC'
(by contradiction: if not it has an outgoing arc to a SCC with a bigger finishing time)

## Correctness sketch
The 2nd pass of DFS starts in a sink SCC
The remove the sink SCC, choose a new sink SCC and repeat

