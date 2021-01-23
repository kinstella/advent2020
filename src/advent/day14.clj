(ns advent.day14
  (:require
   [clojure.java.io :as io]
   [clojure.string :refer [replace join split-lines split includes?]]))

(def data (slurp (io/resource "day14/input.txt")))
(def lines (split-lines data))

(defn mask-from-line [maskline]
  (last (split maskline #"= ")))

(defn data-from-memline [memline]
  (let [[addrpart binarypart] (split memline #"] = ")]
    {:addr
     (->  addrpart
          (clojure.string/replace "mem[" "")
          (Integer/parseInt))
     :data
     (-> binarypart
         (Integer/parseInt)
         (Integer/toBinaryString))}))

(defn lpadx [bin-str len]
  (str (join  (repeat (- len (count bin-str)) "0")) bin-str))

(defn binstr->bin [binstr]
  (Long/parseUnsignedLong binstr 2))

(defn sum-memlocs [memvals]
  (reduce +  (map binstr->bin (vals memvals))))

(defn newval-after-bitmask
  "apply the new value into the current addr bit string while respecting the mask"
  [bitmask value]
  ;(println "-------------------------")
  (let [paddedval (lpadx value 36)]
    (join  (map (fn [mask newval]
                  (if

                   (not= mask \X)
                    mask

                    newval))
                bitmask paddedval))))

(defn load-data [lines]
  (loop [remlines lines
         memvals {}
         curmask (join (repeat 36 "X"))]
    (if (empty? remlines)
      memvals
      (let [x (first remlines)]
        (if (includes? x "mask")
          (do
            (recur (rest remlines)
                   memvals
                   (mask-from-line x)))
          (do
            (let [bin-str (data-from-memline x)
                  curval (or (get memvals (:addr bin-str)) (repeat 36 "0"))
                  newmemval (newval-after-bitmask curmask (:data bin-str))]
              ;(println "newmemval" newmemval)
              (recur (rest remlines)
                     (assoc memvals (:addr bin-str) newmemval)
                     curmask))))))))

(comment
  (def memvals (load-data lines))
  (sum-memlocs memvals)
  ;; => 10050490168421
  )
