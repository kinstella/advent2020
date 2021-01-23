(ns advent.day14
  (:require
   [clojure.java.io :as io]
   [clojure.string :refer [replace join split-lines split includes?]]))

(def data (slurp (io/resource "day14/input.txt")))
(def lines (split-lines data))

(defn mask-from-line [maskline]
  (split maskline #"= "))

(defn binary-from-memline [memline]
  (let [[addrpart binarypart] (split memline #"] = ")]
    {:addr
     (->  addrpart
          (clojure.string/replace "mem[" "")
          (Integer/parseInt))
     :data
     (-> binarypart
         (Integer/parseInt)
         (Integer/toBinaryString))}))

(defn string-with-bitmask
  "apply the new value into the current bit string while respecting the mask"
  [curstr bitmask value])

(defn load-data [lines]
  (loop [remlines lines
         curval (join (repeat 36 "0"))
         curmask (join (repeat 36 "X"))]
    (if (empty? remlines)
      curval
      (let [x (first remlines)]
        (if (includes? x "mask")
          (do
            (recur (rest remlines) curval (mask-from-line x)))
          (do
            (let [bin-str (binary-from-memline x)]
              (recur (rest remlines) curval x))))))))
