(ns advent.day6
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def data (slurp "resources/day8/revised.txt"))
(def instructions (str/split data #"\n"))
(def accumulator (atom 0))
(def executed (atom #{}))


(defn inst-to-map [instrs]
  (for [i (range (count instrs))]
    (let [[opcode opval] (str/split (nth instrs i) #" ")]
      {:line i :opcode opcode :opval (Integer/parseInt opval)})))

(def instmap (inst-to-map instructions))

(defn jmp
  "returns the statement jumped to by jmp"
  [curline instmap val]
  (nth instmap (+ curline val)))

(defn acc
  "increases the acc, then gets the next statement"
  [curline instmap val]
  (reset! accumulator (+ @accumulator val))
  (nth instmap (inc curline)))

(defn processop [curop instmap]
  (cond (= (:opcode curop) "jmp")
        (jmp (:line curop) instmap (:opval curop))

        (= (:opcode curop) "acc")
        (acc (:line curop) instmap (:opval curop))

        (= (:opcode curop) "nop")
        (jmp (:line curop) instmap 1)))

(defn interpreterloop [initop instmap]
  (reset! accumulator 0)
  (reset! executed #{})
  (loop [optoexec initop]
    (if (contains? @executed (:line optoexec))
      @accumulator
      (do
        (let [nextop (processop optoexec instmap)]
          (println "Next op is:" nextop)
          (swap! executed conj (:line optoexec))
          (recur nextop))))))

(interpreterloop (first instmap) instmap)
