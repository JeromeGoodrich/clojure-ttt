(ns clojure-ttt.ui)

(defn prompt [message]
  (println message)
  (read-line))

(defn print-board [board]
  (->> (partition 3 board)
       (map #(apply str %))
       (map #(clojure.string/join " " %))
       (map println)
       (dorun)))

