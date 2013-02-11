(ns repl
  (:require
    [clojure.repl :refer [source]] 
    [week1.karatsuba :refer :all]))

(defn -main [& args]
  (do
    (ns week1.karatsuba)
    (println "karatsuba multiplication is bound the function *k.
    Example: (*k 1234567890 9876543210) = " (*k 1234567890 9876543210) "

    ") 
    (source *k)
    (println)
    (source **k)))