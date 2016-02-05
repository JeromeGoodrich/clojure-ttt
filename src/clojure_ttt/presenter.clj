(ns clojure-ttt.presenter)

(defprotocol Presenter
  (display-end-result [this markers board result])
  (get-input [this])
  (display-board [this board])
  (prompt [this something]))

(deftype Console [display-options]
  Presenter
  (get-input [this]
    (read-line))
  (prompt [this something]
    (println something)
    (read-line))
  (display-end-result [this markers board result]
   (do
     (if (= "win" result)
      (println "Game over! " (second markers) " wins!\n Play again? (y/n)")
      (println "Game over! It's a tie!\n Play again? (y/n)")))
    (read-line))
  (display-board [this board]
    (println (str "\n"(display-options board)))))

(defn new-console [display-options]
  (Console. display-options))


