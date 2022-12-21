(ns adventofcode2022.day11
  (:require [clojure.string :as str] [clojure.math.combinatorics :as combo] [clojure.core.matrix]))

(def all-monkeys [{:items [76, 88, 96, 97, 58, 61, 67]
                   :op (fn [old] (* old 19))
                   :test 3
                   :if_true 2
                   :if_false 3
                   :inspect_count 0},

                  {:items [93, 71, 79, 83, 69, 70, 94, 98]
                   :op (fn [old] (+ old 8))
                   :test 11
                   :if_true 5
                   :if_false 6
                   :inspect_count 0},

                  {:items [50, 74, 67, 92, 61, 76]
                   :op (fn [old] (* old 13))
                   :test 19
                   :if_true 3
                   :if_false 1
                   :inspect_count 0},

                  {:items [76, 92]
                   :op (fn [old] (+ old 6))
                   :test 5
                   :if_true 1
                   :if_false 6
                   :inspect_count 0},

                  {:items [74, 94, 55, 87, 62]
                   :op (fn [old] (+ old 5))
                   :test 2
                   :if_true 2
                   :if_false 0
                   :inspect_count 0},

                  {:items [59, 62, 53, 62]
                   :op (fn [old] (* old old))
                   :test 7
                   :if_true 4
                   :if_false 7
                   :inspect_count 0},

                  {:items [62]
                   :op (fn [old] (+ old 2))
                   :test 17
                   :if_true 5
                   :if_false 7
                   :inspect_count 0},

                  {:items [85, 54, 53]
                   :op (fn [old] (+ old 3))
                   :test 13
                   :if_true 4
                   :if_false 0
                   :inspect_count 0}])


(defn inspect [monkeys monkey monkey-index]
  (let [items (atom (:items monkey))
        mutable-monkeys (atom monkeys)
        op (:op monkey)
        test (:test monkey)
        if-true (:if_true monkey)
        if-false (:if_false monkey)]
    (doseq [item @items]
      (let [new-worry-level (int (Math/floor (mod (op item) 9699690)))
            target-monkey-index (if (= (rem new-worry-level test) 0) if-true if-false)
            target-monkey-items (:items (nth @mutable-monkeys target-monkey-index))]
        (reset! mutable-monkeys (assoc-in @mutable-monkeys [target-monkey-index :items] (conj target-monkey-items new-worry-level)))))
    (assoc-in (assoc-in @mutable-monkeys [monkey-index :items] []) [monkey-index :inspect_count] (+ (:inspect_count monkey) (count (:items monkey))))))

(defn turn [monkeys]
  (let [mutable-monkeys (atom monkeys)]
    (doseq [monkey-index (range (count @mutable-monkeys))]
      (let [monkey (nth @mutable-monkeys monkey-index)
            next-monkey-state (inspect @mutable-monkeys monkey monkey-index)]
        (reset! mutable-monkeys next-monkey-state))) @mutable-monkeys))

(defn run-until [count monkeys]
  (if (= 0 count)
    monkeys
    (recur (dec count) (turn monkeys))))

(reduce * (take 2 (reverse (sort (map :inspect_count (run-until 10000 all-monkeys))))))




