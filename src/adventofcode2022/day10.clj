(ns adventofcode2022.day10
  (:require [clojure.string :as str] [clojure.math.combinatorics :as combo] [clojure.core.matrix]))

(def input (str/split (slurp "day10.input") #"\n"))
(def history (atom []))
(def initial-state {:x 1 :ticks 0})

(defn add-state [state]
  (let [x-pos (rem (dec (:ticks state)) 40)
        char (if (<= (abs (- x-pos (:x state))) 1) \# \ )]
    (print char)
    (if (= (rem (:ticks state) 40) 0) (println) nil)
    (reset! history (conj @history state))))

(defn noop [state]
  (add-state (assoc state :ticks (inc (:ticks state))))
  (assoc state :ticks (inc (:ticks state))))

(defn addx [state val]
  (add-state (assoc state :ticks (inc (:ticks state))))
  (add-state (assoc state :ticks (+ 2 (:ticks state))))
  (assoc state :x (+ (:x state) val) :ticks (+ (:ticks state) 2)))

(defn parts [row]
  (if (str/starts-with? row "noop") ["noop" "0"]
      (str/split row #" ")))

(defn run-instr [instr val state]
  (cond
    (= instr "noop") (noop state)
    (= instr "addx") (addx state val)))

(defn run [input state]
  (if (empty? input) state
      (let [[instr val] (parts (first input))
            next-state (run-instr instr (Integer/parseInt val) state)]
        (recur (rest input) next-state))))
(defn go []
  (reset! history [])
  (run input initial-state))

(defn signal [history ticks]
  (let [z (nth history (dec ticks))
        q (* (:x z) ticks)]
    (println q z)
    q))

(signal @history 20)
(def ticks-to-do [20 60 100 140 180 220])

(reduce + (map (fn [x] (signal @history x)) ticks-to-do))
(go)