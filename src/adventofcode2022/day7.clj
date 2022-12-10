(ns adventofcode2022.day7
  (:require [clojure.string :as str]))

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

(def input (str/split (slurp "day7.input") #"\n"))
(def tree (parse input "/" {}))

(def MAX 100000)

(defn dir-name [path]
  (subs path 0 (str/last-index-of path "/")))

; need to get all dir names
; /foo/bar/baz/aaaa.q = /foo, /foo/bar, /foo/bar/baz
(defn dir-names [path]
  (if (= path "")
    [path]
    (let [dn (dir-name path)]
      (conj (dir-names dn) dn))))

(defn get-directories [state]
  (set (flatten (map dir-names (keys state)))))

(def all-dirs (get-directories tree))

(defn children [dir state]
  (filter (fn [x] (str/starts-with? x (str dir "/"))) (keys state)))

(defn file-size [file state] (Integer/parseInt (get state file)))

(defn directory-size [dir state]
  (let [child-dirs (children dir state)]
    (reduce + (map (fn [x] (file-size x state)) child-dirs))))

(defn all-directory-sizes [state all-dirs]
  (filter (fn [size] (<= size MAX)) (map (fn [dir] (directory-size dir state)) all-dirs)))

(reduce + (all-directory-sizes tree all-dirs))

; part 2
(def total-disk-usage (reduce + (map (fn [x] (file-size x tree)) (keys tree))))
(def require-to-free (- 30000000 (- 70000000 total-disk-usage)))
(defn all-directory-sizes-2 [state all-dirs]
  (map (fn [dir] (directory-size dir state)) all-dirs))

(def sorted-sizes (sort (all-directory-sizes-2 tree all-dirs)))
(first (filter (fn [x] (< require-to-free x)) sorted-sizes))

