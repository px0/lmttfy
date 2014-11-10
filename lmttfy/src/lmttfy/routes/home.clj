(ns lmttfy.routes.home
  (:require [compojure.core :refer :all]
            [lmttfy.layout :as layout]
            [lmttfy.util :as util]))

(defn home-page []
  (println "home-page")
  (layout/render "index.html" {}))

(defroutes home-routes
  (GET "/" [] (home-page)))
