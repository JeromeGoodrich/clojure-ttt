(ns clojure-ttt.ai
  (:require [clojure-ttt.ui :refer :all]
            [clojure-ttt.board :refer :all]
            [clojure.tools.cli :refer :all]))

(defn zip-spaces-and-boards [spaces boards]
 (map #(zipmap [:space :board] %) (map vector spaces boards)))

(defn find-empty-spaces [board]
  (filter number? board))

(defn get-initial-marker [markers]
    (first markers))

(defn create-possible-boards [board spaces markers]
  (map #(mark-spot (first markers) % board) spaces))

(defn max-by-score [scored-boards]
  (apply max-key :score scored-boards))

(defn min-by-score [scored-boards]
  (apply min-key :score scored-boards))

(defn ai-config [board markers]
  (let [empty-spaces (find-empty-spaces board)
        possible-boards (create-possible-boards board empty-spaces markers)]
    (zip-spaces-and-boards empty-spaces possible-boards)))

(defn score-board [space unscored-board markers initial-marker]
    (let [current-marker (first markers)]
       (cond

        ;evaluates terminal states based on comparison between initial and current marker
        (and (win-game? unscored-board) (= current-marker initial-marker)) (conj space {:score  10})
        (and (win-game? unscored-board) (not (= current-marker initial-marker))) (conj space {:score -10})
        (tie-game? unscored-board) (conj space {:score 0})

        :else (let [new-markers (reverse markers)
                    empty-spaces (find-empty-spaces unscored-board)
                    next-node-boards (create-possible-boards unscored-board empty-spaces new-markers)
                    scored-boards (map #(score-board space  % new-markers initial-marker) next-node-boards)]
                (if (= current-marker initial-marker)
                   (max-by-score scored-boards)
                   (min-by-score scored-boards))))))

(defn score-boards [spaces unscored-boards markers initial-marker]
  (map #(score-board %1  %2 markers initial-marker) spaces unscored-boards))

(defn ai-make-move [board markers]
  (let [empty-spaces (find-empty-spaces board)
        unscored-boards (create-possible-boards board empty-spaces markers)
        spaces-map (map #(hash-map :space %) empty-spaces)
        initial-marker (get-initial-marker markers)
        scored-boards-coll (score-boards spaces-map unscored-boards markers initial-marker)]

    (:space (max-by-score scored-boards-coll))))

