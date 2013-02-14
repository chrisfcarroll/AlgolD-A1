(ns testing
  (:require [clojure.test]))

(def ^:dynamic *show-manual-test-commentary* false)

(defn not-nil? [expr] (not (nil? expr)))

(defn is-in-vector? 
  [element vector-to-search]
  (= element (some #{element} vector-to-search)))

(defn manual-test [testname & more]
  (if *show-manual-test-commentary* 
    (println "Manual Test:\n        " testname "\n        " more)))

(defn some? 
  "returns true iff (some pred coll) returns a non-nill result"
  [pred coll] (not-nil? (some pred coll)))

(defn in-list? [element sequence] (= element (some #{element} sequence)))

(defmacro lett 
  "expands to (let bindings exprs true) so that the return value is true not nil. 
  Useful for using let within (are ... ) tests."
  [bindings & exprs]  `(let ~bindings ~@exprs true))

(defmacro aare 
  "Expands to (are argv expr & <expanded list-of-args>"
  [argv expr args] ;`(clojure.test/are ~argv ~expr ~@listofargs) 
     ;`(clojure.test/are ~argv ~expr ~@(eval args)))
    (concat `(clojure.test/are ~argv ~expr ) (eval args)))

(comment defmacro are
  [argv expr & args]
  (if (or
       (and (empty? argv) (empty? args))
       ;; Catch wrong number of args
       (and (pos? (count argv))
            (pos? (count args))
            (zero? (mod (count args) (count argv)))))
    `(temp/do-template ~argv (is ~expr) ~@args)
    (throw (IllegalArgumentException. "The number of args doesn't match are's argv."))))
