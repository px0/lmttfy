(ns lmttfy.db.core
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [lmttfy.db.schema :as schema]))

(defdb db schema/db-spec)

(defentity ticket)

(defn has-key? [id]
  (not (empty? (get-ticket id))))

(defn insert-ticket [{:keys [id Summary Description AssignedToUserID] :as tkt} ]
  (insert ticket (values {:id id
                          :Summary Summary
                          :Description Description
                          :AssignedToUserID AssignedToUserID})))

(defn get-ticket [id]
  (select ticket (where {:id id})))

(comment
  (insert-ticket {:id "m" :Summary "Title" :Description "this is my text" :AssignedToUserID "Max"})

  )
