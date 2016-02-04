(ns clojure-ttt.player
  (:require [clojure-ttt.ai :refer :all]
            [clojure-ttt.human :refer :all]
            [clojure-ttt.board :refer :all]))

(defprotocol Player
  (make-move [this board markers]))

(deftype HumanPlayer [io board-display]
  Player
  (make-move [this board markers]
    (let [move (human-make-move board markers io board-display)]
      (mark-spot (first markers) move board))))

(defn new-human-player [io board-display]
  (HumanPlayer. io board-display))

(deftype AIPlayer [difficulty]
 Player
 (make-move [this board markers]
  (let [move (ai-make-move board markers difficulty)]
    (mark-spot (first markers) move board))))

(defn new-computer-player [difficulty]
  (AIPlayer. difficulty))
