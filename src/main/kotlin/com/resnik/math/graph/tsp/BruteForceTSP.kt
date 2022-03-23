package com.resnik.math.graph.tsp

import com.resnik.math.graph.objects.Path
import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.permuteRecursive
import com.resnik.math.verticesToEdges

// O(n!)
@Deprecated("Abuses recursive stack calls.")
class BruteForceTSP(vararg points: ArrayPoint) : TSP(*points) {

    override fun evaluate(): Path =
        permuteRecursive(graph.storage.vertexStorage.toSet())
        .map { with(Path(verticesToEdges(it))){this.wrap(); this} }
        .minByOrNull { it.getDistance() }!!

}