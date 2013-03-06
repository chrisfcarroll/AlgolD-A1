(ns graph-files-test
  (:require 
    [clojure.test :refer :all]
    [graph-files :refer :all]
    [multiset.core :refer [multiset? multiset] :as ms]
  ))

(deftest unweighted-graph-from-tab-delimited-lines-should-be-a-seq-of-vectors-of-node-and-multiset-of-edges-es
  (let [s (unweighted-graph-from-tab-delimited-lines "1\t10\t11\t\n2\t21\t22\t")
        row (first s)]
      (is (= (list [1 (multiset 11 10)] [2 (ms/multiset 21 22)]) s))
      (is (seq? s))
      (is (vector? row))
      (is (int (first row)))
      (is (multiset? (second row)))
      (is (int (first (second row))))))


(deftest weighted-graph-without-parallels-from-tab-comma-delimited-lines-should-be-a-seq-of-vectors-of-node-and-map-of-edge-length-pairs-es
  (let [s (weighted-graph-without-parallels-from-tab-comma-delimited-lines "1\t10,110\t11,111\t\n2\t21,121\t22,212\t")
        row (first s)]
      (is (= (list [1 {10 110, 11 111}] [2 {21 121, 22 212}]) s))
      (is (seq? s))
      (is (vector? row))
      (is (int (first row)))
      (is (map? (second row)))
      (is (vector? (first (second row))))
      (is (int     (first (first (second row)))))))