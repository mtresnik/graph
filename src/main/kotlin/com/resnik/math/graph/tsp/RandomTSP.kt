package com.resnik.math.graph.tsp

import com.resnik.math.graph.objects.Edge
import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.objects.Vertex
import com.resnik.math.linear.array.ArrayPoint

@ExperimentalStdlibApi
class RandomTSP(vararg points: ArrayPoint, private val seedFunction: (Int) -> Int = { n -> defaultSeedFunction(n) }) :
    TSP(*points) {

    override fun evaluate(): Path {
        val iterations: Int = seedFunction(graph.storage.vertexStorage.size())
        var minDistance = Double.MAX_VALUE
        var minPath: Path? = null
        for (i in 0 until iterations) {
            val path = randomPathOptimized()
            if (path.getDistance() < minDistance) {
                minDistance = path.getDistance()
                minPath = path
            }
        }
        return minPath!!
    }

    private fun randomPathOptimized(): Path {
        val maxVertex: Vertex =
            graph.storage.vertexStorage.maxByOrNull { vertex -> vertex.edges.sumOf { edge -> edge.getDistance() } }!!
        val exploredEdges: MutableSet<Edge> = mutableSetOf()
        val toExplore: ArrayDeque<Vertex> = ArrayDeque(graph.storage.vertexStorage.toList())
        val visited: MutableSet<Vertex> = mutableSetOf()
        var curr: Vertex = maxVertex
        toExplore.remove(curr)
        visited.add(curr)
        while (!toExplore.isEmpty()) {
            val currList: List<Edge> = curr.edges.toList()
            var edge: Edge = currList[0]
            while (exploredEdges.contains(edge) || visited.contains(edge.to)) {
                edge = currList[Math.floor(currList.size * Math.random()).toInt()]
            }
            curr = edge.to
            exploredEdges.add(edge)
            visited.add(curr)
            toExplore.remove(curr)
        }
        val retPath = Path(exploredEdges)
        retPath.wrap()
        return retPath
    }

    companion object {
        fun defaultSeedFunction(n: Int): Int = n * n * 2000
    }

}