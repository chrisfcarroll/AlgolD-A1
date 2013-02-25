(ns repl
  (:require
    [clojure.repl :refer [source]] 
    [week1.karatsuba :refer :all]
    [week1.merge-sort :refer :all]
    [week2.quick-sort :refer :all]
    [week3.pq-mincut :refer :all]
  ))

(defn show-karatsuba []
  (do
    (ns week1.karatsuba)
    (println "karatsuba multiplication is bound the function *k.
    Example: (*k 1234567890 9876543210) = " (*k 1234567890 9876543210) "

    ") 
    (source *k)
    (println)
    (source **k)))

(defn show-merge-sort []
  (do
    (ns week1.merge-sort)
    (println "merge-sort sorts a sequence:
    Example: (merge-sort '(1 9 3 8 3 4)) = " (merge-sort '(1 9 3 8 3 4)) "

    ") 
    (source merge-sort)))

(defn show-quick-sort []
  (do
    (ns week2.quick-sort)
    (println "
    Example: (quick-sort '(1 9 3 8 3 4)) = " (quick-sort '(1 9 3 8 3 4)) "
    ") 
    (source quick-sort)))

(defn help [] (println "
  ------------------------------------------------------------------------
  lein run <name> to show the source code and example for algorithm <name>

  lein repl to be able to play with it.
  hints for playing in the repl:
  ==> (ns week2.quick-sort) ; changes the current namespace
  ==> (quick-sort '(1 4 5 3 0 9 5))
  ==> (ns week1.karatsuba)
  ==> (*k 12345 12345)

  "))

(ns repl)

(defn -main 
  ([subject] (condp #(.startsWith %1 %2) subject
              "karatsuba"  (show-karatsuba)
              "merge-sort" (show-merge-sort)
              (-main)))
  ([]  (do (help) (ns week3.pq-mincut))))


(defn load-use [& nses]
  (doseq [ns nses] 
    (let [filename (str "src/"  (.. ns (replace "-"  "_") (replace "." "/")) ".clj"  )]
        (load-file filename)
        (use (symbol ns)))))
