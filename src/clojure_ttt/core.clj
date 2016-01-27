(ns clojure-ttt.core
  (:require [clojure-ttt.ui :refer :all]
            [clojure-ttt.board :refer :all]
            [clojure.tools.cli :refer :all]
            [clojure-ttt.ai :refer :all]
            [clojure-ttt.config :refer :all]))

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
  [["-p1" "--player1 MARKER" "Player1 marker"
    :default "X"]
   ["-p2" "--player2 MARKER" "Player2 marker"
    :default "O"]
   ["-b"  "--board SIZE" "board size"
    :default 3
    :parse-fn #(Integer. %)]
   ["-h"  "--help"]])

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (clojure.string/join \newline errors)))

(defn make-move [board players]
      (if (= (:name (first players)) "TicTacJoe")
        (ai-make-move board (map #(:marker %) players))
        (Integer. (prompt "Select a space using the numbers of the spaces above"))))

(defn end-game [board players]
  (cond
    (tie-game? board) (print "Game over! It's a tie!")
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

(defn exit [status message]
  (println message)
  (System/exit status))

(defn usage [options-summary]
  (->>["Welcome to Clojure TicTacToe."
       ""
       "Usage: lein run action [options]"
       ""
       "Examples:"
       "  lein run me-first -p1 x -p2 o      comp v. human, human goes first"
       "  lein run head-to-head -p2 q -p2 -b 4    human v. human on a 4x4 board"
       ""
       "Options Summary:"
       options-summary
       ""
       "Actions:"
       "  me-first       play first against computer"
       "  comp-first     play second against the computer"
       "  head-to-head   play against another human"]
    (clojure.string/join \newline)))

(defn -main [& args]
  (let [{:keys [options arguments summary errors]} (parse-opts args cli-options)]
    (cond
      (:help options) (exit 0 (usage summary))
      (not (> 0 (count arguments))) (exit 1 (usage summary)))
      errors (exit 1 (error-msg errors))
    (let [config (game-config (first arguments) options)
          players [(first config) (second config)]
          board  (nth config 2)]
      (game-loop board players))))


