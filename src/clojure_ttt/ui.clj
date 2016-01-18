(ns clojure-ttt.ui)

(defn prompt [message]
  (println message)
  (read-line))

(defn print-board [board]
  (let [rows (map #(apply str %) (partition 3 board))]
    (println rows)))

