(ns week2.pq2-quick-sort)

(defn calculate-qs-comparisons-with-1st-element-as-pivot [sequence] ())

(defn choose-pivot-index
  "returns the index of a single element of tsequence lying between from-index and to-index inclusive.
  Valid values for how
   :first    - Always returns from-index"
  [how tsequence from-index to-index] 
  (case how
    :first from-index
    (throw (IllegalArgumentException. ":first is the only implementation."))))

(defn <-at [s x y] (< (nth s x) (nth s y)) )

(defn swap-at! [tseq i j]
  (let [z (nth tseq i )]
    (assoc! tseq i (nth tseq j))
    (assoc! tseq j z)))

(defn qs-partition-about-and-get-new-pivot-index! 
  "rearrange elements of tseq so that all elements less than the element at pivot-index are moved 
  to the left of it, and all bigger elements are moved to the right.
  returns the new index of the pivot element."

  ([tseq pivot-index from-index to-index]
    (print "in :[")
      (doseq [i (range 0 (count tseq))]
        (print (nth tseq i) " "))
    (println "], (left, pivot, right :" from-index "," pivot-index "," to-index ")" )

    (if (not= pivot-index from-index) (throw (IllegalArgumentException. "Can't yet deal with the case when pivot is not at start of sub sequence." ))) 
        ; put the pivot at the beginning so we can always work simplistically left to right

    (let [ii                     (atom from-index)
          rightmost-small-index  (atom from-index)]
      (doseq [i (range (inc from-index) (inc to-index))]
          (if (<-at tseq i pivot-index) 
            (do 
              (swap! rightmost-small-index inc)
              (swap-at! tseq i @rightmost-small-index)
            )))
      (print "out :[")
        (doseq [i (range 0 (count tseq))]
          (print (nth tseq i) " "))
      (println "], rightmost-small-index: " @rightmost-small-index)

      (swap-at! tseq pivot-index @rightmost-small-index)

      (print "out :[")
        (doseq [i (range 0 (count tseq))]
          (print (nth tseq i) " "))
      (println "], rightmost-small-index: " @rightmost-small-index)

      @rightmost-small-index))

  ([tseq pivot-index] (qs-partition-about-and-get-new-pivot-index! tseq pivot-index 0 (dec (count tseq)))))



(defn quick-sort-t! [ tsequence from-index to-index ]
  (if (= from-index to-index)
    tsequence
    (let [pivot-index (choose-pivot-index :first tsequence from-index to-index)]
        (let [new-pivot-index  (qs-partition-about-and-get-new-pivot-index! tsequence pivot-index from-index to-index)
              lft-from from-index
              lft-to (dec new-pivot-index)
              rt-from (inc new-pivot-index)
              rt-to to-index]
          (quick-sort-t! tsequence lft-from lft-to)
          (quick-sort-t! tsequence rt-from  rt-to )
          tsequence))))

(defn quick-sort [sequence] 
  (persistent! (quick-sort-t! (transient sequence) 0 (dec (count sequence)) )))

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
