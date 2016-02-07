(ns clojure-ttt.core
  (:require [clojure-ttt.presenter :refer :all]
            [clojure-ttt.board :refer :all]
            [clojure-ttt.player :refer :all]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure-ttt.config :refer :all]
            [clojure-ttt.cli :refer :all]))


(defn evaluate-end-game [game]
  (if (win-game? game)
    "win"
    "tie"))

(defn ame-loop [board players markers io]
  (display-board io board)
  ( (game-over? board)
    (do
      (display-end-result io (evaluate-end-game board) markers)
      board)
    (let [board (make-move (first players) board markers)]
           (game-loop board (reverse players) (reverse markers) io))))

(defn end-game [game board markers io players]
  (let  [response (get-input io)]
      (if (not= response  "y")
        (println "See you next time!")
        (let [new-game (game-loop board players markers io)]
        (end-game new-game board markers io players)))))

(defn -main [& args]
  (let [{:keys [options arguments summary errors]} (parse-opts args cli-options)]
    (validate-cli options arguments summary errors)
    (let [io (new-console (parse-board-display options))
          players (player-config (first arguments) options io)
          board (board-config options)
          markers (create-markers options)]
     (end-game (game-loop board players markers io)
               board
               markers
               io
               players))))


  ; evaluate end-game and ask user to play again
   ; capture response
   ; if yes, get initial conditions and restart game-loop
