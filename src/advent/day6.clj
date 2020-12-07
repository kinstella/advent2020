(ns advent.day6
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def data (slurp "resources/day6/input.txt"))
(def bags (str/split data #"\n"))

(defn get-item-counts [item]
  (let [[itemcount & theitem] (str/split item #" " 2)]
    {:qty itemcount :bagtype (first theitem)}))

(defn codify-rules [bagdesc]
  (let [bagdesc (str/replace bagdesc #"[.]" "")
        [thebag & [containables]] (map str/trim (str/split bagdesc #"contain"))
        items (str/split containables #", ")
        containrules (map get-item-counts items)]
    {:bag thebag :contents containrules}))

(def cargorules (map codify-rules bags))
(count cargorules)

(defn can-contain-bag? [bagcontents bagstr]
  (some (fn [bagcontent] (= bagstr (:bagtype bagcontent))) bagcontents))

(defn find-bags-in-rules [cargorules bagstr]
  (filter (fn [rule] (can-contain-bag? (:contents rule) bagstr))  cargorules))

(defn find-bags-in-bags [rules bagtypes]
  (loop [bagstofind (conj [] bagtypes)
         containerset #{}
         ]
    (println "bags to find:" bagstofind)
    (if (= 0 (count bagstofind))
      {:count (count containerset) :bags containerset}
      (do
        (recur (concat (rest bagstofind)
                       (map :bag (find-bags-in-rules rules (first bagstofind))))
               (into containerset (map :bag  (find-bags-in-rules rules (first bagstofind)))))))))

(find-bags-in-bags cargorules "shiny gold bags")
;(map :bag (find-bags-in-rules cargorules "shiny gold bags"))
;(map :bag (take 10 cargorules ))

;(take 2 cargorules)
;
;
(find-bags-in-rules cargorules "shiny gold bags")