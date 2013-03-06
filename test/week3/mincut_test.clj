(ns week3.mincut-test
(:require
    [clojure.test :refer :all] 
    [clojure.set :refer [subset? union difference]]
    [multiset.core :as ms :refer [multiset multiset?]]
    [week3.mincut :refer :all]
    [testing :refer :all]))

(def simple-test-cases 
  '({1 (multiset 2), 2 (multiset 1)}
    {1 (multiset 2,3), 2 (multiset 1), 3 (multiset 1)}
    {1 (multiset 2,3), 2 (multiset 1,3), 3 (multiset 1,2)}
    {1 (multiset 2,3), 2 (multiset 1,3), 3 (multiset 1,2)}
    {1 (multiset 2 3 4), 2 (multiset 1), 3 (multiset 1 4 5) 4 (multiset 1 3 5) 5 (multiset 3 4)}
    {1 (multiset 2 3 4), 2 (multiset 1), 3 (multiset 1 4 5) 4 (multiset 1 3 5) 5 (multiset 3 4 6 7) 6 (multiset 5 7) 7 (multiset 5 6) }))

(deftest should-find-200-nodes
    (is (= 200 (count pq-graph)))
    )

(deftest choose-2-adjacent-nodes-should-choose-two-nodes-in-range
  (are- [input]
      (lett [[n1 n2] (choose-2-adjacent-nodes input)
            cnt (count input)]
        (is (<= 1 n1 cnt))
        (is (<= 1 n2 cnt))
        (is (not= n1 n2)))
    simple-test-cases
  ))

(deftest shrink-graph-should-remove-nodes
  (are- [input] 
      (lett [result     (shrink-graph input)
             resultkeys (set (keys result))
             inputkeys  (set (keys input ))]
          (is (= (dec (count input)) (count result)))
          (is (subset? resultkeys inputkeys)))
    simple-test-cases
  ))

(deftest shrink-graph-should-merge-adjacency-sets
  (is (= {1 (multiset 2 2), 2 (multiset 1 1)}
         (shrink-graph {1 (multiset 2,3), 2 (multiset 1), 3 (multiset 1)} 2 3)))
  (is (= {1 (multiset 3), 3 (multiset 1)}
         (shrink-graph {1 (multiset 2,3), 2 (multiset 1), 3 (multiset 1)} 1 2)))
  )

(deftest shrink-graph-should-update-3rd-party-nodes-adjacent-to-shrunk-node
  (is (= {1 (multiset 3 4), 3 (multiset 1 4 5), 4 (multiset 1 3 5) 5 (multiset 3 4)}
         (shrink-graph {1 (multiset 2 3 4), 2 (multiset 1), 3 (multiset 1 4 5) 4 (multiset 1 3 5) 5 (multiset 3 4)} 1 2)))
  )

(deftest shrink-graph-should-not-change-3rd-party-nodes-where-not-adjacent-to-shrunk-node
  (is (= {1 (multiset 3 4), 3 (multiset 1 4 5), 4 (multiset 1 3 5) 5 (multiset 3 4)}
         (shrink-graph {1 (multiset 2 3 4), 2 (multiset 1), 3 (multiset 1 4 5) 4 (multiset 1 3 5) 5 (multiset 3 4)} 1 2)))
  )

(deftest shrink-graph-should-merge-adjacency-sets-preserving-some-invariants
  (are [input n1 n2] 
      (lett [result (shrink-graph input n1 n2)
             new-n1 (get result n1)
             old-n1 (get input n1)
             old-n2 (get input n2)]
          (is (= new-n1 (disj (ms/union old-n1 old-n2) n1 n2)))
          (is (nil? (get result n2))))
    {1 (multiset 2,3), 2 (multiset 1), 3 (multiset 1)} 2 3
    {1 (multiset 2,3), 2 (multiset 1), 3 (multiset 1)} 1 2
    {1 (multiset 2 3 4), 2 (multiset 1), 3 (multiset 1 4 5) 4 (multiset 1 3 5) 5 (multiset 3 4)} 1 2
  ))

(deftest shrink-graph-should-merge-adjacency-sets-removing-self-loops
  (is (= {1 (multiset 3), 3 (multiset 1)}
         (shrink-graph {1 (multiset 2,3), 2 (multiset 1), 3 (multiset 1)} 1 2)))
  )

(deftest shrink-graph-should-replace-refs-to-n2-with-n1
  (is (= {1 (multiset 2 2), 2 (multiset 1 1)}
         (shrink-graph {1 (multiset 2,3), 2 (multiset 1,3), 3 (multiset 1,2)} 2 3)))
  )

(deftest shrink-graph-until-two-nodes-left-should-do-so
  (are- [input] 
      (= 2 (count (shrink-graph-until-two-nodes-left input)))
      simple-test-cases))

(deftest should-not-get-node-adjacent-to-self
  (let [testcase {1 (multiset 2 4 3 3), 
                  2 (multiset 3 3 4 1), 
                  3 (multiset 1 1 2 2 4 4), 
                  4 (multiset 1 2 3 3)}]
    (let [result (shrink-graph testcase 1 3)
          adj1 (get result 1)]
      (comment println result)
      (is-not (contains? adj1 1))
      )))

(deftest test-case-1-should-probably-get-close-in-3-runs
  (is (<= (min-cut testgraph1 3) 5)))

