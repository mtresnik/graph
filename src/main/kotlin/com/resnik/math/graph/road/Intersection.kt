package com.resnik.math.graph.road

import com.resnik.math.Point
import com.resnik.math.graph.Edge
import com.resnik.math.graph.Vertex
import com.resnik.math.haversine

class Intersection(val lat: Double, val lon: Double) : Vertex(lon, lat) {

    override fun distanceTo(other: Point): Double {
        if(other is Intersection){
            return haversine(lat, lon, other.lat, other.lon)
        }
        return super.distanceTo(other)
    }

    override fun connect(other: Vertex): Edge {
        if(other is Intersection){
            val road = Road(this, other)
            this.edges.add(road)
            return road
        }
        return super.connect(other)
    }

}