(ns week2.quick-sort-test
  (:require 
    [clojure.test :refer :all]
    [week2.quick-sort :refer [quick-sort]]
    ))

(deftest quick-sort-should-be-correct
  (are [input] (is (<= (quick-sort input)))
        '(1)
        '(1 2 3)  '(1 2 3)
        (list 1 2 3 45243 2134 657 342 4 8 3 345435 576573)
  ))