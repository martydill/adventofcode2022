(ns adventofcode2022.day8
  (:require [clojure.string :as str] [clojure.math.combinatorics :as combo] [clojure.core.matrix]))

(def input (str/split (slurp "day8.input") #"\n"))
(def _m (map seq input))
(def m (map (fn [x] (map #(Integer/parseInt (str %)) x)) _m))

(defn is-visible-in-row [x row]
  (or
   (empty?
    (filter (fn [val] (>= val (nth row x))) (subvec row 0 x)))
   (empty?
    (filter (fn [val] (>= val (nth row x))) (subvec row (inc x))))))

(defn is-biggest-in-row-or-column [x y m]
  (let [ row (vec (nth m y))
        col (vec (nth (clojure.core.matrix/transpose m) x))]
    (or (is-visible-in-row x row) (is-visible-in-row y col))))

(def coordinates-to-try (combo/cartesian-product (range (count (first m))) (range (count (first m)))))
(count coordinates-to-try)
(count (filter (fn [z] z) (map (fn [x]
                                 (is-biggest-in-row-or-column (first x) (last x) m)) coordinates-to-try)))

; part 2

(defn take-up-to-first-match [val row]
  (cond
    (empty? row) 0
    (< (first row) val) (+ 1 (take-up-to-first-match val (rest row)))
    (= (first row) val) 1
    :else 1))

(defn get-row-score [x row]
  (let [val (nth row x)
        vals-to-the-left  (subvec row 0 x)
        vals-to-the-right  (subvec row (inc x))]
    (*
     (take-up-to-first-match val (reverse vals-to-the-left))
     (take-up-to-first-match val vals-to-the-right))))

(defn get-scenic-score [x y m]
  (let [row (vec (nth m y))
        col (vec (nth (clojure.core.matrix/transpose m) x))]
    (* (get-row-score x row) (get-row-score y col))))

(def all-scenic-scores (map (fn [x] (get-scenic-score (first x) (last x) m)) coordinates-to-try))
(reduce max all-scenic-scores)