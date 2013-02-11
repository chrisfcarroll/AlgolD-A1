(ns week1.karatsuba-test
  (:use clojure.test
        week1.karatsuba))

(deftest karatsuba*-should-multiply-correctly
  (are [a b expected] (= expected (*k a b))
        1 1 1,
        8 9 72,
        12 2 24,
        1234 1000 1234000,
        12345678901234567890   987654321  12193263112482853211126352690,
  ))

(deftest karatsuba*-should-do-negatives-correctly
  (are [a b expected] (= expected (*k a b))
          1   -1   -1,
         -1    1   -1,
         -1   -1    1,
        -12    2  -24,
       12345678901234567890  -987654321 -12193263112482853211126352690,
       -12345678901234567890   987654321 -12193263112482853211126352690,
       -12345678901234567890  -987654321  12193263112482853211126352690,
  ))

(deftest karatsuba*-should-multiply-by-zero-correctly
  (are [x y] (= 0 (*k x y))
        0 1,
        0 9,
        9 0,
        123456789123456789 0
  ))

(deftest half-length-of-longer-number-should-be-correct
  (are [a b expected] (= (half-length-of-longer-number a b) expected)
        1    9         0,
        12   9         1,
        1234 1         2,
        1234 123       2,
        1234 123456789 4,
  ))

(deftest half-length-of-longer-number-should-be-correct-for-negative-numbers
  (are [a b expected] (= (half-length-of-longer-number a b) expected)
        1 -1 0
  ))


(deftest e-should-muliply-by-nth-power-of-10
  (are [s n expected] (= expected (e s n))
    12345 0 12345
    12345 5 1234500000))

(deftest decimal-shift-right-shold-be-correct
  (are [x n expected] (= expected (decimal-shift-right x n))
    123400000 5 1234
    1234000   5 12
    123       5 0))

(deftest modulo-10-power-should-be-correct
  (are [x n expected] (= expected (modulo-10-power x n))
       123456789  1          9
       123456789  2         89
       123456789  8   23456789
       123456789 99  123456789
       ))

