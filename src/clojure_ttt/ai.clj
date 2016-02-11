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


;node - consists of board and space and score

;defn (alpha-beta [node depth alpha beta maximizing-player? depth-limit]
; (if (game-over?
;  (find-score space board)
;  (if (= current-marker starting-marker)
;    (let [score -infinity
;    (generate child nodes - find unmarked spaces on current node and mark those spaces return the parent node space and the newly marked boards
;    this gives us a collection of child nodes how to call recur on each of these?
;    score = max between score and (recur child-node depth alpha beta FALSE depth limit)
;    alpha = max between (alpha and score)
;    if beta <= alpha
;    return value

;    this presents several problems for tail recursion because in order to alter alpha we need to have already got the score for a node.
;    SO how to get around this?
;    "the trick is to turn the for-each with break into a recursion with an appropriate base case"
;    So in this case it's for each child node. we start off with a collection of nodes we get the score from each one and compare it to alpha/beta
;    and then set the new score and new alpha/betas accordingly. We stop when there's nothing left in the collection ie when there are no more nodes at that level.
;   (loop [coll-of-nodes coll-of-nodes
;          score score
;          alpha alpha]
;   base-case = when (coll-of-nodes is empty)
;   (let node (first coll-of-nodes)
;        new-score (max (find-score(node) score))
;   (recur (rest coll-of-nodes) (new-score) ((max (new-score alpha)

(defn alphabeta [node alpha beta maximizing-player?]
  (if game-over?
    (get-score node)
    (if (maximizing-player?)
      (let [child-nodes (get-child-nodes node)]
        (loop [child-nodes child-nodes
               score score
               alpha alpha]
          (if (empty? child-nodes))




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
