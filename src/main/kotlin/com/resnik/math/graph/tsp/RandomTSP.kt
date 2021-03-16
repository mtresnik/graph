package com.resnik.math.graph.tsp

import com.resnik.math.Point
import com.resnik.math.graph.Edge
import com.resnik.math.graph.Path
import com.resnik.math.graph.Vertex

@ExperimentalStdlibApi
class RandomTSP(vararg points: Point, private val seedFunction: (Int) -> Int = { n -> defaultSeedFunction(n) }): TSP(*points)  {

    override fun evaluate(): Path {
        val ITERATIONS: Int = seedFunction.invoke(graph.vertices.size)
        var minDistance = Double.MAX_VALUE
        var minPath: Path? = null
        for(i in 0 until ITERATIONS){
            val path = randomPathOptimized()
            if(path.getDistance() < minDistance){
                minDistance = path.getDistance()
                minPath = path
            }
        }
        return minPath!!
    }

    fun randomPathOptimized(): Path {
        val maxVertex: Vertex = graph.vertices.maxBy { vertex -> vertex.edges.sumByDouble { edge -> edge.getDistance() } }!!
        val exploredEdges: MutableSet<Edge> = mutableSetOf()
        val toExplore: ArrayDeque<Vertex> = ArrayDeque(graph.vertices)
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
        return Path(exploredEdges)
    }

    companion object {
        fun defaultSeedFunction(n: Int): Int = n * n * 1000
    }

}