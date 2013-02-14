(ns week2.quick-sort-test
  (:require 
    [clojure.test :refer :all]
    [testing :refer :all]
    [week2.quick-sort :refer :all]
    ))

(deftest quick-sort-should-be-correct
  (are [input] (<= (quick-sort input))
        '(1)
        '(1 2 3)  '(1 2 3)
        (list 1 2 3 45243 2134 657 342 4 8 3 345435 576573)
  ))

(deftest myfilter-should-filter-correctly
  (are [expected predicate input] (= expected (myfilter predicate input))
    '(1 2 1 2) #(< % 3) [ 1 2 4 5 6 7 1 2 5 3 4]
    ))

(deftest choose-pivot-should-return-an-element-of-the-input
  (are [input] (some? #(= % (choose-pivot :first input)) input )
      '(1 2 3)
    ))

(deftest qs-partition-should-not-lose-any-elements
  (are [input pivot] 
      (lett [output (qs-partition-on input pivot)
             combined-output (flatten output)]
        (println "input,output:" input "," output "," combined-output)
        (aare [element] (in-list? element combined-output) input)
      )
    '(1 2 3) 1
    ))