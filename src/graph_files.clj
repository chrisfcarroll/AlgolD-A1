(ns graph-files
  (:require 
    [clojure.string :refer [split-lines split trimr]]
    [multiset.core :refer [multiset? multiset] :as ms]))

(defn unweighted-graph-from-tab-delimited-lines [string]
  (for [line (split-lines string)
              :when (not (.startsWith line "#"))] 
    (let [[nstring edgesstring] (split line #"\s" 2) 
          node (Integer/parseInt nstring)
          edges (apply multiset (map #(Integer/parseInt %) (split edgesstring #"\s")))]
        [node edges])))

(defn unweighted-graph-from-file [filepath] 
  (into (sorted-map) (unweighted-graph-from-tab-delimited-lines(slurp filepath))))


(defn parseInts [seq] (map #(Integer/parseInt %) seq))

(defn weighted-graph-without-parallels-from-tab-comma-delimited-lines [string]
  (for [line (split-lines string)
              :when (not (.startsWith line "#"))] 
    (let [[nstring edgesstring] (split line #"\s" 2) 
          node (Integer/parseInt nstring)
          edge-weights (apply assoc {} (map #(Integer/parseInt %) (split edgesstring #"(\s|,)")))]
        [node edge-weights])))

(defn weighted-graph-without-parallels-from-file [filepath] 
  (into (sorted-map) (weighted-graph-without-parallels-from-tab-comma-delimited-lines(slurp filepath))))
