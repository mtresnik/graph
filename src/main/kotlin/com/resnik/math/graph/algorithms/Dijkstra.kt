package com.resnik.math.graph.algorithms

import com.resnik.math.graph.objects.Graph
import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.objects.Vertex
import java.util.*

class Dijkstra(start: Vertex, dest: Vertex, graph: Graph) : GraphAlgorithm(start, dest, graph) {

    override fun evaluate(): Path {
        val shortestPathTree = PriorityQueue(Vertex.ValueComparator)
        graph.storage.vertexStorage.forEach {
            if(it == start){
                it.previous = start
                it.value = 0.0
            } else {
                it.previous = null
                it.value = Double.MAX_VALUE
            }
        }
        shortestPathTree.add(start)
        while(shortestPathTree.isNotEmpty() && !hasVisited(dest)){
            val closestVertex= shortestPathTree.poll()
            if(closestVertex == null || closestVertex.value == Double.MAX_VALUE)
                break
            closestVertex.edges.forEach {
                val neighbor = it.to
                if(!hasVisited(neighbor)) {
                    val combinedDistance = closestVertex.value + it.getDistance()
                    if(combinedDistance < neighbor.value){
                        shortestPathTree.remove(neighbor)
                        neighbor.value = combinedDistance
                        neighbor.previous = closestVertex
                        shortestPathTree.add(neighbor)
                        onVisit(neighbor)
                    }
                }
            }
        }
        return backtrack(dest)
    }

}