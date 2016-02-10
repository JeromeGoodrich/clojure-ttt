(ns clojure-ttt.presenter)

(defprotocol Presenter
  (print-io [this something])
  (display-end-result [this result markers])
  (get-input [this])
  (display-board [this board])
  (prompt [this something]))

(deftype Console [display-options]
  Presenter
  (print-io [this something]
    (println something))
  (get-input [this]
    (read-line))
  (prompt [this something]
    (println something)
    (read-line))
  (display-end-result [this result markers]
     (if (= "win" result)
      (println "Game over! " (second markers) " wins!\n Play again? (y/n)")
      (println "Game over! It's a tie!\n Play again? (y/n)")))
  (display-board [this board]
    (println (str "\n"(display-options board)))))

(defn new-console [display-options]
  (Console. display-options))
