(ns week3.mincut
  (:require 
    [graph-files :refer :all]
    [multiset.core :refer [multiset multiset? union minus multiplicity multiplicities] :as ms]))
(comment 
"The file contains the adjacency list representation of a simple undirected graph. There are 200 vertices labeled 1 to 200. The first column in the file represents the vertex label, and the particular row (other entries except the first column) tells all the vertices that the vertex is adjacent to. So for example, the 6th row looks like : 6  155 56  52  120 ....... 
This just means that the vertex with label 6 is adjacent to (i.e., shares an edge with) the vertices with labels 155,56,52,120,......,etc
Your task is to code up and run the randomized contraction algorithm for the min cut problem and use it on the above graph to compute the min cut. (HINT: Note that you'll have to figure out an implementation of edge contractions. Initially, you might want to do this naively, creating a new graph from the old every time there's an edge contraction. But you should also think about more efficient implementations.) (WARNING: As per the video lectures, please make sure to run the algorithm many times with different random seeds, and remember the smallest cut that you ever find.) Write your numeric answer in the space provided. So e.g., if your answer is 5, just type 5 in the space provided.")
;
; Load the file into a node-node adjacency list
;
(def pq-graph    (unweighted-graph-from-file "src/week3/kargerMinCut.txt"))
(def testgraph1  (unweighted-graph-from-file "src/week3/testcase1.txt"))
(def testgraph4  (unweighted-graph-from-file "src/week3/testcase4.txt"))
;
; bits of the algorithm
;
(defn choose-2-adjacent-nodes [graph]
  (let [nodecount (count graph)
        i1    (rand-int nodecount)
        n1    (nth (keys graph) i1)
        adjs (get graph n1)
        adjcount (count adjs)
        i2  (rand-int adjcount)
        n2  (nth (seq adjs) i2)]
    [n1 n2]))

(defn replace-all-x-with-y [mset x y] 
  (reduce #(conj %1 (if (= x %2) y %2)) (ms/multiset) mset))

(defn shrink-graph 
  ([graph] (let [[node1 node2] (choose-2-adjacent-nodes graph)] 
                      (shrink-graph graph node1 node2)))
  ([graph node1 node2] 
    (let [node1-adj (get graph node1)
          node2-adj (get graph node2)
          node1-adj-minus-node2 (minus node1-adj (apply multiset (take (multiplicity node1-adj node2) (repeat node2))))
          node2-adj-minus-node1 (minus node2-adj (apply multiset (take (multiplicity node2-adj node1) (repeat node1))))
          new-adjacencies (ms/sum node1-adj-minus-node2 node2-adj-minus-node1)
          tmp1 (dissoc graph node2)
          tmp2 (assoc tmp1 node1 new-adjacencies)]
      (comment println "(joining" node2 node2-adj "(" (multiset? node2-adj) ") to" node1 node1-adj "(" (multiset? node2-adj) ") merged:" merged-adjacencies " new:" new-adjacencies
               "; tmp2:" tmp2)
      (loop [tmp3 tmp2 i 0]
        (let [ith-node (nth (keys tmp3) i)
              old-adjs (get tmp3 ith-node)
              adj-node2?  (old-adjs node2)]
          (comment println ith-node ": " old-adjs " : has " adj-node2? "? ->" (replace-all-x-with-y old-adjs node2 node1) "else" old-adjs)
          (let [new-adjacencies-for-ith-node (if adj-node2? (replace-all-x-with-y old-adjs node2 node1) old-adjs)
                result (assoc tmp3 ith-node new-adjacencies-for-ith-node)]
              (if (= (count tmp3) (inc i))
                result
                (recur result (inc i)))))))))

(defn shrink-graph-until-two-nodes-left [graph]
  (if (<= (count graph) 2) 
    graph
    (recur (shrink-graph graph))))
;
; and finally 
;
(defn min-cut 
  ([graph] (let [mincut (shrink-graph-until-two-nodes-left graph)
                m1 (second (first (seq (multiplicities (second (first mincut))))))
                m2 (second (first (seq (multiplicities (second (second mincut))))))]
                (if (not (= m1 m2)) 
                    (throw (Exception. (str "expected final adjacency lists to balance: " mincut))))
                    m1
                ))
  ([graph n-runs] (reduce min (map (fn [x] (let [r (min-cut graph)] (do (println r) r ))) (range n-runs)))))
