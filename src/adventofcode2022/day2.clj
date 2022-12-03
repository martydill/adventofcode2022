(ns adventofcode2022.day2
  (:require [clojure.string :as str]))

(def input (slurp "day2.input"))
(def split-input (str/split input #"\n"))

; A - rock
; B - paper
; C - scissors
; X - rock
; Y - Paper
; Z - scissors

(def scores {"AY" 8 ; win
             "AX" 4 ; tie
             "AZ" 3 ; loss
             "BY" 5 ; tie
             "BX" 1 ; loss
             "BZ" 9 ; win
             "CY" 2 ; loss
             "CX" 7 ; win
             "CZ" 6 ; tie
             })

(defn foo [x] (list (map + [1 2 3])))


(let [a    :b]
  (str "foo"     "bar" "baz"
       "a"    a))

(defn score [row] (get scores (str/replace row " " "")))
(def results (map score split-input))
(reduce + results)


(def needed {"AY" "AX" ; tie
             "AX" "AZ" ; lose 
             "AZ" "AY" ; win
             "BY" "BY" ; tie
             "BX" "BX" ; lose
             "BZ" "BZ" ; win
             "CY" "CZ" ; tie
             "CX" "CY" ; lose 
             "CZ" "CX" ; win
             })

(defn score2 [row] (get scores (get needed (str/replace row " " ""))))
(def results2 (map score2 split-input))
(reduce + results2)

