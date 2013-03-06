(ns week5.dijkstra-test
  (:require 
    [clojure.test :refer :all]
    [week5.dijkstra :refer :all]))

(def test-graph-1  {1 {}})
(def test-graph-2  {1 {2 2}, 2 {1 1}})
(def test-graph-3a {1 {2 99, 3 3}, 2 {}, 3 {} })
(def test-graph-3b {1 {2 99, 3 3}, 2 {3 3}, 3 {2 2, 1 1} })
(def test-graph-4  {1 {2 99, 3 3}, 2 {3 3, 4 99}, 3 {2 2, 1 1, 4 4} 4 {1 1, 2 2, 3 3} })

(deftest shortest-distances-and-paths-from-node-should-be-correct 
  (let [[lengths paths] (shortest-distances-and-paths-from-node 1 test-graph-4)]
    (is (= {1 0, 3 3, 2 5, 4 7} lengths))
    (is (= [1 3 2 4] (get paths 4) ))
    ))

(deftest shortest-distances-from-node-should-be-correct-from-node-1-to-small-graph
  (is (= {1 0}
         (shortest-distances-from-node 1 test-graph-1)))
  (is (= {1 0, 2 2} 
         (shortest-distances-from-node 1 test-graph-2)))
  )

(deftest choose-next-node-should-obey-dijkstras-greedy-criterion
  (is (= nil (choose-next-node 1 test-graph-1  #{1} {}          {1 0} )))
  (is (= [2 1 2] (choose-next-node 1 test-graph-2  #{1} {2 {1 1}}   {1 0} )))
  (is (= [3 1 3] (choose-next-node 1 test-graph-3a #{1} (dissoc test-graph-3a 1) {1 0} )))
  (is (= [2 3 5] (choose-next-node 
                    1 
                    test-graph-4 
                    #{1 3} 
                    (dissoc test-graph-4 1 3) 
                    {1 0, 3 3} )))
  )

(deftest next-nodes-list-should-return-nodes-not-yet-seen
  (is (= () (next-nodes-list test-graph-1 #{1} {} {1 0})))
  (is (= '([2 1 2]) (next-nodes-list test-graph-2 #{1} (dissoc test-graph-2 1) {1 0})))
  (is (= '([2 1 99] [2 3 5] [4 3 7]) 
                  (next-nodes-list 
                    test-graph-4 
                    #{1 3} 
                    (dissoc test-graph-4 1 3) 
                    {1 0, 3 3} )))
)
