(ns adventofcode2022.day6
  (:require [clojure.string :as str]))

(def input (slurp "day6.input"))
(def groups (partition 4 1 input))
(def set-groups (map set groups))
(defn not-match [x] (not (= (count x) 4)))
(def non-matching-groups (take-while not-match set-groups))
(def matching-group-index (+ (count non-matching-groups) 4))
matching-group-index
 
; part 2

(def groups (partition 14 1 input))
(def set-groups (map set groups))
(defn not-match [x] (not (= (count x) 14)))
(def non-matching-groups (take-while not-match set-groups))/login
(def matching-group-index (+ (count non-matching-groups) 14))
matching-group-index