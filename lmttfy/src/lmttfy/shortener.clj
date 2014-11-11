(ns lmttfy.shortener
  (:require [lmttfy.db.core :as db]))

(defn random-string [length]
  (let [ascii-codes (concat (range 48 58) (range 65 91) (range 97 123))]
    (apply str (repeatedly length #(char (rand-nth ascii-codes)))))) 

(defn hash-string [] (random-string 8))

(defn find-unique-string []
  (let [hash (hash-string)]
    (if-not (db/has-key? hash)
      hash
      (find-unique-string))))

(defn create-ticket [Title Description AssignedToUserID]
  (let [id (find-unique-string)]
    (db/insert-ticket id Title Description AssignedToUserID)
    id))
