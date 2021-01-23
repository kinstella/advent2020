(ns advent.day13
  (:require [clojure.string :refer [split-lines split]]))

(def data (slurp "resources/day13/input.txt"))
(def lines (split-lines data))

(defn calc-next-arrival [busnum ts]
  (let [bus (Integer/parseInt busnum)]
    (+ (* bus (quot ts bus)) bus)))

(defn read-bus-data [data]
  (let [timestamp (first data)
        bus-vec (filter #(not= % "x") (split (last data) #","))
        next-arrival (map #(calc-next-arrival % timestamp) bus-vec)]
    (zipmap bus-vec next-arrival)))

(read-bus-data lines)