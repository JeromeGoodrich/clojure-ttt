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
  (let [size (int (Math/sqrt (count board)))
        rows (partition size board)]
    (has-winner? rows)))

(defn vertical-winner? [board]
  (let [size (int (Math/sqrt (count board)))
        cols (->> board
               (partition size)
               (apply interleave)
               (partition size))]
    (has-winner? cols)))

(defn diagonal-winner? [board]
  (let [size (int (Math/sqrt (count board)))
        diag-spaces1 (take size (iterate (partial + (+ size 1)) 0))
        diag-spaces2 (take size (iterate (partial + (- size 1)) (- size 1)))
        diag1 (map #(get board %) diag-spaces1)
        diag2 (map #(get board %) diag-spaces2)]
    (has-winner? [diag1 diag2])))

(defn tie-game? [board]
  (not-any? number? board))

(defn win-game? [board]
  (some #(% board) [horizontal-winner? vertical-winner? diagonal-winner?]))

(defn game-over? [board]
  (some #(% board) [horizontal-winner? vertical-winner? diagonal-winner? tie-game?]))

(defn pretty-row-to-string [row size]
  (let [last-space (nth row (dec size))
        other-spaces (pop row)]
     (apply str
      (apply str (map #(if (string? %) (str "_" % "_|") "___|") other-spaces))
      (if (string? last-space) (str "_" last-space "_\n") "___\n"))))

(defn row-to-string [row]
  (apply str (map #(format "%-3s" %) row)))

(defn pretty-last-row-to-string [last-row size]
  (let [last-space (nth last-row (dec size))
        other-spaces (pop last-row)]
    (apply str
      (apply str (map #(if (string? %) (str " " % " |") "   |") other-spaces))
      (if (string? last-space) (str " " last-space " \n") "   \n"))))
