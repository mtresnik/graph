package com.resnik.math.graph.tsp

import com.resnik.math.graph.objects.Edge
import com.resnik.math.graph.objects.Graph
import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.objects.Vertex
import com.resnik.math.linear.array.ArrayPoint

class GreedyTSP(vararg points: ArrayPoint, graph: Graph? = null) : TSP(*points, graph=graph) {

    override fun evaluate(): Path {
        val visited = mutableSetOf<Vertex>()
        val visitedEdges = mutableListOf<Edge>()
        val vertices = graph.storage.vertexStorage.toMutableList()
        val numVertices = vertices.size

        val startIndex = vertices.indices.random()
        val start = vertices[startIndex]
        var currentVertex = start
        visited.add(currentVertex)
        while(visited.size < numVertices) {
            val nextEdges = currentVertex.edges.filter { it.to !in visited }
            if(nextEdges.isEmpty()) {
                val currIndex = vertices.indices.first()
                currentVertex = vertices[currIndex]
                visited.clear()
                visitedEdges.clear()
            } else {
                val closestEdge = nextEdges.minByOrNull { it.getDistance() } ?: throw IllegalStateException("Distance was null")
                currentVertex = closestEdge.to
                visited.add(currentVertex)
                visitedEdges.add(closestEdge)
            }
        }
        return with(Path(visitedEdges)){this.wrap(); this}
    }

}