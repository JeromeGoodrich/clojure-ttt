(ns clojure-ttt.core
  (:require [clojure-ttt.ui :refer :all]
            [clojure-ttt.board :refer :all]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure-ttt.ai :refer :all]
            [clojure-ttt.config :refer :all]))

;(defn make-move [board players]
;  (if (= (:type (first players)) "computer")
;    (ai-make-move board (map #(:marker %) players))
;    (let [space (human-make-move board players)
;          available-moves (find-unmarked-spaces board)
;          on-board? (some #(= (Integer. space) %) available-moves)]
;      (cond
;        (not on-board?) (do
;                          (invalid-move)
;                          (make-move board players))
;        :else (Integer. space)))))

(defn game-loop [board players markers]
 (print-board board)
 (if (game-over? board)
   board
   (let [board (make-move (first players) board markers)]
           (game-loop board (reverse players) (reverse markers)))))


(defn -main [& args]
  (let [{:keys [options arguments summary errors]} (parse-opts args cli-options)]
    (validate-cli options arguments summary errors)
    (let [players (player-config (first arguments) options)
            board (board-config options)
            markers (create-markers options)]
       (game-loop board players markers))))
