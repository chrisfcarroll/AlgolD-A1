(ns week3.indexing
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

(defn flatten-1tomany-map-into-kv-pairs [onetomanymap]
  (reduce (fn [acc [key vals]] (apply conj acc (for [val vals] [key val])) ) () onetomanymap))

(defn merge-kv-pair-into-1tomany-map [acc [key newval]]
  (let [old-v-list (get acc key)
        new-v-list (conj old-v-list newval)]
    (assoc acc key new-v-list)))

(defn pivot-merge-kv-pair-into-1tomany-map [acc [newval key]]
  (let [old-v-list (get acc key)
        new-v-list (conj old-v-list newval)]
    (assoc acc key new-v-list)))
