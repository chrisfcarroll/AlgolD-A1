(ns week1.merge-sort)

(defn two-halves-of [a]
  (let [n (int (/ (count a) 2))]   (split-at n a)))

(defn merge-sort [ sequence ]

  (if (empty? (rest sequence)) 
    sequence

    (let [[interiml interimr] (two-halves-of sequence)
          sortedl (merge-sort interiml)
          sortedr (merge-sort interimr)]
          
        (comment println "input sequence : " sequence "(sortedl, sortedr) : (" sortedl sortedr ")")
        
        (loop [a ()
               finall sortedl
               finalr sortedr]
          (cond
            (empty? finall) (concat a finalr)
            (empty? finalr) (concat a finall)
            :else
              (let [fLeft  (first finall)
                    fRight (first finalr)] 
                (comment println "a :" a "; (finall, finalr) : (" finall finalr ")")
                (if (< fLeft fRight)
                  (recur
                      (concat a (list fLeft))
                      (rest finall)
                      finalr)
                  (recur
                      (concat a (list fRight))
                      finall
                      (rest finalr))
                  )))))))
