(ns clojure-ttt.board)

(defn create-board [size]
  (vec (range (* size size))))

(defn mark-spot [marker spot board]
    (assoc board spot marker))

(defn is-won? [row]
  (apply = row))

(defn- has-winner? [lines]
  (some true? (map is-won? lines)))

(defn horizontal-winner? [board]
  (let [rows (partition 3 board)]
    (has-winner? rows)))

(defn vertical-winner? [board]
  (let [cols (->> board
               (partition 3)
               (apply interleave)
               (partition 3))]
    (has-winner? cols)))

(defn diagonal-winner? [board]
  (let [diag1 (map #(get board %) [0 4 8])
        diag2 (map #(get board %) [2 4 6])]
    (has-winner? [diag1 diag2])))

(defn tie-game? [board]
  (not-any? number? board))

(defn win-game? [board]
  (some #(% board) [horizontal-winner? vertical-winner? diagonal-winner?]))

(defn game-over? [board]
  (some #(% board) [horizontal-winner? vertical-winner? diagonal-winner? tie-game?]))
