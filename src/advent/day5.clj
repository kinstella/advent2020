(ns advent.day5
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def data (slurp "resources/day5/input.txt"))
(def passes (str/split data #"\n"))

(def rows (vec (range 0 128)))
(def seatcols (vec (range 0 8)))

(defn get-half [s rows]
  (if (or (= s \F) (= s \L))
    (subvec rows 0  (/ (count rows) 2))
    (subvec rows (/ (count rows) 2))))

;; ;; todo == do this with reduce?
;; (defn get-row [rowstr rows]
;;   (loop [remrows rows
;;          remstr rowstr]
;;     (if (<= (count remrows) 2)
;;       (get-half (first remstr) remrows)
;;       (recur (get-half (first remstr) remrows)
;;              (rest remstr)))))

(defn traverse [str dataset]
  (loop [remset dataset
         remstr str]
    (if (<= (count remset) 2)
      (get-half (first remstr) remset)
      (recur (get-half (first remstr) remset)
             (rest remstr)))))

(defn findseat [boardingpass]
  (let [row (first (traverse (subs boardingpass 0 7) rows))
        seatcol (first (traverse (subs boardingpass 7) seatcols))
        seatid (+ (* 8 row) seatcol)]
    {:row row :seatcol seatcol :seatid seatid}))

(def occupied (set (map (comp :seatid findseat) passes)))

(set/difference (set (range 73 827)) occupied)
