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

(defn convert-row-to-board [row size]
  (let [last-space (nth row (dec size))
        other-spaces (pop row)]
     (apply str
      (apply str (map #(if (string? %) (str "_" % "_|") "___|") other-spaces))
      (if (string? last-space) (str "_" last-space "_\n") "___\n"))))

(defn convert-last-row-to-board [last-row size]
  (let [last-space (nth last-row (dec size))
        other-spaces (pop last-row)]
    (apply str
      (apply str (map #(if (string? %) (str " " % " |") "   |") other-spaces))
      (if (string? last-space) (str " " last-space " \n") "   \n"))))

(defn pretty-board [board]
  (let [size (int (Math/sqrt (count board)))
        rows  (vec (partition size board))
        last-row (vec (nth rows (dec size)))
        other-rows (map #(vec %) (pop rows))
        converted-other-rows (apply str (map #(convert-row-to-board % size) other-rows))
        converted-last-row (convert-last-row-to-board last-row size)]
    (apply str converted-other-rows converted-last-row)))

(defn row-to-string [row]
  (map #(str %) row))

(defn ugly-board [board]
  (let [size (int (Math/sqrt  (count board)))
        rows (partition size board)]
  (->> (map #(row-to-string %)rows)
       (map #(clojure.string/join "  " %))
       (map println)
       (dorun))))

(defn display-board-config [options]
  (if (= 1 (:board-type options)) pretty-board ugly-board))

(defn board-config [options]
  (create-board (:board options)))
