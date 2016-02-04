(ns clojure-ttt.config_spec
 (:require [speclj.core :refer :all]
           [clojure-ttt.config :refer :all]
           [clojure-ttt.ui :refer :all])
  (:import [clojure_ttt.config HumanPlayer]
           [clojure_ttt.config AIPlayer]))

(describe "player-config"

  (it "created players are instances of Player"
    (let [io (new-console-io)]
      (should-be (map #(instance? Player %)) (player-config "me-first" {:difficulty 3} io pretty-board))))

  (context "me-first game"
    (it "human player is in the first position"
      (let [io (new-console-io)]
        (should-be #(isa? HumanPlayer %)
                   (type (first (player-config "me-first" {:difficulty 3} io pretty-board))))))
    (it "computer player is in the second position"
      (let [io (new-console-io)]
        (should-be #(isa? AIPlayer %)
                   (type (second (player-config "me-first" {:difficulty 3} io pretty-board)))))))

  (context "comp-first game"
    (it "computer player is in the first position"
      (let [io (new-console-io)]
        (should-be #(isa? AIPlayer %)
                   (type (first (player-config "comp-first" {:difficulty 3} io pretty-board))))))
    (it "human player is in the second position"
      (let [io (new-console-io)]
        (should-be #(isa? HumanPlayer %)
                   (type (second (player-config "comp-first" {:difficulty 3} io pretty-board)))))))

  (context "head-to-head game"
    (it "there are human players in both positions"
      (let [io (new-console-io)]
        (should= [true true]
                   (map #(= (type %) HumanPlayer) (player-config "head-to-head" {:difficulty 3} io pretty-board)))))))

(describe "create-marker"
  (it "creates markers for the game"
    (should= ["X" "O"] (create-markers {:player1 "X" :player2 "O"}))))

(describe "pretty-board"
  (context "3x3 board"
    (it "creates a string representation of the board"
      (should= (str "___|_X_|___\n"
                    "___|_O_|___\n"
                    "   |   |   \n")(pretty-board [0 "X" 2 3 "O" 5 6 7 8]))))

  (context "4x4 board"
    (it "creates a string representation of the board"
      (should= (str "___|___|___|___\n"
                    "___|___|___|___\n"
                    "___|___|___|___\n"
                    "   |   |   |   \n") (pretty-board [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15])))))

(describe "config-board-display"
  (it "returns pretty board if :board-type is 1"
    (should= pretty-board (config-board-display {:board-type 1})))

  (it "returns ugly-board if board-type is 2"
    (should= ugly-board (config-board-display {:board-type 2})))

  (it "returns an ugly board with any input other than 1 or 2"
    (should= ugly-board (config-board-display {:board-type "F"}))))

(describe "board-config"
  (it "creates a board given a size"
    (should= [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15] (board-config {:board 4}))
    (should= [0 1 2 3 4 5 6 7 8] (board-config {:board 3}))))
