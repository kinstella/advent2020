(ns advent.day4
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def data (slurp "resources/day4/passports.txt"))
(def passports (str/split data #"\n\n"))

(def required #{"byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"})

(defn get-fields [pp]
  (into {} (map #(str/split  % #":")
                (str/split pp #"\s+"))))

(defn is-valid-passport? [pp]
  (let [ppfields (keys (get-fields pp))]
    (empty? (clojure.set/difference
             required
             (set ppfields)))))

(defn valid-byr? [val]
  "Birth Year - must have four digits; at least 1920 and at most 2002."
  (let [yearval (Integer/parseInt val)]
    (and (= 4 (count val))
         (>= yearval 1920)
         (<= yearval 2002))))

(defn valid-iyr? [val]
  "Issue Year - four digits; at least 2010 and at most 2020."
   (let [yearval (Integer/parseInt val)]
    (and (= 4 (count val))
         (>= yearval 2010)
         (<= yearval 2020))))

(defn valid-eyr? [val]
  "Expiration Year - four digits; at least 2020 and at most 2030."
  (let [yearval (Integer/parseInt val)]
    (and (= 4 (count val))
         (>= yearval 2020)
         (<= yearval 2030))))

(defn valid-cm? [v]
  (if (not (nil? (re-matches (re-pattern "^[0-9]{3}cm$") v)))
    (let [cmval (Integer/parseInt (first (str/split v #"cm")))]
      (and (>= cmval 150) (<= cmval 193)))))

(defn valid-in? [v]
  (if (not (nil? (re-matches (re-pattern "^[0-9]{2}in$") v)))
    (let [cmval (Integer/parseInt (first (str/split v #"in")))]
      (and (>= cmval 59) (<= cmval 76)))))

(defn valid-hgt? [val]
  "Height - a number followed by either cm or in:
    If cm, the number must be at least 150 and at most 193.
    If in, the number must be at least 59 and at most 76."
  (if (or (valid-cm? val) (valid-in? val))
    true
    false))

(defn valid-hcl? [val]
  "Hair Color - a # followed by exactly six characters 0-9 or a-f."
  (if (not (nil? (re-matches (re-pattern "^#[0-9a-z]{6}$") val)))
    true
    false))

(defn valid-ecl? [val]
  "Eye Color - exactly one of: amb blu brn gry grn hzl oth."
  (let [valid-colors #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"}]
    (contains? valid-colors val)))

(defn valid-pid? [val]
  "Passport ID - a nine-digit number, including leading zeroes."
   (and 
    (= 9 (count val))
    (every? #(Character/isDigit %) val)))

(defn is-fully-valid-passport? [pp]
  (let [ppfields (get-fields pp)]
    (and (empty? (clojure.set/difference
                  required
                  (set (keys ppfields))))
         (valid-byr? (get ppfields "byr"))
         (valid-iyr? (get ppfields "iyr"))
         (valid-eyr? (get ppfields "eyr"))
         (valid-hgt? (get ppfields "hgt"))
         (valid-hcl? (get ppfields "hcl"))
         (valid-ecl? (get ppfields "ecl"))
         (valid-pid? (get ppfields "pid")))))

(count (filter is-fully-valid-passport? passports))

