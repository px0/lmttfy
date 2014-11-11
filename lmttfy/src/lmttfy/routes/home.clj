(ns lmttfy.routes.home
  (:require [compojure.core :refer :all]
            [lmttfy.layout :as layout]
            [lmttfy.shortener :as shortener]
            [lmttfy.db.core :as db]
            [lmttfy.util :as util]))

(def baseurl "http://lmttfy.klick.com/")

(defn home-page []
  (println "home-page")
  (layout/render "index.html" {}))

(defn post-ticket [Title Description AssignedToUserID]
  (println "post-ticket")
  (println "Dsc:" )
  (>pprint Description)
  (let [id (shortener/create-ticket Title Description AssignedToUserID)]
    (println "successfully created ticket with hash" id)
    (layout/render "index.html" {:created true
                                 :link (str baseurl id)
                                 :Title Title
                                 :Description (clojure.string/replace Description #"\r?\n" "\\\\n")
                                 :AssignedToUserID AssignedToUserID
                                 })))

(defn render-short-url [hash]
  (let [[ticket] (db/get-ticket hash)]
    (if-let [{:keys [summary description assignedtouserid]} ticket]
      (do
        (>pprint ticket)
        (layout/render "index.html" {:show true
                                     :link "http://lmttfy.klick.com/12345678"
                                     :Title summary
                                     :Description description
                                     :AssignedToUserID assignedtouserid
                                     }))
      "Not found")))

(defroutes home-routes
  (GET "/" [] (home-page))
  (POST "/" [Title Description AssignedToUserID] (post-ticket Title Description AssignedToUserID))
  (GET "/:hash" [hash] (render-short-url hash)))
