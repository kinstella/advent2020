(ns advent.day6
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def data (slurp "resources/day6/input.txt"))
(def bags (str/split data #"\n"))

(defn get-item-counts [item]
  (let [[itemcount & theitem] (str/split item #" " 2)]
    {:qty itemcount :bagtype (first theitem)}))

(defn codify-rules [bagdesc]
  (let [bagdesc (str/replace bagdesc #"[.]|bags|bag" "")
        [thebag & [containables]] (map str/trim (str/split bagdesc #"contain"))
        thebag (str/trim thebag)
        items (str/split containables #", ")
        items (map str/trim items)
        containrules (map get-item-counts items)]
    {:bag thebag :contents containrules}))

(defn can-contain-bag? [bagcontents bagstr]
  (some (fn [bagcontent] (= bagstr (:bagtype bagcontent))) bagcontents))

(defn find-bags-in-rules [cargorules bagstr]
  (filter (fn [rule] (can-contain-bag? (:contents rule) bagstr))  cargorules))

(defn find-bags-in-bags [rules bagtypes]
  (loop [bagstofind (conj [] bagtypes)
         containerset #{}]
    (println "bags to find:" bagstofind)
    (if (= 0 (count bagstofind))
      {:count (count containerset) :bags containerset}
      (do
        (recur (concat (rest bagstofind)
                       (map :bag (find-bags-in-rules rules (first bagstofind))))
               (into containerset (map :bag  (find-bags-in-rules rules (first bagstofind)))))))))

;; do the things
(def cargorules (map codify-rules bags))
(find-bags-in-bags cargorules "shiny gold")
