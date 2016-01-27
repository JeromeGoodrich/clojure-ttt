(ns clojure-ttt.config
  (:require [clojure-ttt.board :refer :all]))

(defn get-types [game-type]
  (cond
    (= game-type "me-first") ["human" "computer"]
    (= game-type "comp-first") ["computer" "human"]
    (= game-type "head-to-head") ["human" "human"]))

(defn game-config [game-type options]
  (let [player-types (get-types game-type)
        player1 {:marker (:player1 options)
                 :type (first player-types)}
        player2 {:marker (:player2 options)
                 :type (second player-types)}
        board (create-board (:board options))]
    [player1 player2 board]))
