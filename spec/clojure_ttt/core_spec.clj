(ns clojure-ttt.core-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.core :refer :all]
            [clojure-ttt.config :refer :all]
            [clojure-ttt.presenter :refer :all]
            [clojure-ttt.ai :refer :all]
            [clojure-ttt.player :refer :all]
            [clojure-ttt.board :refer :all]
            [clojure-ttt.spec-helper :refer :all]
            [clojure-ttt.cli :refer :all]))

(def game-loops (atom 0))

(defn mock-game-loop [& args]
  (swap! game-loops inc))

(describe "eval end game"
  (it "correctly evaluates a tie game"
    (should= "tie" (eval-end-game ["X" "O" "X"
                                   "X" "O" "O"
                                   "O" "X" "X"])))

  (it "correctly evaluates a won game"
    (should= "win" (eval-end-game ["X" "O" "X"
                                   "O" "X" "O"
                                   "X"  7   8]))))

(describe "game-loop"
  (it "plays through the last 2 moves of a game and returns the board"
    (let [io (new-test-console ["6"])
          human (new-human-player io)
          computer (new-computer-player 3)]
    (should= ["X" "O" "X" "O" "X" "O" "X" 7 "O"]
             (game-loop ["X" "O" "X" "O" "X" "O" 6 7 8]
                             [computer human]
                             ["O" "X"]
                             io)))))

(describe "restart-game-maybe"
  (it "correctly exits the game"
    (with-redefs [exit mock-exit]
    (let [game ["X" "O" "X" "O" "X" "O" "X" 7 "O"]
          board [0 1 2 3 4 5 6 7 8]
          markers ["X" "O"]
          io (new-test-console ["n"])
          players [(new-human-player io) (new-computer-player 3)]]
      (should= 0 (restart-game-maybe game board markers io players)))))

  (it "starts a new game with the same config"
   (with-redefs [exit mock-exit
                 game-loop mock-game-loop]
    (let [game ["X" "O" "X" "O" "X" "O" "X" 7 "O"]
          board [0 1 2 3 4 5 6 7 8]
          markers ["X" "O"]
          io (new-test-console ["y" "n"])
          players [(new-human-player io) (new-computer-player 3)]
          restart-game (restart-game-maybe game board markers io players)]
      (should= 1 @game-loops)))))
