(ns week2.programming-question2-test
  (:require 
    [clojure.test :refer :all]
    [week2.programming-question2 :refer :all]))

(comment deftest calculate-qs-comparisons-with-1st-element-as-pivot-should-be-correct
  (testing "QS Comparisons"
    (are [expected input] (= expected (calculate-qs-comparisons-with-1st-element-as-pivot input))
        0 '(1)
    )))