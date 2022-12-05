(ns adventofcode2022.day4
  (:require [clojure.string :as str]))

(def input (str/split (slurp "day4.input") #"\n"))

(defn get-pairs [row] (str/split row #","))

(defn get-upper-and-lower [pair] (str/split pair #"-"))

(defn to-ints [pair]
  [(Integer/parseInt (first pair)) (Integer/parseInt (last pair))])

(defn pair-to-ints [pair]
  (to-ints [(first (get-upper-and-lower pair)) (last (get-upper-and-lower pair))]))

(defn row-to-ints [row]
  (let [pairs (get-pairs row)]
    [(pair-to-ints (first pairs)) (pair-to-ints (last pairs))]))

(defn is-in [val range]
  (and (>= val (first range))
       (<= val (last range))))

(defn pair-is-in [pair range]
  (and (is-in (first pair) range) (is-in (last pair) range)))

(def rows-of-ints (map row-to-ints input))

(def all-results
  (map
   (fn [row] (or (pair-is-in (first row) (last row)) (pair-is-in (last row) (first row))))
   rows-of-ints))

(def filtered-results (filter (fn [x] x) all-results))
(count filtered-results)