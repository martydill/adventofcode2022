(ns adventofcode2022.day9
  (:require [clojure.string :as str] [clojure.math.combinatorics :as combo] [clojure.core.matrix]))

(def input (str/split (slurp "day9.input") #"\n"))

(defn direction [line]
  (first line))

(defn number [line]
  (Integer/parseInt (last (str/split line #" "))))

(defn process-line [line state]
(let [dir (diretion line)
      num (number line))
(cond (= dir \U) move-)

(defn run [state lines]
  (if (empty? lines) state
      (recur (process-line (first lines) state) (rest lines))))


(def start [[100 100] [100 100]])
(def visited-cells [])

(def unique-visited-cells (set visited-cells))

(run start input)
  
  (first "abc")
