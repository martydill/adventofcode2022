(ns adventofcode2022.day7
  (:require [clojure.string :as str]))

(def input (str/split (slurp "day7.input") #"\n"))

(defn up-one-level [path]
  (let [new-path (str/join "/" (butlast (str/split path #"/")))]
    (if (= new-path "") "/"
        new-path)))

(defn down-one-level [path cmd]
  (let [dest (last (str/split cmd #" "))]
    (if (= dest "/")
      "/"
      (str/replace (str path "/" dest) "//" "/"))))

(defn add-entry [path cmd current-state]
  (let [parts (str/split cmd #" ")
        filename (last parts)
        filesize (first parts)
        fullpath (down-one-level path filename)]
    (assoc current-state fullpath filesize)))

(defn parse [input current-path current-state]
  (let [next-command (first input)]
    (if (empty? next-command)
      current-state
      (cond
        (= next-command "$ ls") (parse (rest input) current-path current-state) ; skip
        (str/starts-with? next-command "$ cd ..") (parse (rest input) (up-one-level current-path) current-state) ; change dir up
        (str/starts-with? next-command "$ cd ") (parse (rest input) (down-one-level current-path next-command) current-state) ; chagne dir down
        (str/starts-with? next-command "dir ") (parse (rest input) current-path current-state)
        :else (parse (rest input) current-path (add-entry current-path next-command current-state))))))

(def tree (parse input "/" {}))






