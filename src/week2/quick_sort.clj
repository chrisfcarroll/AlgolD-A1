(ns week2.quick-sort)


(defn quick-sort [sequence]
  (if (empty? (rest sequence))
    sequence
    (let [pivot (first sequence)
          lft   (filter #(<  % pivot) sequence )
          same  (filter #(=  % pivot) sequence )
          rt    (filter #(> % pivot) sequence )]
      (comment println "will concat :"  lft "," same "," rt )
      (concat (quick-sort lft) same (quick-sort rt)))))