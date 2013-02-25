(ns week3.pq-mincut
  (:require 
    [week3.indexing :refer :all]
    [multiset.core :refer [multiset multiset? union minus multiplicity multiplicities] :as ms]))

; Load the file into a map of node-node adjacencies
;(def pq-input-txt (slurp "src/week3/kargerMinCut.txt"))
;(def pq-input-rows (mapentries-from-tab-delimited-lines pq-input-txt))
(defn graph-from-file [filepath]
  (into (sorted-map) (mapentries-from-tab-delimited-lines(slurp filepath))))
(def pq-node-map (graph-from-file "src/week3/kargerMinCut.txt"))
(def testgraph1  (graph-from-file "src/week3/testcase1.txt"))
(def testgraph4  (graph-from-file "src/week3/testcase4.txt"))

;
(defn choose-2-adjacent-nodes [node-map]
  (let [nodecount (count node-map)
        i1    (rand-int nodecount)
        n1    (nth (keys node-map) i1)
        adjs (get node-map n1)
        adjcount (count adjs)
        i2  (rand-int adjcount)
        n2  (nth (seq adjs) i2)]
    [n1 n2]))

(defn swap-x-to-y [multiset-input x y] 
  (reduce #(conj %1 (if (= x %2) y %2)) (ms/multiset) multiset-input))

(defn shrink-graph 
  ([node-map] (let [[n1 n2] (choose-2-adjacent-nodes node-map)] 
                      (shrink-graph node-map n1 n2)))
  ([node-map n1 n2] 
    (let [n1adj (get node-map n1)
          n2adj (get node-map n2)
          n1adjminusn2 (minus n1adj (apply multiset (take (multiplicity n1adj n2) (repeat n2))))
          n2adjminusn1 (minus n2adj (apply multiset (take (multiplicity n2adj n1) (repeat n1))))
          new-adjacencies (ms/union n1adjminusn2 n2adjminusn1)
          t1 (dissoc node-map n2)
          t2 (assoc t1 n1 new-adjacencies)]
      (comment println "(joining" n2 n2adj "(" (multiset? n2adj) ") to" n1 n1adj "(" (multiset? n2adj) ") merged:" merged-adjacencies " new:" new-adjacencies
               "; tmp2:" t2)
      (loop [acc t2 i 0]
        (let [n (nth (keys acc) i)
              old-adjs (get acc n)
              adj-n2?  (old-adjs n2)]
          (comment println n ": " old-adjs " : has " adj-n2? 
                      "? ->" (swap-x-to-y old-adjs n2 n1) "else" old-adjs)
          (let [
              new-adjs (if adj-n2? (swap-x-to-y old-adjs n2 n1) old-adjs)
              t3 (assoc acc n new-adjs)]
              (if (= (count acc) (inc i))
                t3
                (recur t3 (inc i)))))))))

(defn shrink-graph-until-two-nodes-left [node-map]
  (if (<= (count node-map) 2) 
    node-map
    (recur (shrink-graph node-map))))

(defn min-cut 
  ([node-map] (let [mincut (shrink-graph-until-two-nodes-left node-map)
                m1 (second (first (seq (multiplicities (second (first mincut))))))
                m2 (second (first (seq (multiplicities (second (second mincut))))))]
                (if (not (= m1 m2)) 
                    (throw (Exception. (str "expected final adjacency lists to balance: " mincut))))
                    m1
                ))
  ([node-map n-runs] (reduce min (map (fn [x] (min-cut node-map)) (range n-runs)))))


(comment 
"The file contains the adjacency list representation of a simple undirected graph. There are 200 vertices labeled 1 to 200. The first column in the file represents the vertex label, and the particular row (other entries except the first column) tells all the vertices that the vertex is adjacent to. So for example, the 6th row looks like : 6  155 56  52  120 ....... 

This just means that the vertex with label 6 is adjacent to (i.e., shares an edge with) the vertices with labels 155,56,52,120,......,etc

Your task is to code up and run the randomized contraction algorithm for the min cut problem and use it on the above graph to compute the min cut. (HINT: Note that you'll have to figure out an implementation of edge contractions. Initially, you might want to do this naively, creating a new graph from the old every time there's an edge contraction. But you should also think about more efficient implementations.) (WARNING: As per the video lectures, please make sure to run the algorithm many times with different random seeds, and remember the smallest cut that you ever find.) Write your numeric answer in the space provided. So e.g., if your answer is 5, just type 5 in the space provided.")

(defn merge2
  "An example implementation of `merge` using transients."
  [x y]
  (persistent! (reduce
                (fn [res [k v]] (assoc! res k v))
                (transient x)
                y)))
