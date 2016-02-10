(ns clojure-ttt.ai-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.ai :refer :all]
            [clojure-ttt.board :refer :all]))


(defn random-ai [board markers depth-limit]
  (rand-nth (find-unmarked-spaces board)))


(defn gen-all-boards [board markers starting-marker ai depth-limit]
  (let [current-marker (first markers)]
  (cond
    (and (win-game? board)(= starting-marker current-marker)) true
    (and (win-game? board)(not (= starting-marker current-marker))) false
    (tie-game? board) true
    :else
      (if (= current-marker starting-marker)
        (let [unmarked-spaces (find-unmarked-spaces board)
              boards (create-possible-boards board unmarked-spaces markers)]
          (flatten (map #(gen-all-boards % (reverse markers) starting-marker ai depth-limit) boards)))
        (let [move (ai board markers depth-limit)
              board (mark-spot (first markers) move board)]
          (gen-all-boards board (reverse markers) starting-marker ai depth-limit))))))

(defn calc-winning [gen-all-boards]
 (let [total (count (flatten (gen-all-boards)))
       losing (count (filter false? (flatten (gen-all-boards))))]
        (float (/ losing total))))

(defn mock-game-loop [board markers]
  (cond
    (win-game? board) false
    (tie-game? board) true
    :else (let [marker (first markers)
                spot (ai-make-move board markers)
                board (mark-spot marker spot board)]
             (mock-game-loop board (reverse markers)))))




(describe "ai-performance"

  (it "chooses the only available move"
    (should= 8  (ai-make-move ["X" "O" "X"
                               "X" "O" "O"
                               "O" "X"  8] ["X" "O"] 8)))

  (it "selects a winning move over a non-winning move"
    (should= 6 (ai-make-move ["O" "X" "O"
                              "O" "X" "X"
                               6   7  "X"] ["O" "X"] 8)))

  (it "blocks the opponent from winning"
    (should= 6 (ai-make-move ["X" "O" "X"
                              "X" "O" "O"
                               6  "X"  8 ] ["O" "X"] 8)))

  (it "blocks the opponent from winning"
    (should= 8 (ai-make-move ["X" "O" "X"
                              "O" "O" "X"
                               6  "X"  8 ] ["O" "X"] 8)))

  (it "blocks the opponent from winning"
    (should= 1 (ai-make-move ["X"  1  "X"
                              "X" "O" "O"
                              "O"  7   8] ["O" "X"] 8)))

  (it "blocks the opponent from winning"
    (should= 1 (ai-make-move ["X" 1 "X"
                               3 "O" 5
                               6  7  8] ["O" "X"] 8)))

  (it "will prevent the opponent from making a 'fork'"
    (should= 6 (ai-make-move ["X"   1    2
                               3   "X"   5
                               6    7   "O"] ["O" "X"] 8)))

  (it "will win if given the chance"
    (should= 5 (ai-make-move ["X" 1 "O"
                               3 "X" 5
                              "X" 7 "O"] ["O" "X"] 8)))

  (it "will win if given the chance"
    (should= 3 (ai-make-move ["O" 1 "X"
                               3 "X" 5
                              "O" 7 "X"] ["O" "X"] 8)))

  (it "a random ai will lose if going through all possible game states"
    (should= true  (some false? (flatten (gen-all-boards [0 1 2 3 4 5 6 7 8] ["X" "O"] "X" random-ai 8)))))

  (it "minimax ai will never lose"
   (pending "")
    (should-not (some false? (flatten (gen-all-boards [0 1 2 3 4 5 6 7 8] ["X" "O"] "X" ai-make-move 8)))))

  (it "minimax ai will win around 85% of the time"
    (should-be (and #(> 85 %) #(< 80 %))  (- 100 (* 100 (float (/ (count (filter false? (gen-all-boards [0 1 2 3 4 5 6 7 8] ["X" "O"] "X" ai-make-move 3)))
                                                                  (count (gen-all-boards [0 1 2 3 4 5 6 7 8] ["X" "O"] "X" ai-make-move 3))))))))

  (it "will move to a corner on the first move"
    (should= 8 (ai-make-move [0 1 2
                              3 4 5
                              6 7 8] ["X" "O"] 8))))

