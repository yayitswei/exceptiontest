(ns exceptiontest.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.handler :as handler]))

(defroutes app-routes
  (GET "/" []
       (assert false)
       "*ok*"))

(defn wrap-exception [f]
  (fn [request]
    (try (f request)
         (catch Exception e
           (.printStackTrace e)
           {:status 500 :body "We're sorry, something went wrong."}))))

(def app
  (-> app-routes wrap-exception handler/site))

(def port 3000)

(defn -main [& args]
  (server/run-server app {:port port :join? false})
  (println "Server listening on port" port))
