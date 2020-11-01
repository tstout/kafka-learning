(ns kafka-workbench.logging
  (:require [taoensso.timbre :as log]))

(defn config-logging!
  "Configure logging level according to environment"
  [env]
  {:pre [(#{:local :dev :prd :tst} env)]}
  (->
   env
   ({:local :debug
     :dev :debug
     :prd :info
     :tst :debug} :debug)
   log/set-level!)
  (log/merge-config! {:timestamp-opts {:pattern "yyyy-MM-dd HH:mm:ss.SS"}}))


(comment
  (config-logging! :local2)
  
  (config-logging! :dev)
  ;;
  )