(ns week1.merge-sort-test
  (:use clojure.test
        week1.merge-sort))

(deftest merge-sort-should-be-correct
  (are [ inputs] (<= (merge-sort inputs))
        '(1)
        '(2 1)
        '(1 4 3 2)
        '(2 1 5 6 7 8 24 3 1232 676574000000000 -1 -1 -1 3213.9080 55.00 59/113 60/113 60/119 0.0)
        ))

(deftest two-halves-of-should-be-correct
  (are [expected input] (= expected (two-halves-of input))
        '( (1 2 3) (4 5 6))   '(1 2 3 4 5 6)
        '( (1 2)   (3 4 5))   '(1 2 3 4 5)
        '( (1)     (2) )      '(1 2)
        '( ()      (1) )      '(1)
        '( ()      ()  )      '()
  ))