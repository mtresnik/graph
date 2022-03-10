package com.resnik.math.graph.algorithms

import com.resnik.math.graph.objects.Graph
import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.objects.Vertex
import java.lang.IllegalStateException
import java.util.*
import kotlin.Comparator

class AStar(start: Vertex, dest: Vertex, graph: Graph) : GraphAlgorithm(start, dest, graph) {

    private class AStarVertex(val inner : Vertex, var f : Double = Double.MAX_VALUE) {
        var g : Double = 0.0
        var h : Double = 0.0

        object FComparator : Comparator<AStarVertex> {

            override fun compare(o1: AStarVertex?, o2: AStarVertex?): Int {
                if(o1 == null && o2 == null) return 0
                if(o1 != null && o2 != null) {
                    return o1.f.compareTo(o2.f)
                }
                throw IllegalStateException("One of the two is null here: $o1 comp $o2")
            }

        }

    }

    override fun evaluate(): Path {
        start.previous = start
        val open = PriorityQueue(AStarVertex.FComparator)
        open.add(AStarVertex(start, 0.0))
        while(open.isNotEmpty() && !hasVisited(dest)) {
            val parent = open.poll()
            if(parent.inner == dest) break
            parent.inner.edges.forEach { edge ->
                val successor = AStarVertex(edge.to)
                if(!hasVisited(successor.inner)) {
                    val g = parent.g + edge.getDistance()
                    val h = successor.inner.distanceTo(dest)
                    val f = g + h
                    if(f < successor.f) {
                        successor.g = g
                        successor.h = h
                        successor.f = f
                        successor.inner.previous = parent.inner
                        open.add(successor)
                        onVisit(successor.inner)
                    }
                }
            }
        }
        return backtrack(dest)
    }

}