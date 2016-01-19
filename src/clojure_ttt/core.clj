(ns clojure-ttt.core
  (:require [clojure-ttt.ui :refer :all]
           [clojure-ttt.board :refer :all]))

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

(defn make-move [player]
  (cond
    (= player :name "TicTacJoe") true
    :else (prompt "Select a space using the numbers of the spaces above")))

(defn end-game [player board]
  (cond
    (clojure-ttt.board/tie-game? board) (print "Game over! It's a tie!")
    :else (print (str "Game over! " (:name (first player)) " wins!"))))

;(defn game-loop
;  (let [players (create-players (prompt "Welcome to TicTacToe! How many humans will be playing (1 or 2)?")
;        board (board-size (prompt "What size board would you like to play on (3x3 = 3, 4x4 = 4, etc.)")
;    (loop [players players
;           board board]
;      (if (board/game-over? board))
;        (end-game board players)
;          (recur (board/mark-spot (first players :marker)
;                                  (make-move (first players))
;                                  board)
;             (reverse players))))


