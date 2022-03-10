package com.resnik.math.graph.tsp

import com.resnik.math.verticesToEdges
import com.resnik.math.graph.objects.Path
import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.permuteRecursive

// O(n!)
class BruteForceTSP(vararg points: ArrayPoint) : TSP(*points) {

    override fun evaluate(): Path =
        permuteRecursive(connectedGraph.storage.vertexStorage.toSet())
        .map { Path(verticesToEdges(it)) }
        .minByOrNull { it.getDistance() }!!

}