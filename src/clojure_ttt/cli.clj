(ns clojure-ttt.cli)


(def cli-options
  [["-f" "--first MARKER" "Player1 marker"
    :id :player1
    :default "X"
    :validate [#(= (count %) 1) "Marker is too long"]]
   ["-s" "--second MARKER" "Player2 marker"
    :id :player2
    :default "O"]
   ["-d" "--difficulty LEVEL" "AI difficulty"
    :default 3
    :desc "1 = dumb AI, 3=  AI wins 85% of time, 8= unbeatable AI"
    :parse-fn #(Integer. %)]
   ["-b"  "--board SIZE" "board size"
    :default 3
    :parse-fn #(Integer. %)]
   ["-h"  "--help"]
   ["-t" "--board-type TYPE" "Board Type"
    :default 1
    :parse-fn #(Integer. %)
    :desc "1 is the pretty board. 2 is a more intutive, but uglier board.
          To mark the upper left space on the pretty board hit 0.
          Numbers increase as you move right across the board"]])

(defn usage [options-summary]
  (->>["Welcome to Clojure TicTacToe."
       ""
       "Usage: lein run action [options]"
       ""
       "Examples:"
       "  lein run me-first -f x -s o      comp v. human, human goes first"
       "  lein run head-to-head -f q -s H -b 4    human v. human on a 4x4 board"
       ""
       "Options Summary:"
       options-summary
       ""
       "Actions:"
       "  me-first       play first against computer"
       "  comp-first     play second against the computer"
       "  head-to-head   play against another human"]
    (clojure.string/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (clojure.string/join \newline errors)))

(defn exit [status message]
  (println message)
  (System/exit status))

(defn validate-cli [options arguments summary errors]
  (cond
    (:help options) (exit 0 (usage summary))
    (not= (count arguments) 1) (exit 1 (usage summary))
    (= (:player1 options) (:player2 options)) (exit 1 "Markers cannot be the same.")
    errors (exit 1 (error-msg errors))
    (not (some #(= (first arguments) %) ["me-first" "comp-first" "head-to-head"])) (exit 1 (usage summary))))
