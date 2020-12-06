(ns advent.day6
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def data (slurp "resources/day6/input.txt"))
data

(def groups (str/split data #"\n\n"))
groups

(def answers (map #(str/split % #"\n") groups))
answers


(def allsets (map #(-> (apply str %) set) answers))
(def thesums (map count allsets))
(reduce + thesums)

;; part two
;;  -- where a question exists per line
;; 
;; 

(defn xsect-each-group [groupanswers]
  (apply set/intersection (map set groupanswers)))

(reduce + (map #(-> % xsect-each-group
                    count) answers))


