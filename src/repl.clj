(ns repl
  (:require [week1.karatsuba :refer :all]))

(defn -main [& args]
  (println "karatsuba multiplication is bound the function *k.
    Example: (*k 1234567890 9876543210)")
   (*k 1234567890 9876543210)
  )