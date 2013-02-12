(ns repl
  (:require
    [clojure.repl :refer [source]] 
    [week1.karatsuba :refer :all]
    [week1.merge-sort :refer :all]
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

(defn help [] (println "
  lein run <name> to show the source code and example for algorithm <name>

  lein repl to be able to play with it.
  "))

(defn -main 
  ([subject] (condp #(.startsWith %1 %2) subject
              "karatsuba"  (show-karatsuba)
              "merge-sort" (show-merge-sort)
              (-main)))
  ([]  (do (show-karatsuba) (show-merge-sort) (help))))
