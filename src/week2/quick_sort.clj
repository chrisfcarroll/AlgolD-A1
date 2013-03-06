(ns week2.quick-sort
  (require [contrib :refer :all]))

(def pq-input-list (vec (map #(Integer/parseInt %) (.split (slurp "src/week2/QuickSort.txt") "\r\n"))))

(def count-comparisons (atom 0))
(def default-pivot-choice (atom :median-of-3))
(def verbose (atom false))

(defn median-index-of-3 [s from-index to-index]
  (let [mid-index (quot (+ from-index to-index) 2)
        l (nth s from-index)
        m (nth s mid-index)
        r (nth s to-index)
        l-m (< l m)
        m-r (< m r)
        r-l (< r l)
        m-l (not l-m)
        l-r (not r-l)
        r-m (not m-r)]
      (cond 
        (and l-m m-r) mid-index
        (and m-r r-l) to-index
        (and r-l l-m) from-index
        (and m-l l-r) from-index
        (and l-r r-m) to-index
        (and r-m m-l) mid-index
        )))

(defn choose-pivot-index
  "returns the index of a single element of tsequence lying between from-index and to-index 
  inclusive. Valid values for how
   :first        - Always returns from-index
   :last         - Always returns from-index
   :median-of-3  - Returns the index of whichever is the median of the elements at 
                   from-index, to-index and the index mid-way (rounded down) between them."
  [how tsequence from-index to-index] 
  (case how
    :first       from-index
    :last        to-index
    :median-of-3 (median-index-of-3 tsequence from-index to-index)
    (throw (IllegalArgumentException. "Valid values [:first :last :median-of-3]"))))

(defn <-at [s x y] (< (nth s x) (nth s y)) )

(defn swap-at! [tseq i j] "Swaps the ith and jth elements of a transient collection"
  (let [z (nth tseq i )]
    (assoc! tseq i (nth tseq j))
    (assoc! tseq j z)))

(defn qs-partition-about-and-get-new-pivot-index! 
  "rearrange elements of tseq so that all elements less than the element at pivot-index are moved 
  to the left of it; and all bigger elements are moved to the right.
  returns the new index position of the pivot element."

  ([tseq pivot-index from-index to-index]

    (if (not= pivot-index from-index)
          (swap-at! tseq from-index pivot-index))

    (let [ii                     (atom from-index)
          rightmost-small-index  (atom from-index)]
      (doseq [i (range (inc from-index) (inc to-index))]
          (if @verbose (println-transient tseq))
          (if (<-at tseq i from-index) 
            (do 
              (swap! rightmost-small-index inc)
              (swap-at! tseq i @rightmost-small-index)
            )))

      (swap-at! tseq from-index @rightmost-small-index)

      @rightmost-small-index))

  ([tseq pivot-index] (qs-partition-about-and-get-new-pivot-index! tseq pivot-index 0 (dec (count tseq)))))

(defn quick-sort-t! [ tsequence pivot-choice from-index to-index ]
  (if (<= to-index from-index)
    tsequence
    (let [pivot-index     (choose-pivot-index pivot-choice tsequence from-index to-index)
          pivot-value-at-time-chosen (nth tsequence pivot-index)
          new-pivot-index (qs-partition-about-and-get-new-pivot-index! 
                                  tsequence pivot-index from-index to-index)]
          (swap! count-comparisons #(+ % (- to-index from-index)))
          (if @verbose (do  (println "Chose pivot [" pivot-index "] is " pivot-value-at-time-chosen)
                            (println "(" from-index "-" to-index ") : " (- to-index from-index))))
          (quick-sort-t! tsequence pivot-choice from-index (dec new-pivot-index))
          (quick-sort-t! tsequence pivot-choice (inc new-pivot-index) to-index)
          tsequence)))

(defn quick-sort 
  ([sequence pivot-choice] 
        (persistent! (quick-sort-t! (transient sequence) pivot-choice 0 (dec (count sequence)) ))
  )
  ([sequence] (quick-sort sequence @default-pivot-choice)))

(defn quick-sort-with-comparison-count 
  ([sequence pivot-choice]
    (reset! count-comparisons 0)
    {:result (quick-sort sequence pivot-choice) :count @count-comparisons}
  )
  ([sequence] (quick-sort-with-comparison-count sequence @default-pivot-choice)))

(comment "Question 1
GENERAL DIRECTIONS:
Download the text file here. 

The file contains all of the integers between 1 and 10,000 (inclusive, with no repeats) in unsorted order. The integer in the ith row of the file gives you the ith entry of an input array.

Your task is to compute the total number of comparisons used to sort the given input file by QuickSort. As you know, the number of comparisons depends on which elements are chosen as pivots, so we'll ask you to explore three different pivoting rules.
You should not count comparisons one-by-one. Rather, when there is a recursive call on a subarray of length m, you should simply add m−1 to your running total of comparisons. (This is because the pivot element is compared to each of the other m−1 elements in the subarray in this recursive call.)

WARNING: The Partition subroutine can be implemented in several different ways, and different implementations can give you differing numbers of comparisons. For this problem, you should implement the Partition subroutine exactly as it is described in the video lectures (otherwise you might get the wrong answer).

DIRECTIONS FOR THIS PROBLEM:

For the first part of the programming assignment, you should always use the first element of the array as the pivot element.

HOW TO GIVE US YOUR ANSWER:

Type the numeric answer in the space provided.
So if your answer is 1198233847, then just type 1198233847 in the space provided without any space / commas / other punctuation marks. You have 5 attempts to get the correct answer.
(We do not require you to submit your code, so feel free to use the programming language of your choice, just type the numeric answer in the following space.)")
