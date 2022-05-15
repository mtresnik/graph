package com.resnik.math.graph.tsp

import com.resnik.math.graph.mst.PrimsMST
import com.resnik.math.graph.objects.ConnectedGraph
import com.resnik.math.graph.objects.Edge
import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.objects.Vertex
import com.resnik.math.linear.array.ArrayPoint

/*
* len(path) <= 2 * len(path*)
* O(n^2 + prims)
* */
class GreedyTwiceAroundTSP(vararg points: ArrayPoint, graph: ConnectedGraph? = null) : TSP(*points, graph = graph) {

    override fun evaluate(): Path {
        val baseGraph = this.graph
        val mst = PrimsMST(baseGraph).build()
        // Duplicate edges of mst
        val toSave = mutableSetOf<Edge>()
        mst.storage.edgeStorage.forEach { edge ->
            // find if reverse exists in to
            val reversed = edge.reverse()
            val matching = edge.to.edges.firstOrNull { other -> other.to == reversed.to } != null
            if (!matching) {
                toSave.add(reversed)
            }
        }
        mst.storage.edgeStorage.saveAll(toSave)
        // Find an Eulerian tour of mst using DFS
        val vertices = graph.storage.vertexStorage.toMutableList()
        val startIndex = vertices.indices.random()
        val startVertex = vertices[startIndex]
        var currVertex = startVertex
        val visited = mutableListOf<Edge>()
        val visitedVertices = mutableSetOf<Vertex>()
        visitedVertices.add(startVertex)
        val expectedSize = mst.storage.edgeStorage.toSet().size
        while (visited.size != expectedSize) {
            visitedVertices.add(currVertex)
            val toVisit = currVertex.edges.filter { it !in visited }.sortedBy { it.getDistance() }
            if (toVisit.isEmpty()) {
                // Possible error here... must not have initialized graph properly if this happens
                if (isConsistent(baseGraph, currVertex, startVertex)) {
                    // Only add new edge here if it's consistent with base graph...
                    visited.add(Edge(currVertex, startVertex))
                }
                break
            }
            // Super shortcut
            if (visitedVertices.size == vertices.size) {
                // Attempt to connect current node and start
                if (isConsistent(baseGraph, currVertex, startVertex)) {
                    visited.add(Edge(currVertex, startVertex))
                    break
                }
            }

            // Prioritize exploration here
            val nonVisitedConnecting = toVisit.filter { it.to !in visitedVertices }.sortedBy { it.getDistance() }
            val chosenEdge: Edge = nonVisitedConnecting.firstOrNull() ?: toVisit.first()
            visited.add(chosenEdge)
            currVertex = chosenEdge.to
        }

        val retEdges = visitedVertices.zipWithNext { from, to -> Edge(from, to) }.toMutableList()
        return with(Path(retEdges)) { this.wrap(); this }
        /*
        * This is always true for a fully Connected Graph, where you can just consider the first occurrence.
        * */
//        if(visitedVertices.zipWithNext { a, b ->  a to b}.all { (from, to) -> isConsistent(baseGraph, from, to)}) {
//            if(isConsistent(baseGraph, visitedVertices.last(), startVertex)) {
//
//            }
//        }
    }

}