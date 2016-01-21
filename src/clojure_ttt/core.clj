(ns clojure-ttt.core
  (:require [clojure-ttt.ui :refer :all]
            [clojure-ttt.board :refer :all]
            [clojure.tools.cli :refer :all]))

(defn create-players [number]
  (cond
    (= "1" number) (let [players [{:name (prompt "What is your name?") :marker (prompt "Select a marker (it can be any letter)")}
                                  {:name "TicTacJoe" :marker (prompt "Select a marker for the computer")}]
                       order (prompt "Who will go first? (me/comp)")]
                    (cond
                      (= order "me") players
                      (= order "comp") (reverse players)))

    (= "2" number) (let [players [{:name (prompt "Player 1, what is your name?")
                                   :marker (prompt "Select a marker (it can be any letter)")}
                                  {:name (prompt "Player 2, what is your name?")
                                   :marker (prompt "Select a marker (it can be any letter not chosen by player 1)")}]] players)))
(def cli-options
  [["-p1" "--player1 TYPE" "Player1 type"
    :validate [(some ["h" "human" "computer" "comp" "c"]) "Player type must be human or computer"]]
   ["-p2" "--player2 TYPE" "Player2 type"
    :validate [(some ["h" "human" "computer" "comp" "c"]) "Player type must be human or computer"]]
;what happens if markers are the same?
    ["-m1" "--marker1 MARKER" "Player1 marker"
    :default "X"]
   ["-m2" "--marker2 MARKER" "Player2 marker"
    :default "O"]
   ["-b"  "--board SIZE" "board size"
    :default 3
    :parse-fn #(Integer/parseInt %)]
   ["-h"  "--help"]])


(defn score-board [space-with-board markers]
  (let [empty-spaces (filter number? board)
        possible-boards (map #(mark-spot marker % board) empty-spaces)
        boards-with-spaces (zip-spaces-and-boards empty-spaces possible-boards)
        scored-boards (score-boards boards-with-spaces score-board markers)
        my-marker (first markers)]

  (loop [board (:board (space-with-board))
         marker (first markers)]
    (cond
       (and (win-game? board) (= marker my-marker)) (assoc space-with-board {:score 10})
       (and (win-game? board) (not (= marker my-marker))) (assoc space-with-board {:score -10})
       (tie-game? board) (assoc space-with-board {:score 0})
       :else (if (= marker my-marker)
                (recur (max-by-score scored-boards)(reverse markers))
                (recur (min-by-score scored-boards)(reverse markers)))))))

(defn score-boards [spaces-with-boards scorer markers]
  (map #(scorer % markers) spaces-with-boards)

(defn zip-spaces-and-boards [spaces boards]
 map #(zipmap [:space :board] %) (map vector spaces boards))

(defn empty-spaces [board]
  (filter number? board))

(defn create-possible-boards [board spaces markers]
  (map #(mark-spot (first markers) % board) spaces)

(defn max-by-score [scored-boards]
  (apply max-key :score scored-boards))

(defn min-by-score [scored-boards]
  (apply min-key :score scored-boards))

(defn ai-make-move [board markers]
  (let [empty-spaces (find-empty-spaces board)
        possible-boards (create-possible-boards board empty-spaces markers)
        board-space-maps (zip-spaces-and-boards empty-spaces possible boards)
        scored-boards (score-boards (board-space-maps))]
    (max-by-score scored-boards))


    (map #(score-boards? %) possible-boards-with-spaces)

    (->> possible-boards-with-space ; {:space x :board []}
      (map score-board)
      (max-by :score board)
      :space-number)

(defn make-move [board players]
  (cond
    (= (:name (first player)) "TicTacJoe") (ai-make-move board (vector (:marker (first player)) (:marker (second player)))
    :else (prompt "Select a space using the numbers of the spaces above")))

(defn end-game [board players]
  (cond
    (clojure-ttt.board/tie-game? board) (print "Game over! It's a tie!")
    :else (print (str "Game over! " (:name (second players)) " wins!"))))

(defn game-loop [board players]
    (loop [board board
           players players]
      (print-board board)
      (if (game-over? board)
        (end-game board players)
        (recur (mark-spot (:marker (first players))
                                    (make-move board players)
                                    board)
               (reverse players)))))

(defn -main []
  (let [board (clojure-ttt.board/create-board 3)
       players (create-players (prompt "Welcome to TicTacToe! How many humans will be playing?"))]
 (game-loop board players)))


    ; look for command line args
    ; if none
    ;   interactive configuration
    ; {:player-1 {:space "X" :move-fn make-move} :player-2 {} :board-size 3}

