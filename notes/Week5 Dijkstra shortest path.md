# Week 5 Dijkstra's shortest path

Input: Directed Graph =(V,E) (m=|E|), n=|V|)
each edge has non-negative length le
source vertext s

Output: for each v in V, compute L(v)= length of a shortest s-v path in G

Assumptions
1) For convenience, that exists a path s->v
2) non-negative paths

# The Algorithm

Generalisation of BFS. Reduces to BFS if path lengths are all 1.