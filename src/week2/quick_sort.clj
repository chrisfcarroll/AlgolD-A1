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

(defn quick-sort [sequence]
  (if (empty? (rest sequence))
    sequence
    (let [pivot (first sequence)
          lft   (myfilter #(<  % pivot) sequence )
          same  (myfilter #(=  % pivot) sequence )
          rt    (myfilter #(> % pivot) sequence )]
      (comment println "will concat :"  lft "," same "," rt )
      (concat (quick-sort lft) same (quick-sort rt)))))