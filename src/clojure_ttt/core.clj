(ns clojure-ttt.core
  (:require [clojure-ttt.ui :refer :all]
            [clojure-ttt.board :refer :all]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure-ttt.ai :refer :all]
            [clojure-ttt.config :refer :all]
            [clojure-ttt.cli :refer :all]))


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
