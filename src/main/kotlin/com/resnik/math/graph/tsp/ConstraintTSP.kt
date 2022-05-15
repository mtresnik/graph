package com.resnik.math.graph.tsp

import com.resnik.intel.csp.CSPFactory
import com.resnik.intel.csp.constraint.global.GlobalAllDiff
import com.resnik.math.graph.objects.Graph
import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.objects.Vertex
import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.verticesToEdges

@Deprecated("Adds unneeded memory overhead")
class ConstraintTSP(vararg points: ArrayPoint, graph: Graph? = null) : TSP(*points, graph = graph) {

    override fun evaluate(): Path {
        // Create a CSP for this TSP using the constraint
        val vertices = this.graph.storage.vertexStorage.toList()
        val indices = vertices.indices.toList()
        val domain = mutableMapOf<Int, List<Vertex>>()
        vertices.forEachIndexed { index, _ -> domain[index] = vertices }
        val allDiff = GlobalAllDiff<Int, Vertex>()
        val minimumConstraint = MinPathDistanceConstraint(indices)
        val csp = CSPFactory.createCSP(domain)
        println(csp.javaClass)
        csp.addConstraint(minimumConstraint)
        csp.addConstraint(allDiff)
        val solutions = csp.findAllSolutions()
        val assignment = solutions.firstOrNull() ?: throw IllegalStateException("No solution was found for this TSP.")
        val ordered = assignment.values.toList()
        return Path(verticesToEdges(ordered))
    }

}