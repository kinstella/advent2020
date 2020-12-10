(ns advent.day10
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def data (slurp "resources/day10/input.txt"))
(def adapters (map #(Long/parseLong %) (str/split data #"\n")))

(sort adapters)

(def diff1 (atom 0))
(def diff3 (atom 0))

(defn count-intervals [dpts]
  (reset! diff1 0)
  (reset! diff3 0)
  (loop [remadapters (sort dpts)
         prevnum 0]
    (if (> 0 (count remadapters))
      (do (println (count remadapters) "prevnum " prevnum "next " (first remadapters))
          (let [thediff (- (first remadapters) prevnum)]
            (cond (= thediff 1)
                  (swap! diff1 inc)
                  (= thediff 3)
                  (swap! diff3 inc)
                  (= thediff 2)
                  (println "whatever")
                  :else
                  (println "fail!" thediff)))
          (recur (rest remadapters) (first remadapters)))
      {:diff1 @diff1 :diff3 @diff3})))

(count-intervals adapters)
@diff1
@diff3
