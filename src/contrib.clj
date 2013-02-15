(ns contrib)
(defmacro dbg[x] `(let [x# ~x] (println '~x "=" x#) x#))