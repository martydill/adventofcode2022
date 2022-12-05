(ns adventofcode2022.day3
  (:require [clojure.string :as str]))

(def input (str/split (slurp "day3.input") #"\n"))

(defn parts [row]
  [(subs row 0 (/ (count row) 2))
   (subs row (/ (count row) 2))])

(defn common [row] (clojure.set/intersection (set (first row)) (set (last row))))

(defn priority [i]
  (let [c (first i)]
    (if (Character/isUpperCase c)
      (+ (- (int c) (int \A)) 27)
      (+ (- (int c) (int \a)) 1))))

(def input-parts (map parts input))

(def common-parts (map common input-parts))

(def priorities (map priority common-parts))

(reduce + priorities)

; Part 2

(def groups (partition 3 input))
(defn common-in-group [group] (clojure.set/intersection (set (first group)) (set (nth group 1)) (set (last group))))
(def common-badges (map common-in-group groups))

(def badge-priorities (map priority common-badges))
(reduce + badge-priorities)
