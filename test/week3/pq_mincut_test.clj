(ns week3.pq-mincut-test
  (:require 
    [clojure.test :refer :all]
    [week3.pq-mincut :refer :all]
  ))

(deftest seq-from-tab-delimited-lines-should-be-a-seq-of-vectors-of-node-and-seq-of-edges-es
  (let [s (seq-from-tab-delimited-lines "1\t10\t11\t\n2\t21\t22\t")
        row (first s)]
      (is (= '([1 (10 11)] [2 (21 22)]) s))
      (is (seq? s))
      (is (vector? row))
      (is (int (first row)))
      (is (seq? (second row)))
      (is (int (first (second row))))))
