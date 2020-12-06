(ns advent.day4
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def data (slurp "resources/day4/passports.txt"))
(def passports (str/split data #"\n\n"))

(def required #{"byr"
                "iyr"
                "eyr"
                "hgt"
                "hcl"
                "ecl"
                "pid"})

(defn get-fields [pp]
   (into {} (map #(str/split  % #":") 
                 (str/split pp #"\s+"))))
  
(defn is-valid-passport? [pp]
  (let [ppfields (keys (get-fields pp))]
    (empty? (clojure.set/difference
             required
             (set ppfields)))))

(count passports)
(count (filter is-valid-passport? passports))
