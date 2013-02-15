(ns contrib)

(defmacro dbg[x] `(let [x# ~x] (println '~x "=" x#) x#))

(defn str-transient [t]
  (str "["
      (clojure.string/join " " (for [i (range 0 (count t))] (nth t i)))
      "]"))

(defn println-transient [t]
  (println (str-transient t)))