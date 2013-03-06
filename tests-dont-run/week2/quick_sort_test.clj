(ns week2.quick-sort-test
  (:require 
    [clojure.test :refer :all]
    [testing :refer :all]
    [week2.quick-sort :refer :all]
    [contrib :refer :all]
    ))

(deftest quick-sort-should-be-correct-for-any-pivot
  (doseq [pivot-choice [:last :first :median-of-3]]
    (are [input] (apply <= (quick-sort input pivot-choice))
          [1]
          [1 2 3]
          [1 3 4 2 5]
          [3 1 2 4 5]
          [3 1 4 2 5]
          [5 1 2 3 4]
          [45 21 65 34 4 8 35 57 3 2 5]
  )))

(deftest choose-pivot-index-first-should-choose-left-index
  (are [input left-index right-index] 
      (= left-index (choose-pivot-index :first input left-index right-index))
    '(1 2 3) 0 2
    '(1 2 3) 1 1
    ))

(deftest choose-pivot-index-last-should-choose-right-index
  (are [input left-index right-index] 
      (= right-index (choose-pivot-index :last input left-index right-index))
    '(1 2 3) 0 2
    '(1 2 3) 1 1
    ))

(deftest choose-pivot-index-median-of-3-should-return-index-of-median-of-first-last-middle
  (are [expected input left-index right-index] 
      (= expected (choose-pivot-index :median-of-3 input left-index right-index))
     0 '(1 3 2) 0 0
     1 '(1 2 3) 0 2
     0 '(2 1 3) 0 2
     2 '(1 3 2) 0 2
     0 '(1 2)   0 1
    ))

(deftest <-at-should-be-true-when-element-at-left-less-than-element-at-right
  (are [input i j] (<-at input i j) 
    [1 3 2] 0 1
    [1 3 2] 2 1
    [1 3 2] 0 2
    )
  )
(deftest <-at-should-be-false-when-element-at-left-not-less-than-element-at-right
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
      [2 1 0] [0 1 2] 2 0
      [0 2 1] [0 1 2] 2 1
  ))

(defn is-partitioned-on-pivot? 
  ([tseq pivot-index from to]
    (and 
      (is (<= from pivot-index to))
      (every? #(do %) (doseq [i (range from pivot-index)] (is (<-at tseq i pivot-index))))
      (every? #(do %) (doseq [i (range (inc pivot-index) (inc to))] (is (<-at tseq pivot-index i))))
      ))
  ([tseq pivot-index] (is-partitioned-on-pivot? tseq pivot-index 0 (dec (count tseq)))))

(deftest qs-partition-about-and-get-new-pivot-index!-should-be-correct
  (are [expected-new-index input pivot-index]
    (lett [tseq       (transient input)
           new-index  (qs-partition-about-and-get-new-pivot-index! tseq pivot-index)]
        (is (= expected-new-index new-index))
        (is (is-partitioned-on-pivot? tseq new-index )))

      0, [1 2 0] 2
      5, [3 0 1 2 4 5] 5
      1, [3 5 4 2 0 1] 5 

  ))