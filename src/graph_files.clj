(ns graph-files
  (:require 
    [clojure.string :refer [split-lines split trimr]]
    [multiset.core :refer [multiset? multiset] :as ms]))

(defn mapentries-from-tab-delimited-lines [string]
  (for [line (split-lines string)
              :when (not (.startsWith line "#"))] 
    (let [[nstring edgesstring] (split line #"\s" 2) 
          node (Integer/parseInt nstring)
          edges (apply multiset (map #(Integer/parseInt %) (split edgesstring #"\s")))]
        [node edges])))