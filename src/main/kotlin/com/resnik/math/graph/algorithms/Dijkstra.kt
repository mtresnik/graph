package com.resnik.math.graph.algorithms

import com.resnik.math.graph.algorithms.cost.DefaultVertexWrapper
import com.resnik.math.graph.objects.Path
import java.util.*

class Dijkstra(parameters: GraphAlgorithmParameterInterface) : GraphAlgorithm<DefaultVertexWrapper>(parameters) {

    override fun evaluate(): Path {
        val startWrapper = DefaultVertexWrapper(getStart(), 0.0)
        val destWrapper = DefaultVertexWrapper(getDestination())
        startWrapper.previous = startWrapper
        val shortestPathTree = PriorityQueue<DefaultVertexWrapper>()
        shortestPathTree.add(startWrapper)
        while (shortestPathTree.isNotEmpty() && !hasVisited(getDestination())) {
            val closestVertex = shortestPathTree.poll()
            if (closestVertex == null || closestVertex.state.cost.total == Double.MAX_VALUE || closestVertex == destWrapper)
                break
            closestVertex.inner.edges.forEach { edge ->
                val successor = if (edge.to == getDestination()) destWrapper else DefaultVertexWrapper(edge.to)
                if (!hasVisited(successor)) {
                    val combinedDistance = closestVertex.state.cost.total + edge.getDistance()
                    if (combinedDistance < successor.state.cost.total) {
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