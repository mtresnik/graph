package com.resnik.math.graph.tsp

import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.objects.Vertex
import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.verticesToEdges
import java.util.*

class PermutationTSP(vararg points: ArrayPoint) : TSP(*points) {

    class PermutationNode(val vertex: Vertex, distance: Double = 0.0, val parent: PermutationNode? = null) {

        private val totalDistance = (parent?.getTotalDistance() ?: 0.0) + distance
        private val size = (parent?.size() ?: 0) + 1

        operator fun contains(other: Vertex): Boolean {
            if (vertex.id == other.id) return true
            return parent?.contains(other) ?: false
        }

        fun size(): Int {
            return size
        }

        fun getTotalDistance(): Double {
            return totalDistance
        }

        fun toList(): MutableList<Vertex> {
            val ret = mutableListOf<Vertex>(vertex)
            if (parent == null) return ret
            ret.addAll(parent.toList())
            return ret
        }

    }

    override fun evaluate(): Path {
        // Get all possible start nodes (all points)
        val children = Stack<PermutationNode>()
        val numVertices = graph.storage.vertexStorage.size()
        val first = graph.storage.vertexStorage.first()
        // Doesn't matter where you start from in a TSP
        children.add(PermutationNode(first))

        val solutions = mutableListOf<PermutationNode>()
        var minDistance = Double.MAX_VALUE

        while (children.isNotEmpty()) {
            val currentPermutation = children.pop()
            val totalDistance = currentPermutation.getTotalDistance() + currentPermutation.vertex.distanceTo(first)
            if (totalDistance > minDistance) {
                continue
            }
            if (numVertices == currentPermutation.size()) {
                // found a solution, convert to path
                if (totalDistance <= minDistance) {
                    minDistance = totalDistance
                    solutions.add(currentPermutation)
                }
            } else {
                // Get next to add to permutation from current vertex
                val currentVertex = currentPermutation.vertex
                val nextEdges = currentVertex.edges.filter { it.to !in currentPermutation }
                nextEdges.forEach {
                    children.add(PermutationNode(it.to, it.getDistance(), currentPermutation))
                }
            }
        }
        val minSolution =
            solutions.minByOrNull { it.getTotalDistance() } ?: throw IllegalStateException("Distance was null!")
        return with(Path(verticesToEdges(minSolution.toList()))) { this.wrap(); this }
    }


}