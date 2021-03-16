package com.resnik.math.graph.tsp

import com.resnik.math.Point
import com.resnik.math.verticesToEdges
import com.resnik.math.graph.Path
import com.resnik.math.permuteRecursive

// O(n!)
class BruteForceTSP(vararg points: Point) : TSP(*points) {

    override fun evaluate(): Path =
        permuteRecursive(graph.vertices)
        .map { Path(verticesToEdges(it)) }
        .minBy { it.getDistance() }!!

}