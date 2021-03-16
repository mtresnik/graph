package com.resnik.math.graph.algorithms

import com.resnik.math.graph.Graph
import com.resnik.math.graph.Path
import com.resnik.math.graph.Vertex
import java.util.*

class Dijkstra(start: Vertex, dest: Vertex, graph: Graph) : GraphAlgorithm(start, dest, graph) {

    override fun evaluate(): Path {
        val shortestPathTree = TreeSet<Vertex>()
        graph.vertices.forEach {
            if(it == start){
                it.previous = start
                it.value = 0.0
            }else{
                it.previous = null
                it.value = Double.MAX_VALUE
            }
            shortestPathTree.add(it)
        }
        while(shortestPathTree.isNotEmpty()){
            val closestVertex= shortestPathTree.pollFirst()
            if(closestVertex == null || closestVertex.value == Double.MAX_VALUE)
                break
            closestVertex.edges.forEach {
                val neighbor = it.to
                val combinedDistance = closestVertex.value + it.getDistance()
                if(combinedDistance < neighbor.value){
                    shortestPathTree.remove(neighbor)
                    neighbor.value = combinedDistance
                    neighbor.previous = closestVertex
                    shortestPathTree.add(neighbor)
                }
            }
        }
        return backtrack(dest)
    }

}