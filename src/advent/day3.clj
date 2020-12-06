(ns advent.day3
  (:require [clojure.string :as str]))

(def data (slurp "resources/day3/input.txt"))
(def lines (str/split data #"\n"))

(defn tobagganing [rslope dslope lines]
  (count (filter #(= % \#)
                 (loop [x 0
                        y 0
                        encounters []]
                   (if (> y (- (count lines) 1))
                     encounters
                     (recur
                      (+ rslope x)
                      (+ dslope y)
                      (conj encounters (nth (nth lines y) (mod x 31)) )) )))))

(* 
 (tobagganing 1 1 lines)
;; => 65
 
 (tobagganing 3 1 lines)
;; => 237
 
 (tobagganing 5 1 lines)
;; => 59
 
 (tobagganing 7 1 lines)
;; => 61
 
 (tobagganing 1 2 lines))
;; => 38



