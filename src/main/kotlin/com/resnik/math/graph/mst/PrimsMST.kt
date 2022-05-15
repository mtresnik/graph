package com.resnik.math.graph.mst

import com.resnik.math.graph.objects.Edge
import com.resnik.math.graph.objects.Graph
import com.resnik.math.graph.objects.Vertex

class PrimsMST(graph: Graph) : MST(graph) {

    override fun build(): Graph {
        val retGraph = Graph()

        val cheapestVertexCost = mutableMapOf<Vertex, Double>()
        val edgeProvidingConnection = mutableMapOf<Vertex, Edge?>()
        val notVisited = this.graph.storage.vertexStorage.toMutableList()
        notVisited.forEach { vertex ->
            cheapestVertexCost[vertex] = Double.POSITIVE_INFINITY
            edgeProvidingConnection[vertex] = null
        }

        val edgeForest = mutableListOf<Edge>()

        while (notVisited.isNotEmpty()) {
            val toRemoveIndex = notVisited.indices.minByOrNull { index ->
                val vertex = notVisited[index]
                cheapestVertexCost[vertex] ?: Double.POSITIVE_INFINITY
            } ?: notVisited.indices.random()

            val removed = notVisited.removeAt(toRemoveIndex)
            val edgeRemoved = edgeProvidingConnection[removed]
            if (edgeRemoved != null) edgeForest.add(edgeRemoved)

            removed.edges.forEach { edge ->
                if (edge.to in notVisited) {
                    val weight = (edge.getDistance() + 1) * edge.weight.coerceAtLeast(1.0)
                    if (weight < (cheapestVertexCost[edge.to] ?: Double.POSITIVE_INFINITY)) {
                        cheapestVertexCost[edge.to] = weight
                        edgeProvidingConnection[edge.to] = edge
                    }
                }
            }
        }

        edgeForest.forEach { edge ->
            val cloned = edge.clone()
            retGraph.storage.edgeStorage.save(cloned)
            cloned.from.edges.add(cloned)
        }

        return retGraph
    }

}