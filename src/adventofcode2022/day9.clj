(ns adventofcode2022.day9
  (:require [clojure.string :as str] [clojure.math.combinatorics :as combo] [clojure.core.matrix]))

(def input (str/split (slurp "day9.input") #"\n"))

(defn direction [line]
  (first line))

(defn number [line]
  (Integer/parseInt (last (str/split line #" "))))

(def visited-positions (atom []))

(defn add-position [pos]
  (swap! visited-positions (fn [x] (conj @visited-positions pos))))

(defn move-tail [state]
  (let [hx (first (first state))
        hy (last (first state))
        tx (first (last state))
        ty (last (last state))]
    (cond
        ; head 2 squares right, move right
      (and (= hy ty) (= hx (+ 2 tx))) [[hx hy] [(inc tx) ty]]
        ; head 2 squares left, move left
      (and (= hy ty) (= hx (- tx 2))) [[hx hy] [(dec tx) ty]]
        ; head 2 squares up, move up
      (and (= hx tx) (= hy (- ty 2))) [[hx hy] [tx (inc ty)]]
        ; head 2 squares down, move down
      (and (= hx tx) (= hy (+ 2 ty))) [[hx hy] [tx (dec ty)]]

      ; one square away, dont' need to move
      (and (<= (abs (- hx tx)) 1) (<= (abs (- hy ty)) 1)) state

      ; same square, don't need to move
      (and (= hx tx) (= hy ty)) state

         ; head up right diagonal
      (and (> hy ty) (> hx tx)) [[hx hy] [(inc tx) (inc ty)]]
        ; head up left diagnoanl
      (and (> hy ty) (< hx tx)) [[hx hy] [(dec tx) (inc ty)]]
        ; head down right diagnoal
      (and (< hy ty) (> hx tx)) [[hx hy] [(inc tx) (dec ty)]]
        ; head down left diagonal
      (and (< hy ty) (< hx tx)) [[hx hy] [(dec tx) (dec ty)]]

    ; Don't need to move tail
      :else (println hx hy tx ty))))

(defn move-head [state x-modifier y-modifier num]
  (if (= num 0) state
      (let [cur-x (first (first state))
            cur-y (last (first state))
            next-state [[(x-modifier cur-x) (y-modifier cur-y)] [(first (last state)) (last (last state))]]
            next-state-after-tail-move (move-tail next-state)]
        (add-position (last next-state-after-tail-move))
        (recur next-state-after-tail-move x-modifier y-modifier (dec num)))))

(def move-funcs {\U [identity inc] \D [identity dec] \L [dec identity] \R [inc identity]})

(defn process-line [line state]
  (let [dir (direction line)
        num (number line)
        move-funcs (get move-funcs dir)]
    (move-head state (first move-funcs) (last move-funcs) num)))

(defn run [state lines]
  (if (empty? lines) state
      (recur (process-line (first lines) state) (rest lines))))


(def start [[100 100] [100 100]])

(run start input)
(count (set @visited-positions))

