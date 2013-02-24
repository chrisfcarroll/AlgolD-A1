(ns week3.pq-mincut
  (:require [clojure.string :refer [split-lines split trimr]]))

; Load the file into a map of nodes->edges and a map of edges->nodes
(def pq-input-txt (slurp "src/week3/kargerMinCut.txt"))

(defn mapentries-from-tab-delimited-lines [string]
  (for [line (split-lines string)] 
    (let [[nstring edgesstring] (split line #"\t" 2) 
          node (Integer/parseInt nstring)
          edges (map #(Integer/parseInt %) (split edgesstring #"\t"))]
        [node edges])))

(def pq-input-rows (mapentries-from-tab-delimited-lines pq-input-txt))

(def pq-node-index (into (sorted-map) pq-input-rows))

(def pq-edges-2 
  (let [pq-edges-builder (transient {} )]
    (doseq [[node edges] pq-input-rows]
      (doseq [edge edges]
        (let [prev-node-for-edge (get pq-edges-builder edge)]
          (assoc! pq-edges-builder edge (cons node prev-node-for-edge)))))
    (persistent! pq-edges-builder)))

(defn pq-edges-3 [input-rows]
    (let [res {0 0}]
      (reduce 
        (fn [[node edges] pq-input-rows] 
          (for [edge edges]
            (let [prev-node-for-edge (get res edge)]
              (assoc res edge (cons node prev-node-for-edge)))))
        input-rows)))

(defn e3 [x] (reduce (fn [res [k v]] (for [e v] (conj res [e k]))) () x))

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

(defn flatten-1tomany-map-into-kv-pairs [onetomanymap]
  (reduce (fn [acc [key vals]] (apply conj acc (for [val vals] [key val])) ) () onetomanymap))

(defn merge-kv-pair-into-1tomany-map [acc [key newval]]
  (let [old-v-list (get acc key)
        new-v-list (conj old-v-list newval)]
    (assoc acc key new-v-list)))

