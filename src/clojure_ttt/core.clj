(ns clojure-ttt.core
  (:require [clojure-ttt.presenter :refer :all]
            [clojure-ttt.board :refer :all]
            [clojure-ttt.player :refer :all]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure-ttt.config :refer :all]
            [clojure-ttt.cli :refer :all]))


(defn evaluate-end-game [board]
  (if (win-game? board)
    "win"
    "tie"))

(defn end-game [board markers io]
 (->> (evaluate-end-game board)
      (display-end-result io markers board)))

(defn game-loop [board players markers io]
 (display-board io board)
 (if (game-over? board)
   board
   (let [board (make-move (first players) board markers)]
           (game-loop board (reverse players) (reverse markers) io))))

(defn -main [& args]
  (let [{:keys [options arguments summary errors]} (parse-opts args cli-options)]
    (validate-cli options arguments summary errors)
    (let [io (new-console (parse-board-display options))
          players (player-config (first arguments) options io)
          board (board-config options)
          markers (create-markers options)]
     (end-game (game-loop board players markers io)
               markers
               io))))
