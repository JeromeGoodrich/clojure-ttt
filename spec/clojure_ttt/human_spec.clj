(ns clojure-ttt.human-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.human :refer :all]
            [clojure-ttt.spec-helper :refer :all]))

(describe "human-make-move"
  (it "returns a move if the space on the board is unmarked"
    (let [board [0 1 2 3 4 5 6 7 8]
          markers ["X" "O"]
          io (new-test-console ["7"])]
      (should= 7 (human-make-move board markers io))))

  (it "ignores invalid moves and returns a valid one"
    (let [board [0 1 2 3 4 5 6 7 8]
          markers ["X" "O"]
          io (new-test-console ["q" "12" "7"])]
      (should= 7 (human-make-move board markers io)))))
