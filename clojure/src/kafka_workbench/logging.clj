(ns kafka-workbench.logging
  (:require [taoensso.timbre :as log]))


(defn config-logging! [env]
  (log/set-level!
   (->
    env
    ({:local :debug :dev :debug :prd :info :tst :debug} :debug)))
  (log/merge-config! {:timestamp-opts {:pattern "yyyy-MM-dd HH:mm:ss.SS"}}))