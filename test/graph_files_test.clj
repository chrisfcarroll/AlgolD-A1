(ns graph-files-test
  (:require 
    [clojure.test :refer :all]
    [graph-files :refer :all]
    [multiset.core :refer [multiset? multiset] :as ms]
  ))

(deftest mapentries-from-tab-delimited-lines-should-be-a-seq-of-vectors-of-node-and-multiset-of-edges-es
  (let [s (mapentries-from-tab-delimited-lines "1\t10\t11\t\n2\t21\t22\t")
        row (first s)]
      (is (= (list [1 (multiset 11 10)] [2 (ms/multiset 21 22)]) s))
      (is (seq? s))
      (is (vector? row))
      (is (int (first row)))
      (is (multiset? (second row)))
      (is (int (first (second row))))))
