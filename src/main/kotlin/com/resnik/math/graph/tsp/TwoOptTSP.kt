package com.resnik.math.graph.tsp

import com.resnik.math.graph.objects.ConnectedGraph
import com.resnik.math.graph.objects.Edge
import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.objects.Vertex
import com.resnik.math.linear.array.ArrayPoint
import java.util.*

class TwoOptTSP(vararg points: ArrayPoint, graph: ConnectedGraph? = null) : TSP(*points, graph = graph) {

    fun originalCost(first: Edge, second: Edge): Double {
        return first.getDistance() + second.getDistance()
    }

    fun costOfSwap(first: Edge, second: Edge): Double {
        return first.from.distanceTo(second.to) + second.from.distanceTo(first.to)
    }

    fun shouldSwap(first: Edge, second: Edge): Boolean {
        // Shouldn't cause a self swap thing
        // if(first.to == second.from || first.from == second.to) return false
        return costOfSwap(first, second) < originalCost(first, second) || intersects(first, second)
    }

    // in-place swap
    fun swap(first: Edge, second: Edge) {
        val temp = first.to
        first.to = second.to
        second.to = temp
    }

    fun cyclePath(path: Path) {
        val toAdd = path.removeAt(0)
        path.add(toAdd)
    }

    fun getIntersectionPairs(currentTour: Path): List<Pair<Edge, Edge>> {
        val retList = mutableListOf<Pair<Edge, Edge>>()
        (0..currentTour.lastIndex).forEach { firstIndex ->
            ((firstIndex + 1)..currentTour.lastIndex).forEach { secondIndex ->
                if (firstIndex != secondIndex) {
                    val firstEdge = currentTour[firstIndex]
                    val secondEdge = currentTour[secondIndex]
                    if (intersects(firstEdge, secondEdge)) {
                        retList.add(firstEdge to secondEdge)
                    }
                }
            }
        }
        return retList
    }

    fun isBroken(currentTour: Path): Boolean {
        if (currentTour.first().from != currentTour.last().to) return true
        val numVertices = currentTour.getUniqueVertices().size
        // Use dfs to navigate graph
        val unvisited = Stack<Vertex>()
        unvisited.add(currentTour.first().from)
        val visited = mutableSetOf<Vertex>()
        while (unvisited.isNotEmpty()) {
            val currVertex = unvisited.pop()
            visited.add(currVertex)
            currVertex.edges.forEach { edge ->
                if (edge.to !in visited) {
                    unvisited.add(edge.to)
                }
            }
        }
        return numVertices != visited.size
    }

    fun intersects(first: Edge, second: Edge): Boolean {
        if (first.to == second.from && first.from == second.to) return false
        return first.intersects(second)
    }

    fun getBestCycled(originalTour: Path): Path {
        var minDistance = originalTour.getDistance()
        var minTour = originalTour
        var currentTour = originalTour.clone()
        val intersectionPairs = Stack<Pair<Edge, Edge>>()
        intersectionPairs.addAll(getIntersectionPairs(currentTour))
        var changed = true
        while (intersectionPairs.isNotEmpty() && changed) {
            changed = false
            while (intersectionPairs.isNotEmpty()) {
                val currIntersection = intersectionPairs.pop()
                val (first, second) = currIntersection
                swap(first, second)
                if (isBroken(currentTour)) {
                    swap(first, second)
                } else {
                    changed = true
                    break
                }
            }
            val currentDistance = currentTour.getDistance()
            if (currentDistance < minDistance) {
                minDistance = currentDistance
                minTour = currentTour
            }
            currentTour = currentTour.clone()
            intersectionPairs.clear()
            intersectionPairs.addAll(getIntersectionPairs(currentTour))
        }
        return minTour
    }

    override fun evaluate(): Path {
        // First find a tour using another TSP approximation
        val originalTour = GreedyTSP(graph = graph).evaluate()
        var currentTour = getBestCycled(originalTour.clone())

        var minDistance = originalTour.getDistance()
        var minTour = originalTour

        repeat((originalTour.size * originalTour.size).coerceAtMost(100)) { cycleNumber ->
            cyclePath(currentTour)
            currentTour = getBestCycled(currentTour)
            val currentDistance = currentTour.getDistance()
            if (currentDistance < minDistance) {
                minDistance = currentDistance
                minTour = currentTour
            }
            currentTour = currentTour.clone()
        }

        return minTour
    }

}