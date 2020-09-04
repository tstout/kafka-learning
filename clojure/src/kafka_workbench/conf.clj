(ns kafka-workbench.conf
  (:require [jackdaw.admin :as ja]))

(defn mk-client []
  (ja/->AdminClient {"bootstrap.servers" "stout-pi3:9092"}))


(comment
  (def client (mk-client))
  (ja/describe-cluster client)
  (ja/describe-topics client)
  (ja/list-topics client)
  @(ja/list-topics* client)
  (ja/get-broker-config client 0)
  (ja/create-topics!)



  ;;
  )