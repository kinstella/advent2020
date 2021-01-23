(ns advent.day12
  (:require [clojure.string :refer [split-lines join]]
            [clojure.tools.logging :as lg]))

(def loc (atom {:heading 90
                :n 0
                :e 0}))

(def dirs {0 :north
           90 :east
           180 :south
           270 :west})

(defn north [amt]
  (log/info "North by " amt)
  (swap! loc assoc :n (+ (:n @loc) amt)))
(defn south [amt]
  (log/info "South by " amt)
  (swap! loc assoc :n (- (:n @loc) amt)))
(defn east [amt]
  (log/info "East by " amt)
  (swap! loc assoc :e (+ (:e @loc) amt)))
(defn west [amt]
  (log/info "West by " amt)
  (swap! loc assoc :e (- (:e @loc) amt)))
(defn turn-right [deg]
  (log/info "Right by " deg)
  (swap! loc assoc :heading (mod (+ (:heading @loc) deg) 360)))
(defn turn-left [deg]
  (log/info "Left by " deg)
  (swap! loc assoc :heading (mod (- (:heading @loc) deg) 360)))
(defn forward [amt]
  (let [curheading (get dirs (:heading @loc))]
    (log/info "heading " (:heading @loc) " so " curheading " by " amt)
    (case curheading
      :north (north amt)
      :south (south amt)
      :east (east amt)
      :west (west amt))))

(defn parseline [inst]
  (let [command (str (first inst))
        amt (Integer/parseInt (join (rest inst)))]
    (swap! ct inc)
    (log/info "Instruction Number: " @ct)
    (case command
      "N" (north amt)
      "S" (south amt)
      "E" (east amt)
      "W" (west amt)
      "R" (turn-right amt)
      "L" (turn-left amt)
      "F" (forward amt)
      (log/error "unknown command:" command))
    (log/info inst @loc)))

(defn set-sail [instrs]
  (reset! ct 0)
  (reset! loc {:heading 90 :n 0 :e 0})
  (log/error (count instrs))
  (doseq [x instrs]
    (parseline x)))

(defn manhattan []
  (+ (Math/abs (:n @loc)) (Math/abs (:e @loc))))

(comment
  (def data (slurp "resources/day12/input.txt"))
  (def lines (split-lines data))
  (count lines)
  (set-sail lines)

  (doseq [x lines]
    (parseline x))
  @loc
  (manhattan)

  #_endcomm)