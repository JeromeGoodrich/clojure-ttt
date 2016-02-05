(ns clojure-ttt.ai
  (:require [clojure-ttt.board :refer :all]
            [clojure.tools.cli :refer :all]))

(defn find-unmarked-spaces [board]
  (filter number? board))

(defn create-possible-boards [board spaces markers]
  (map #(mark-spot (first markers) % board) spaces))

(defn max-by-score [scored-boards]
  (apply max-key :score scored-boards))

(defn min-by-score [scored-boards]
  (apply min-key :score scored-boards))


(defn find-space-score [space board markers starting-marker depth depth-limit]
    (let [current-marker (first markers)]
     (cond
        (and (win-game? board)
             (= current-marker starting-marker)) (conj space {:score  (- 10 depth)})
        (and (win-game? board)
             (not (= current-marker starting-marker))) (conj space {:score (+ depth -10)})
        (tie-game? board) (conj space {:score 0})
        :else (let [new-markers (reverse markers)
                    depth (inc depth)
                    unmarked-spaces (find-unmarked-spaces board)
                    boards (create-possible-boards board unmarked-spaces new-markers)
                    spaces-with-scores (map #(find-space-score space % new-markers starting-marker depth depth-limit) boards)]
                (cond
                  (= depth depth-limit) (conj space {:score 0})
                  (not (= current-marker starting-marker)) (max-by-score spaces-with-scores)
                  (= current-marker starting-marker) (min-by-score spaces-with-scores))))))

(defn get-spaces-with-scores [spaces boards markers starting-marker depth depth-limit]
  (map #(find-space-score %1  %2 markers starting-marker depth depth-limit) spaces boards))

(defn ai-make-move [board markers depth-limit]
 (if (= (count board) (count (find-unmarked-spaces board)))
   8
   (let [unmarked-spaces (find-unmarked-spaces board)
         boards (create-possible-boards board unmarked-spaces markers)
         spaces (map #(hash-map :space %) unmarked-spaces)
         starting-marker (first markers)
         spaces-with-scores (get-spaces-with-scores spaces boards markers starting-marker 0 depth-limit)]
     (:space (max-by-score spaces-with-scores)))))

