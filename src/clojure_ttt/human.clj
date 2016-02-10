(ns clojure-ttt.human
  (:require [clojure-ttt.presenter :refer :all]))

(defn choose-space [markers io]
  (let [current-marker (first markers)]
    (prompt io (str "It is your turn, " current-marker ". Choose an unmarked space."))))

(defn validate-move [io board move]
 (let [available-moves (filter number? board)]
   (if (not (some #(= (Integer. move) %) available-moves))
     (validate-move io board (prompt io "Invalid move, please choose a valid space to move to"))
     (Integer. move))))

(defn human-make-move [board markers io]
  (let [move (choose-space markers io)]
  (try (validate-move io board move)
    (catch Exception e (do
                         (print-io io (str (.getMessage e) " That's not a number. Let's try that again!\n"))
                         (human-make-move board markers io))))))
