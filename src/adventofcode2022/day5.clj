(ns adventofcode2022.day5
  (:require [clojure.string :as str]))

; Cheating and hardcoding this to save time
(def stacks [[\r \s \l \f \q]
             [\n \z \q \g \p \t]
             [\s \m \q \b]
             [\t \g \z \j \h \c \b \q]
             [\p \h \m \b \n \f \s]
             [\p \c \q \n \s \l \v \g]
             [\w \c \f]
             [\q \h \g \z \w \v \p \m]
             [\g \z \d \l \c \n \r]])

(def input (str/split (slurp "day5.input") #"\n"))
(def instructions-input (drop 10 input))

(defn do-split-line [line] (str/split line #" "))

(defn num [split-line] (Integer/parseInt (nth split-line 1)))
(defn src [split-line] (- (Integer/parseInt (nth split-line 3)) 1))
(defn dst [split-line] (- (Integer/parseInt (nth split-line 5)) 1))

(defn modify-stacks [current-stacks count s d]
  (if (= count 0)
    current-stacks
    (let [src-row (nth current-stacks s)
          dest-row (nth current-stacks d)
          new-dest-row (conj dest-row (peek src-row))
          new-src-row (pop src-row)
          new-stacks (assoc (assoc current-stacks s new-src-row) d new-dest-row)]
      (modify-stacks new-stacks (- count 1) s d))))

(defn apply-instructions [current-stacks input]
  (if (empty? input)
    current-stacks
    (let [next-instruction (first input)
          split-line (do-split-line next-instruction)
          count (num split-line)
          s (src split-line)
          d (dst split-line)
          new-stacks (modify-stacks current-stacks count s d)]

      (apply-instructions new-stacks (rest input)))))

(def final-crate-positions (apply-instructions stacks instructions-input))

(map peek final-crate-positions)

; Part 2

(defn modify-stacks2 [current-stacks num s d]
    (let [src-row (nth current-stacks s)
          dest-row (nth current-stacks d)
          new-dest-row (into dest-row (take-last num src-row))
          new-src-row (vec (take (- (count src-row) num) src-row))]
      (assoc (assoc current-stacks s new-src-row) d new-dest-row)))

(defn apply-instructions2 [current-stacks input]
  (if (empty? input)
    current-stacks
    (let [next-instruction (first input)
          split-line (do-split-line next-instruction)
          count (num split-line)
          s (src split-line)
          d (dst split-line)
          new-stacks (modify-stacks2 current-stacks count s d)]

      (apply-instructions2 new-stacks (rest input)))))

(def final-crate-positions2 (apply-instructions2 stacks instructions-input))
(map peek final-crate-positions2)
