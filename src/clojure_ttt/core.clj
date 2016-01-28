(ns clojure-ttt.core
  (:require [clojure-ttt.ui :refer :all]
            [clojure-ttt.board :refer :all]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure-ttt.ai :refer :all]
            [clojure-ttt.config :refer :all]))

(defn make-move [board players]
  (if (= (:type (first players)) "computer")
        (ai-make-move board (map #(:marker %) players))
        (let [space (Integer. (choose-space players))
              available-moves (find-unmarked-spaces board)]
          (cond
           (or (not (number? space))
               (not (some #(=  space %) (find-unmarked-spaces board))))
           (do (invalid-move) (make-move board players))
          :else space))))

(defn game-loop [board players]
      (print-board board)
      (cond
        (win-game? board) (winner players)
        (tie-game? board) (tie)
        :else (let [board (mark-spot (:marker (first players))
                                     (make-move board players)
                                      board)]
               (game-loop board (reverse players)))))

(defn exit [status message]
  (println message)
  (System/exit status))


(defn -main [& args]
  (let [{:keys [options arguments summary errors]} (parse-opts args cli-options)]
    (println (parse-opts args cli-options))
    (cond
      (:help options) (exit 0 (usage summary))
      (not= (count arguments) 1) (exit 1 (usage summary))
       errors (exit 1 (error-msg errors)))
    (if (some #(= (first arguments) %) ["me-first" "comp-first" "head-to-head"])
      (let [config (game-config (first arguments) options)
            players [(first config) (second config)]
            board  (nth config 2)] (game-loop board players))
      (exit 1 (usage summary)))))


