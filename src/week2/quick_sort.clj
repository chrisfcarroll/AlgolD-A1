(ns week2.quick-sort)

(defn myfilter[predicate sequence]
  (loop [output () 
         input sequence]
    (comment println "input, output : " input "," output  ". Empty input?" (empty? input))
    (if (empty? input)
      output
      (recur (if (predicate (first input)) 
                (concat output (list (first input))) 
                output)
             (rest input))
  )))

(defn choose-pivot 
  "returns a single element chosen from the given sequence using the method specified by how. Valid values for how
   :first    - returns (first sequence)"
  [how sequence] 
  (case how
    :first (first sequence)
    (throw (IllegalArgumentException. ":first is the only implementation."))))

(defn qs-partition-on
  "Partitions the sequence using < comparison with the given pivot. Returns a vector of two sequences
  [(less than the pivot) (not less than the pivot)]"
  [sequence pivot]
  [() ()])


(defn vrange2 [n]
  (loop [i 0 v (transient [])]
    (if (< i n)
      (recur (inc i) (conj! v i))
      (persistent! v))))


(defn quick-sort [sequence]
  (if (empty? (rest sequence))
    sequence
    (let [pivot (choose-pivot :first sequence)
          lft   (myfilter #(<  % pivot) sequence )
          same  (myfilter #(=  % pivot) sequence )
          rt    (myfilter #(> % pivot) sequence )]
      (comment println "will concat :"  lft "," same "," rt )
      (concat (quick-sort lft) same (quick-sort rt)))))
