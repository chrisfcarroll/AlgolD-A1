(ns week1.karatsuba)

(defn title "Karatsuba Algorithm on Clojure decimal BigInts. 
  With shift operations done by conversion to and from string.")
 
(defn abs [x] (if (< x 0) (- x) x))

(defn half-length-of-longer-number "as in, the number of digits in the integer"
  [x y] (let [lengthx  (.length (str x))
              lengthy  (.length (str y)) 
              digitsinx (if (neg? x) (dec lengthx) lengthx )
              digitsiny (if (neg? y) (dec lengthy) lengthy )]
          (int (/ (max digitsinx digitsiny) 2))))

(defn *d "multiplies 2 numbers, but throws if either number is more than 1 digit so you can't cheat." 
  [x y] (if (and (<= -9 x 9) (<= -9 y 9)) 
            (* x y) 
            (throw (IllegalArgumentException. 
                     (format "Cheated - tried to multiply %d & %d which are not both 1 digit numbers" x y)))))

(defn read-string-but-drop-leading-zeroes [s] 
  (let [result (apply str (drop-while #(= \0 %) s))] (if (= "" result) 0 (read-string result))))

(defn decimal-shift-right [x i] 
  (let [s   (str x)
        len (.length s)
        short? (<= len i)]
        (if short? 0 
                   (read-string (.substring s 0 (- len i))))))

(defn modulo-10-power [x i] 
  (if (<= i 0) 
    (throw (IllegalArgumentException. "This function only defined for i a positive integer."))
    (let [s   (str x)
          lens (.length s)
          short? (<= lens i)]
          (if short? x 
                     (read-string-but-drop-leading-zeroes (subs s (- lens i)))))))

(defn e "as in exponent" [x  n] (read-string (apply str x (take n (repeat "0")))))

(defn- **k [x y]
  (let [n      (half-length-of-longer-number x y)]
    (cond 
      (= n 0) (*d x y)
      :else 
        (let [a       (decimal-shift-right x n)
              b       (modulo-10-power     x n)
              c       (decimal-shift-right y n)
              d       (modulo-10-power     y n)
              z2      (**k a c)
              z0      (**k b d)
              z1      (- (**k (+ a b) (+ c d)) (+ z2 z0))  ]
              (+ (e z2 (* 2 n))  (e z1 n) z0)))))

(defn *k [x y] 
  (let [negresult? (not (= (neg? x) (neg? y)))
        ax         (abs x)
        ay         (abs y)
        result     (**k ax ay)]
    (if negresult? (- result) result)))
