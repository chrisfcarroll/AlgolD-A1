(ns week2.pq2-quick-sort-test
  (:require 
    [clojure.test :refer :all]
    [testing :refer :all]
    [week2.pq2-quick-sort :refer :all]
    ))

(comment deftest quick-sort-should-be-correct
  (are [input] (<= (quick-sort input))
        [1]
        [1 2 3]
        [1 2 3 45243 2134 657 342 4 8 3 345435 576573]
  ))

(deftest choose-pivot-index-should-choose-an-index-within-the-given-range
  (are [input left-index right-index] 
      (<=  left-index (choose-pivot-index :first input left-index right-index) right-index)
    '(1 2 3) 0 3
    '(1 2 3) 1 1
    ))

(deftest <-at-should-be-correct-when-true
  (are [input i j] (<-at input i j) 
    [1 3 2] 0 1
    [1 3 2] 2 1
    [1 3 2] 0 2
    )
  )
(deftest <-at-should-be-correct-when-false
  (are [input i j] (not (<-at input i j))
    [1 3 2] 1 0
    [1 3 2] 1 2
    [1 3 2] 2 0
    )
  )
(deftest swap-at!-should-be-correct
  (are [expected tseq i j] (= expected (persistent! (swap-at! (transient tseq) i j)))
      [1 0 2] [0 1 2] 0 1
      [0 2 1] [0 1 2] 1 2
  ))

(deftest qs-partition-about-and-get-new-pivot-index!-should-be-correct
  (are [expectedtseq 
        expected-new-index 
        input 
        pivot-index]

    (lett [tseq       (transient input)
           new-index  (qs-partition-about-and-get-new-pivot-index! tseq pivot-index)]
        (is (= expected-new-index  new-index))
        (is (= expectedtseq        (persistent! tseq))))

      [0 1 2] 0, [0 1 2] 0
      [0 1 2] 1, [1 0 2] 0
  ))



