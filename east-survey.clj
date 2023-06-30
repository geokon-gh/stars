(ns
    east-survey
  "plot of cave locations"
  (:use geoprim
        geogrid
        geogrid2svg
        geogrid4image
        geogrid4seq
        svgmaps)
  (:require [svgmaps]
            [geojson2svg]
            [quickthing]
            (thi.ng.geom
              [core                          :as geom]
              [matrix                        :as matrix])
            #_'[thi.ng.color.core]
            [clj-curl.easy                   :as curl-easy]
            [clj-curl.opts                   :as curl-opts]
            [thi.ng.geom.viz.core            :as viz]
            [thi.ng.geom.svg.core            :as svg]))

(def
  shoreline-filestr
  "A shoreline file"
  #_
  "./data/shoreline-coarse.json"
  "/home/kxygk/Data/shoreline/GSHHG/shoreline.json")

(def
  khanon-region
  (region
    (point
      10.0
      #_
      9.5
      99.0)
    (point
      9.0
      100.5
      #_
      100.0)))


(def
  caves
  [[9.204618418631329
    99.7741429933002]
   [9.239590
    99.801338]
   [9.31203961279708
    99.78394269229388]
   [9.228682
    99.837597]
   [9.232862
    99.866472]
   [9.201878
    99.774613]
   [9.093424
    99.782259]
   [9.089118
    99.523652]
   [9.122999
    99.696184]
   [9.271773
    99.789355]
   [9.177586
    99.769663]
   [9.178211
    99.764364]
   [9.120318
    99.712254]])

(mapv
  #(apply
     point
     %)
  caves)
(def
  cave
  (point
    8.100833
    98.984722))

(defn
  shoreline-map
  "Given a region and vector of geopoints(POIS)
  Draw a simple shoreline map"
  [region
   pois]
  (->
    (svg/group
      {}
      (svgmaps/latlon-axis ;; draws lat/lon axis
        region)
      (geojson2svg/read-file
        shoreline-filestr
        region)
      (svgmaps/points-of-interest
        pois
        region
        {:fill "none"
         :stroke "purple"
         :r 0.02
         :stroke-width 0.001}))
    (quickthing/set-display-width
      1000
      (dimension
        region))
    #_quickthing/serialize-with-line-breaks))

(spit
  "map.svg"
  (quickthing/serialize-with-line-breaks
    (shoreline-map
      khanon-region
      (mapv
        #(apply
           point
           %)
        caves))))
