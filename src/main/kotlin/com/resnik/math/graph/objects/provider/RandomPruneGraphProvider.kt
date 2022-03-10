package com.resnik.math.graph.objects.provider

import com.resnik.math.graph.objects.Graph
import com.resnik.math.graph.storage.objects.graph.GraphStorage

class RandomPruneGraphProvider(val baseGraph : Graph, percent : Double) : GraphProvider {

    private val percent : Double

    init {
        this.percent = percent.coerceIn(0.0, 1.0)
    }

    override fun build(): Graph {
        val randomVertices = baseGraph.storage.vertexStorage.shuffled().toMutableList()
        val remains = randomVertices.take((randomVertices.size * (1.0 - percent)).toInt())
        val retStorage = GraphStorage()
        retStorage.vertexStorage.saveAll(remains)
        retStorage.vertexStorage.forEach { vertex ->
            val newEdges = vertex.edges.filter { edge ->
                val to = edge.to
                if(to.id != null)
                    retStorage.vertexStorage.contains(to.id!!)
                else
                    retStorage.vertexStorage.contains(to)
            }
            vertex.edges.clear()
            vertex.edges.addAll(newEdges)
        }
        retStorage.edgeStorage.saveAll(retStorage.vertexStorage.flatMap { vertex -> vertex.edges })
        val filtered = retStorage.vertexStorage.filter { it.edges.isNotEmpty() }
        retStorage.vertexStorage.clear()
        retStorage.vertexStorage.saveAll(filtered)
        return Graph(storage = retStorage)
    }

}