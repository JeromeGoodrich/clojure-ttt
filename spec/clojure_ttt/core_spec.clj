(ns clojure-ttt.core-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.core :refer :all]
            [clojure-ttt.config :refer :all]
            [clojure-ttt.presenter :refer :all]
            [clojure-ttt.ai :refer :all]
            [clojure-ttt.player :refer :all]
            [clojure-ttt.board :refer :all]))


(defn mock-game-loop [board players markers io]
  (if (game-over? board)
    board
    (let [board (make-move (first players) board markers)]
           (mock-game-loop board (reverse players) (reverse markers) io))))


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
    (let [io (new-test-console "6" pretty-board)
          human (new-human-player io)
          computer (new-computer-player 3)]
    (should= ["X" "O" "X" "O" "X" "O" "X" 7 "O"]
             (mock-game-loop ["X" "O" "X" "O" "X" "O" 6 7 8]
                             [computer human]
                             ["O" "X"]
                             io)))))

(describe "restart-game"
  (it "correctly exits the game"
    (let [game ["X" "O" "X" "O" "X" "O" "X" 7 "O"]
          board [0 1 2 3 4 5 6 7 8]
          markers ["X" "O"]
          io (new-test-console "n" pretty-board)
          players [(new-human-player io) (new-computer-player 3)]]
      (should= "See you next time!\n" (with-out-str (restart-game game board markers io players)))))

  (it "starts a new game with the same config"
    (let [game ["X" "O" "X" "O" "X" "O" "X" 7 "O"]
          board [0 1 2 3 4 5 6 7 8]
          markers ["X" "O"]
          io (new-test-console "y" pretty-board)
          players [(new-computer-player 3) (new-computer-player 3)]
          new-game (game-loop board players markers io)]
      (should-be (restart-game new-game board markers io players) (restart-game game board markers io players)))))






