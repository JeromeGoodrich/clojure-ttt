(ns clojure-ttt.config
  (:require [clojure-ttt.board :refer :all]
            [clojure-ttt.ui :refer :all]
            [clojure-ttt.ai :refer :all]))

(defprotocol Player
  (make-move [this board markers]))

(deftype HumanPlayer [io]
  Player
  (make-move [this board markers]
    (let [move (human-make-move board markers io)]
      (mark-spot (first markers) move board))))

(defn new-human-player [io]
  (HumanPlayer. io))

(deftype AIPlayer [difficulty]
 Player
 (make-move [this board markers]
  (let [move (ai-make-move board markers difficulty)]
    (mark-spot (first markers) move board))))

(defn new-computer-player [difficulty]
  (AIPlayer. difficulty))

(defn player-config [game-type options io]
  (let [difficulty (:difficulty options)
        human (new-human-player io)
        computer (new-computer-player difficulty)]
  (cond
    (= game-type "me-first") [human computer]
    (= game-type "comp-first") [computer human]
    (= game-type "head-to-head") [human human])))

(defn create-markers [options]
  (let [marker1 (:player1 options)
        marker2 (:player2 options)]
    [marker1 marker2]))

(defn pretty-board [board]
  (let [size (int (Math/sqrt (count board)))
        rows  (vec (partition size board))
        last-row (vec (nth rows (dec size)))
        other-rows (map #(vec %) (pop rows))
        converted-other-rows (apply str (map #(pretty-row-to-string % size) other-rows))
        converted-last-row (pretty-last-row-to-string last-row size)]
    (apply str converted-other-rows converted-last-row)))

(defn ugly-board [board]
  (let [size (int (Math/sqrt  (count board)))
        rows (partition size board)]
  (->> (map #(row-to-string %) rows)
       (map println)
       (dorun))))

(defn config-board-display [options]
  (if (= 1 (:board-type options)) pretty-board ugly-board))

(defn board-config [options]
  (create-board (:board options)))
