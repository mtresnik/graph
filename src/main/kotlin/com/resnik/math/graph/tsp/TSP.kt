package com.resnik.math.graph.tsp

import com.resnik.math.graph.objects.ConnectedGraph
import com.resnik.math.graph.objects.Graph
import com.resnik.math.graph.objects.Traversal
import com.resnik.math.graph.objects.Vertex
import com.resnik.math.linear.array.ArrayPoint

abstract class TSP(vararg points: ArrayPoint, graph: Graph? = null) : Traversal {

    val graph: Graph = graph ?: ConnectedGraph(*points)

    fun isConsistent(from: Vertex, to: Vertex): Boolean {
        return isConsistent(graph, from, to)
    }

    fun isConsistent(baseGraph : Graph, from : Vertex, to : Vertex) : Boolean {
        if(from.edges.firstOrNull { edge -> edge.to == to } != null) return true
        // from : Vertex --> matching : Vertex
        val matching = baseGraph.storage.vertexStorage.firstOrNull { baseVertex ->
            baseVertex.values.contentEquals(from.values)
        } ?: return false
        // does (matching : Vertex) have a connection to a (to : Vertex) equivalent?
        val edgeToStart = matching.edges.firstOrNull { baseEdge ->
            baseEdge.to.values.contentEquals(to.values)
        } ?: return false
        return true
    }

}