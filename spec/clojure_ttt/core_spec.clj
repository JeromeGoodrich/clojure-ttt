(ns clojure-ttt.core-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.core :refer :all]
            [clojure-ttt.config :refer :all]
            [clojure-ttt.ui :refer :all]
            [clojure-ttt.ai :refer :all]))

(describe "game-loop"
  (it "plays through the last move of the game and returns the board"
    (let [io (new-console-io)
          human (new-human-player io pretty-board)
          ai (new-computer-player 3)]
    (should= ["X" "O" "X" "O" "O" "X" "O" "X" "X"] (game-loop ["X" "O" "X" "O" "O" "X" "O" "X" 8]
                                                              [ai human]
                                                              ["X" "O"])))))




