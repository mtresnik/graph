package com.resnik.math.graph.algorithms

import com.resnik.math.graph.algorithms.cost.CostMetricTotalComparator
import com.resnik.math.graph.algorithms.cost.VertexWrapper
import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.objects.Vertex
import java.util.*

class AStar(parameters : GraphAlgorithmParameterInterface) : GraphAlgorithm<AStar.AStarVertexWrapper>(parameters) {

    class AStarVertexWrapper(inner : Vertex, defaultCost : Double = Double.MAX_VALUE)
        : VertexWrapper<AStarVertexWrapper>(inner, defaultCost) {
        var g : Double = 0.0
        override fun compareTo(other: AStarVertexWrapper): Int {
            return CostMetricTotalComparator.compare(this.state.cost, other.state.cost)
        }
    }

    override fun evaluate(): Path {
        val start = getStart()
        val dest = getDestination()
        val startWrapper = AStarVertexWrapper(start, 0.0)
        val destWrapper = AStarVertexWrapper(dest)
        startWrapper.previous = startWrapper
        val open = PriorityQueue<AStarVertexWrapper>()
        open.add(startWrapper)
        while(open.isNotEmpty() && !hasVisited(dest)) {
            val parent = open.poll()
            if(parent.inner == dest) break
            parent.inner.edges.forEach { edge ->
                val successor = if(edge.to == dest) destWrapper else AStarVertexWrapper(edge.to)
                if(!hasVisited(successor)) {
                    val g = parent.g + edge.getDistance()
                    val h = successor.inner.distanceTo(dest)
                    val cost = g + h
                    if(cost < successor.state.cost.total) {
                        successor.g = g
                        successor.state.cost.total = cost
                        successor.previous = parent
                        open.add(successor)
                        onVisit(successor)
                    }
                }
            }
        }
        return backtrack(destWrapper)
    }

}