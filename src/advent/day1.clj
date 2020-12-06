(ns advent.day1)

(def data (slurp "resources/day1/input.data"))
(def ints (map #(Integer/parseInt %) (clojure.string/split data #"\n")))

(defn find-two [ints op]
  (let [diffromnum (- 2020 op)
        resvec (filter (fn [d] (some #(= (- diffromnum d) %) ints)) ints)]

    (when (> (count resvec) 0)
      (println resvec)
      (* (first resvec) (last resvec)))))


(map (fn [x] (find-two ints x)) ints)

(* 503 550 967)