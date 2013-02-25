(ns week3.indexing-test
  (:require 
    [clojure.test :refer :all]
    [week3.indexing :refer :all]
  ))

(deftest mapentries-from-tab-delimited-lines-should-be-a-seq-of-vectors-of-node-and-set-of-edges-es
  (let [s (mapentries-from-tab-delimited-lines "1\t10\t11\t\n2\t21\t22\t")
        row (first s)]
      (is (= '([1 #{10 11}] [2 #{21 22}]) s))
      (is (seq? s))
      (is (vector? row))
      (is (int (first row)))
      (is (set? (second row)))
      (is (int (first (second row))))))

(deftest flatten-1tomany-map-into-kv-pairs-should-be-correct
  (is (= (reverse '([1 10] [1 12] [1 13] [2 20] [2 21] [2 22]))
         (flatten-1tomany-map-into-kv-pairs {1 [10 12 13], 2 [20 21 22]}))))

(deftest merge-kv-pair-into-1tomany-map-should-add-pairs-to-empty-map
  (is (= {10 [1], 11 [1], 20 [2]}
         (reduce merge-kv-pair-into-1tomany-map {} '([10 1] [11 1] [20 2]))
      )))

(deftest merge-kv-pair-into-1tomany-map-should-merge-manies-given-1-to-many
  (is (= {1 '(13 12 11), 2 '(20) }
         (reduce merge-kv-pair-into-1tomany-map {} '([1 11] [1 12] [1 13] [2 20]))
      )))

(deftest pivot-merge-kv-pair-into-1tomany-map-should-merge-manies-given-1-to-many
  (is (= {20 '(2), 12 '(2 1), 11 '(1)}
         (reduce pivot-merge-kv-pair-into-1tomany-map {} '([1 11] [1 12] [2 20] [2 12] ))
      )))