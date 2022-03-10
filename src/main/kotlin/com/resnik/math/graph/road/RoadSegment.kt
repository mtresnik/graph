package com.resnik.math.graph.road

import com.resnik.math.graph.objects.Edge

class RoadSegment(int1: Intersection, int2: Intersection, val speedLimit: Double = 15.0) : Edge(int1, int2) {

    fun getTime(): Double = getDistance() / speedLimit

}