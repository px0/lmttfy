(ns lmttfy.db.core
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [lmttfy.db.schema :as schema]))

(defdb db schema/db-spec)

(defentity ticket)

(defn get-ticket [id]
  (select ticket (where {:id id})))

(defn has-key? [id]
  (not (empty? (get-ticket id))))

(defn insert-ticket [id Summary Description AssignedToUserID]
  (insert ticket (values {:id id
                          :Summary Summary
                          :Description Description
                          :AssignedToUserID AssignedToUserID})))

(comment
  (insert-ticket {:id "m" :Summary "Title" :Description "this is my text" :AssignedToUserID "Max"})

  )
