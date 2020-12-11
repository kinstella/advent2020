(ns advent.day11
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def data (slurp "resources/day11/input.txt"))
(def ferryrows (str/split data #"\n"))

(println ferryrows)

ferryrows

(for [x (range (count ferryrows))]
  (for [y (range (count (nth ferryrows x)))]
    (println (nth (nth ferryrows x) y))))