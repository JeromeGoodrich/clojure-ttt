(ns clojure-ttt.cli-spec
 (require [speclj.core :refer :all]
          [clojure-ttt.cli :refer :all]))

(defn mock-exit [status message]
  status)

(describe "validate cli"
  (context "user asks for help"
    (it " exits with a status of 0"
      (with-redefs [exit mock-exit]
      (should= 0 (validate-cli {:help true} nil nil nil)))))

  (context "user doesn't supply any arguments"
    (it "exits with a status of 1"
      (with-redefs [exit mock-exit]
        (should= 1 (validate-cli nil nil nil nil)))))

  (context "user supplies too many arguments"
    (it "exits with a status of 1"
      (with-redefs [exit  mock-exit]
       (should= 1 (validate-cli nil ["me-first" "head-to-head"] nil nil)))))

  (context "user supplies identical game markers"
    (it "exits with a status of 1"
      (with-redefs [exit  mock-exit]
       (should= 1 (validate-cli {:player1 "X" :player2 "X"} nil nil nil)))))

  (context "user has errors in their input"
    (it "exits with a status of 1"
      (with-redefs [exit  mock-exit]
       (should= 1 (validate-cli nil nil nil ["This is an error"])))))

  (context "user inputs an unsupported argument"
    (it "exits with a status of 1"
      (with-redefs [exit  mock-exit]
       (should= 1 (validate-cli nil ["me-too"] nil nil))))))









