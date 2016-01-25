(ns clojure-ttt.ai-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.ai :refer :all]))

(describe "ai-performance"

  (it "chooses the only available move"
    (should= 8  (ai-make-move ["X" "O" "X"
                               "X" "O" "O"
                               "O" "X"  8] ["X" "O"])))

  (it "selects a winning move over a non-winning move"
    (should= 6 (ai-make-move ["O" "X" "O"
                              "O" "X" "X"
                               6   7  "X"] ["O" "X"])))

  (it "blocks the opponent from winning"
    (should= 6 (ai-make-move ["X" "O" "X"
                              "X" "O" "O"
                               6  "X"  8 ] ["O" "X"])))

  (it "blocks the opponent from winning"
    (should= 8 (ai-make-move ["X" "O" "X"
                              "O" "O" "X"
                               6  "X"  8 ] ["O" "X"])))

  (it "blocks the opponent from winning"
    (should= 1 (ai-make-move ["X" 1 "X"
                               3 "O" 5
                               6  7  8] ["O" "X"])))

  (it "will prevent the opponent from making a 'fork'"
    (should= 6 (ai-make-move ["X"   1    2
                               3   "X"   5
                               6    7   "O"] ["O" "X"])))

  (it "will win if given the chance"
    (should= 5 (ai-make-move ["X" 1 "O"
                               3 "X" 5
                              "X" 7 "O"] ["O" "X"])))

  (it "will move to a corner on the first move"
    (pending "")
    (should= 6 (ai-make-move [0 1 2
                              3 4 5
                              6 7 8] ["X" "O"]))))

