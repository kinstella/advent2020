(ns advent.day9
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def data (slurp "resources/day9/input.txt"))
(def allnums (map #(Long/parseLong %) (str/split data #"\n")))
;(def allnums (take 1000 allnums))

(defn find-addends [numset thesum]
  (for [x (range (count numset))]
    (let [curaddend (nth numset x)
          foundaddend (first (filter #(= thesum (+ % curaddend)) numset))]
      (when (not (nil? foundaddend))
        [curaddend foundaddend]
        ))))

(defn is-next-valid [numset nextnum]
  (let [candnums (filter #(> nextnum %) numset)
        addends (remove nil? (find-addends candnums nextnum))]
    (if (> 0 (count addends))
      (do (println addends)
          true)
      false)))

(defn find-invalid-nums [allnums]
  (loop [startnum 0
         endnum 25]
    (if (false? (is-next-valid (subvec (vec allnums) startnum endnum)
                               (nth allnums endnum)))
      (nth allnums endnum)
      (recur (inc startnum) (inc endnum)))))

;(find-invalid-nums allnums)

;;;;
;;;;
;;;; part 2
;;;; 

(defn find-contig-addends [allnums numtofind]
  (for [startnum (range (count allnums))]
    (do (println "starting at: " startnum)
        (loop [endnum startnum]
          ;(println "looking at this set:" (subvec (vec allnums) startnum endnum))
          ;(println "startnum" startnum "endnum: " endnum)
          (let [thesumresult (apply + (subvec allnums startnum endnum))]
            ;(println "the sum result: " thesumresult)
            (cond (> thesumresult numtofind)
                  (println "sum is too high" thesumresult)

                  (> endnum (count allnums)) ;(apply + (subvec (vec allnums) startnum endnum)))
                  (println "reached end of numset" endnum)
                  
                  (= thesumresult numtofind)
                  (do (println "found the number!" thesumresult "started with " startnum "ended with " endnum )
                      (println "startnum is" (nth allnums startnum) "endnum is" (nth allnums endnum))
                      (println "vec is: "  (sort (subvec allnums startnum endnum))))
                  
                  :else
                    (recur (inc endnum))))))))


(def numtofind 26134589)

(find-contig-addends (vec allnums) numtofind)
