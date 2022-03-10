package com.resnik.math.graph.algorithms

import com.resnik.math.graph.algorithms.cost.DefaultVertexWrapper
import com.resnik.math.graph.objects.Graph
import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.objects.Vertex
import java.util.*

class Dijkstra(start: Vertex, dest: Vertex) : GraphAlgorithm<DefaultVertexWrapper>(start, dest) {

    override fun evaluate(): Path {
        val startWrapper = DefaultVertexWrapper(start, 0.0)
        val destWrapper = DefaultVertexWrapper(dest)
        startWrapper.previous = startWrapper
        val shortestPathTree = PriorityQueue<DefaultVertexWrapper>()
        shortestPathTree.add(startWrapper)
        while(shortestPathTree.isNotEmpty() && !hasVisited(dest)){
            val closestVertex= shortestPathTree.poll()
            if(closestVertex == null || closestVertex.state.cost.total == Double.MAX_VALUE || closestVertex == destWrapper)
                break
            closestVertex.inner.edges.forEach { edge ->
                val successor = if(edge.to == dest) destWrapper else DefaultVertexWrapper(edge.to)
                if(!hasVisited(successor)) {
                    val combinedDistance = closestVertex.state.cost.total + edge.getDistance()
                    if(combinedDistance < successor.state.cost.total){
                        shortestPathTree.remove(successor)
                        successor.state.cost.total = combinedDistance
                        successor.previous = closestVertex
                        shortestPathTree.add(successor)
                        onVisit(successor)
                    }
                }
            }
        }
        return backtrack(destWrapper)
    }

}