(ns lmttfy.routes.home
  (:require [compojure.core :refer :all]
            [lmttfy.layout :as layout]
            [lmttfy.shortener :as shortener]
            [lmttfy.db.core :as db]
            [ring.util.codec :refer [form-encode]]
            [lmttfy.util :as util]))

(def baseurl "http://lmttfy.klick.com/")
(def genomeurl "http://genome.klick.com/tasks/ticket/create")
(defn home-page []
  (println "home-page")
  (layout/render "index.html" {}))

(defn post-ticket [Title Description AssignedToUserID]
  (let [id (shortener/create-ticket Title Description AssignedToUserID)]
    (println "successfully created ticket with hash" id)
    (layout/render "index.html" {:created true
                                 :link (str baseurl id)
                                 :Title Title
                                 :Description (clojure.string/replace Description #"\r?\n" "\\\\n")
                                 :AssignedToUserID AssignedToUserID
                                 })))

(defn remove-nil [m]
  (into {} (remove #(nil? (val %)) m)))


(defn map->querystring [m]
  (str \?
       (form-encode m)))

(defn genome-query-link [m]
  (str genomeurl (map->querystring (remove-nil {:Title (m :title)
                                                :Description (if (nil? (m :description))
                                                               ""
                                                               (clojure.string/replace (m :description) #"\r?\n" "<br/>")) 
                                                :AssignedToUserID (m :assignedtouserid)}))))

(defn render-short-url [hash]
  (let [[ticket] (db/get-ticket hash)]
    (if-let [{:keys [title description assignedtouserid]} ticket]
      (do
        (layout/render "index.html" {:show true
                                     :genomelink (genome-query-link ticket)
                                     :Title title
                                     :Description (clojure.string/replace description #"\r?\n" "\\\\n")
                                     :AssignedToUserID assignedtouserid
                                     }))
      "Not found")))

(defroutes home-routes
  (GET "/" [] (home-page))
  (POST "/" [Title Description AssignedToUserID] (post-ticket Title Description AssignedToUserID))
  (GET "/:hash" [hash] (render-short-url hash)))
