(ns advent.day1
  (:require [clojure.string :as str]))

(def data (slurp "resources/day1/input.data"))
(def theints (map #(Integer/parseInt %) (str/split data #"\n")))

(defn find-two [theints op]
  (let [diffromnum (- 2020 op)
        resvec (filter (fn [d] (some #(= (- diffromnum d) %) theints)) theints)]
    (when (> (count resvec) 0)
      (println resvec)
      (* (first resvec) (last resvec)))))


(map (fn [x] (find-two theints x)) theints)

(* 503 550 967)