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

