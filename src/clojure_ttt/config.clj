(ns clojure-ttt.config
  (:require [clojure-ttt.board :refer :all]
            [clojure-ttt.ui :refer :all]
            [clojure-ttt.ai :refer :all]))


(defprotocol Player
  (make-move [this board markers]))

(deftype HumanPlayer [ ]
  Player
  (make-move [this board markers]
    (let [move (human-make-move board markers)]
      (mark-spot (first markers) move board))))

(defn new-human-player [ ]
  (HumanPlayer.))

(deftype AIPlayer [difficulty]
 Player
 (make-move [this board markers]
  (let [move (ai-make-move board markers difficulty)]
    (mark-spot (first markers) move board))))

(defn new-computer-player [difficulty]
  (AIPlayer. difficulty))

(defn player-config [game-type options]
  (let [difficulty (:difficulty options)
        human (new-human-player)
        computer (new-computer-player difficulty)]
  (cond
    (= game-type "me-first") [human computer]
    (= game-type "comp-first") [computer human]
    (= game-type "head-to-head") [human human])))

(defn create-markers [options]
  (let [marker1 (:player1 options)
        marker2 (:player2 options)]
    [marker1 marker2]))

(defn board-config [options]
  (create-board (:board options)))
