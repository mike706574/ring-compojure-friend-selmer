(ns example.system
  (:require [cemerick.friend :as friend]
            [cemerick.friend.credentials :as creds]
            [cemerick.friend.workflows :as workflows]
            [compojure.core :refer [defroutes GET ANY]]
            [compojure.route :as route]
            [ring.middleware.defaults :as defaults]
            [example.service :as service]
            [selmer.parser :as parser]
            [taoensso.timbre :as log]))

(def users {"admin" {:username "admin"
                     :password (creds/hash-bcrypt "admin")
                     :roles #{::admin}}
            "mike" {:username "mike"
                    :password (creds/hash-bcrypt "mike")
                    :roles #{::user}}})

(derive ::admin ::user)

(defroutes routes
  (GET "/" req
       (friend/authorize
        #{::user}
        (let [name (:current (friend/identity req))]
          (parser/render-file "index.html" {:name name}))))

  (GET "/login" []
       (parser/render-file "login.html"
                           {:anti-forgery-token ring.middleware.anti-forgery/*anti-forgery-token*}))

  (friend/logout (ANY "/logout" request
                      {:status 302
                       :headers {"Location" "/login"}
                       :body ""}))

  (route/not-found "Not Found"))

(def handler
  (-> routes
      (friend/authenticate {:credential-fn (partial creds/bcrypt-credential-fn users)
                            :workflows [(workflows/interactive-form)]})
      (defaults/wrap-defaults defaults/site-defaults)))

(defn system
  [config]
  {:app (service/jetty-service config handler)})
