(ns me.jdarby.example
  (:gen-class
    :methods [#^{:static true} [hello [java.lang.String] java.lang.String]]))

(defn -hello [name] (str "hello " name))