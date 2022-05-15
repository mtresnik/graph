package com.resnik.math.graph.mst

import com.resnik.math.graph.objects.Edge
import com.resnik.math.graph.objects.Graph
import com.resnik.math.graph.objects.Vertex

class KruskalsMST(graph: Graph) : MST(graph) {

    override fun build(): Graph {
        val retGraph = Graph()
        val allSets = mutableListOf<MutableSet<Vertex>>()
        val sortedEdges = mutableListOf<Edge>()
        this.graph.storage.vertexStorage.forEach { vertex ->
            sortedEdges.addAll(vertex.edges.filter { it !in sortedEdges })
            allSets.add(mutableSetOf(vertex))
        }
        val validEdges = mutableSetOf<Edge>()
        sortedEdges.sortBy { (it.getDistance() + 1) * it.weight.coerceAtLeast(1.0) }
        while (sortedEdges.isNotEmpty() && allSets.size > 1) {
            val currentEdge = sortedEdges.removeFirstOrNull() ?: break
            val fromIndex = allSets.indexOfFirst { set -> currentEdge.from in set }
            if (fromIndex != -1) {
                val fromSet = allSets.removeAt(fromIndex)
                val toIndex = allSets.indexOfFirst { set -> currentEdge.to in set }
                if (toIndex != -1) {
                    val toSet = allSets.removeAt(toIndex)
                    val joined = mutableSetOf<Vertex>()
                    joined.addAll(fromSet)
                    joined.addAll(toSet)
                    allSets.add(joined)
                    validEdges.add(currentEdge)
                } else {
                    allSets.add(fromSet)
                }

            }
        }
        if (allSets.size != 1) {
            println("Graph is disjoint!")
        }
        validEdges.forEach { edge ->
            val cloned = edge.clone()
            retGraph.storage.edgeStorage.save(cloned)
            cloned.from.edges.add(cloned)
        }
        return retGraph
    }

}