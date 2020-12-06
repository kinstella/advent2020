(ns advent.day2)

(def data (slurp "resources/day2/input.txt"))
(def lines (clojure.string/split data #"\n"))

(defn lines-to-spec [lines]
  (map (fn [l] (zipmap [:min :max :letter :nada :pw] (clojure.string/split (clojure.string/replace l #"[:-]" " ") #" "))) lines))

(defn isvalidpw? [pwrule]
  (let [min (Integer/parseInt (:min pwrule))
        max (Integer/parseInt (:max pwrule))
        theletter (first (:letter pwrule))
        thefreq (get (frequencies (:pw pwrule)) theletter)]
    ;(println "min is" min "max is" max " letter is " theletter " and freq is " thefreq " in pw" (:pw pwrule))
    (if (and (not (nil? thefreq))
             (>= thefreq min)
             (>= max thefreq))
      true
      false)))

(defn isvalidpos? [pwrule]
  (let [pos1 (Integer/parseInt (:min pwrule))
        pos2 (Integer/parseInt (:max pwrule))
        thepw (:pw pwrule)
        theletter (first (:letter pwrule))]
    ;(println "min is" min "max is" max " letter is " theletter " and freq is " thefreq " in pw" (:pw pwrule))
    (or (and (= theletter (nth thepw (- pos1 1)))
             (not (= theletter (nth thepw (- pos2 1)))))
        (and (not (= theletter (nth thepw (- pos1 1))))
             (= theletter (nth thepw (- pos2 1)))))))

(def thelines (lines-to-spec lines))
(count (filter isvalidpos? thelines))


