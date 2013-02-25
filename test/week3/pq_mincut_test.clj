(ns week3.pq-mincut-test
(:require
    [clojure.test :refer :all] 
    [clojure.set :refer [subset? union difference]]
    [week3.pq-mincut :refer :all]
    [testing :refer :all]))

(def simple-test-cases 
  '({1 #{2}, 2 #{1}}
    {1 #{2,3}, 2 #{1}, 3 #{1}}
    {1 #{2,3}, 2 #{1,3}, 3 #{1,2}}
    {1 #{2,3}, 2 #{1,3}, 3 #{1,2}}))

(deftest should-find-200-nodes
    (is (= 200 (count pq-node-map)))
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
  (is (= {1 #{2}, 2 #{1}}
         (shrink-graph {1 #{2,3}, 2 #{1}, 3 #{1}} 2 3)))
  (is (= {1 #{3}, 3 #{1}}
         (shrink-graph {1 #{2,3}, 2 #{1}, 3 #{1}} 1 2)))
  )

(deftest shrink-graph-should-update-3rd-party-nodes-adjacent-to-shrunk-node
  (is (= {1 #{3 4}, 3 #{1 4 5}, 4 #{1 3 5} 5 #{3 4}}
         (shrink-graph {1 #{2 3 4}, 2 #{1}, 3 #{1 4 5} 4 #{1 3 5} 5 #{3 4}} 1 2)))
  )

(deftest shrink-graph-should-not-change-3rd-party-nodes-where-not-adjacent-to-shrunk-node
  (is (= {1 #{3 4}, 3 #{1 4 5}, 4 #{1 3 5} 5 #{3 4}}
         (shrink-graph {1 #{2 3 4}, 2 #{1}, 3 #{1 4 5} 4 #{1 3 5} 5 #{3 4}} 1 2)))
  )

(deftest shrink-graph-should-merge-adjacency-sets-preserving-some-invariants
  (are [input n1 n2] 
      (lett [result (shrink-graph input n1 n2)
             new-n1 (get result n1)
             old-n1 (get input n1)
             old-n2 (get input n2)]
          (is (= new-n1 (difference (union old-n1 old-n2) #{n1 n2})))
          (is (nil? (get result n2))))
    {1 #{2,3}, 2 #{1}, 3 #{1}} 2 3
    {1 #{2,3}, 2 #{1}, 3 #{1}} 1 2
    {1 #{2 3 4}, 2 #{1}, 3 #{1 4 5} 4 #{1 3 5} 5 #{3 4}} 1 2
  ))

(deftest shrink-graph-should-merge-adjacency-sets-removing-self-loops
  (is (= {1 #{3}, 3 #{1}}
         (shrink-graph {1 #{2,3}, 2 #{1}, 3 #{1}} 1 2)))
  )

(deftest shrink-graph-should-replace-refs-to-n2-with-n1
  (is (= {1 #{2}, 2 #{1}}
         (shrink-graph {1 #{2,3}, 2 #{1,3}, 3 #{1,2}} 2 3)))
  )

