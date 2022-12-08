(ns adventofcode2022.day7
  (:require [clojure.string :as str]))

(def input (str/split (slurp "day7.input") #"\n"))



; takes cmd, input, and current state
; returns next input and next state
(defn run-cd [cmd input current-state]
  [input (assoc current-state 0 cmd)])


; takes cmd, input, and current state
; returns next input and next state
;; (defn run-ls [cmd input current-state]
;;   (let [files-in-dir atom []]


;;     (if (str/starts-with? input "$")
;;       [(rest input) current-state]

;;       nil))

(def start-state ["/" {}])
(run-cd "asdf" {} start-state)

; takes cmd, input, and current state
; returns next input and next state
;; (defn run-command [cmd input current-state]
;;   (cond
;;     (= cmd "$ ls") (run-ls "ls" input current-state)
;;     (str/starts-with? cmd "$ cd ") (run-cd (last (str/split "$ cd asdf" #" ")) input current-state)
;;     (str/starts-with? cmd "dir ")
;;     :else  (println "other" cmd)) current-state)

;; (defn parse [input current-state]
;;   (let [next-command (first input)]
;;     (if (empty? input)
;;       current-state
;;       (let [[next-input next-state] (run-command next-command (rest input) current-state)]
;;       (parse next-input next-state)))))

(defn up-one-level [path]
  (let [new-path (str/join "/" (butlast (str/split path #"/")))]
    (if (= new-path "") "/"
        new-path)))

(defn down-one-level [path cmd]
  (str/replace (str path "/" (last (str/split cmd #" "))) "//" "/"))

(down-one-level "/a", "$ cd asdf")
(down-one-level "/", "$ cd asdf")

(up-one-level "/asdf/abcd/eeee")
(up-one-level "/asdf)")

(defn add-entry [path cmd current-state]
(let [parts (str/split cmd #" ")
      filename (first parts)
      filesize (last parts)
      fullpath (str path "/" filename)]
  (assoc current-state fullpath filesize))

(defn parse [input current-path current-state]
  (let [next-command (first input)]
    (if (empty? next-command)
      current-state
      (cond
        (= next-command "$ ls") (parse (rest input) current-path current-state) ; skip
        (str/starts-with? next-command "$ cd ..") (parse (rest input) (up-one-level current-path) current-state) ; change dir up
        (str/starts-with? next-command "$ cd ") (parse (rest input) (down-one-level current-path next-command) current-state) ; chagne dir down
        (str/starts-with? next-command "dir ") (parse (rest input) current-path current-state)
        :else (parse (rest input) current-path (add-entry path cmd current-state))))))) ; ignore?

(parse input "/" start-state)


(str/join (butlast ["1" "2" "3"]))





