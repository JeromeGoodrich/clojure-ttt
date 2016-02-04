(ns clojure-ttt.core
  (:require [clojure-ttt.io :refer :all]
            [clojure-ttt.board :refer :all]
            [clojure-ttt.player :refer :all]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure-ttt.config :refer :all]
            [clojure-ttt.cli :refer :all]))


; Presenter object depends on IO as does Player
  ; roll Player protocol into Player namespace
  ; human-make move and ai make-move in their own namespaces
(defn end-result [board markers]
 (display-board board board-type)
  (if (win-game? board)
    (prompt io "Game over! " (second markers) " wins!\n Play again? (y/n)")
    (prompt io "Game over! It's a tie!\n Play again? (y/n)")))


(defn game-loop [board players markers]
 (if (game-over? board)
   board
   (let [board (make-move (first players) board markers)]
           (game-loop board (reverse players) (reverse markers)))))

(defn -main [& args]
  (let [{:keys [options arguments summary errors]} (parse-opts args cli-options)]
    (validate-cli options arguments summary errors)
    (let [io (new-console-io)
          board-display (config-board-display options)
          players (player-config (first arguments) options io board-display)
          board (board-config options)
          markers (create-markers options)]
      (game-loop board players markers))))
