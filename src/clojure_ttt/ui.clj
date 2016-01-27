(ns clojure-ttt.ui)

(defn prompt [message]
  (println message)
  (read-line))

(defn print-board [board]
  (->> (partition 3 board)
       (map #(apply str %))
       (map #(clojure.string/join " " %))
       (map println)
       (dorun)))

(def cli-options
  [["-p1" "--player1 MARKER" "Player1 marker"
    :default "X"]
   ["-p2" "--player2 MARKER" "Player2 marker"
    :default "O"]
   ["-b"  "--board SIZE" "board size"
    :default 3
    :parse-fn #(Integer. %)]
   ["-h"  "--help"]])

(defn choose-space [players]
  (let [current-player (:marker (first players))]
  (prompt (str "It is your turn, " current-player ". Choose an unmarked space."))))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (clojure.string/join \newline errors)))

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

(defn winner [players]
  (print (str "Game over! " (:marker (second players)) " wins!")))

(defn tie []
  (print "Game over! It's a tie!"))
