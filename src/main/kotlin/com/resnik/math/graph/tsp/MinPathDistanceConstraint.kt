package com.resnik.math.graph.tsp

import com.resnik.intel.csp.constraint.local.MinimumConstraint
import com.resnik.math.graph.objects.Vertex

class MinPathDistanceConstraint(variables: List<Int>) : MinimumConstraint<Int, Vertex>(variables) {

    override fun evaluate(assignment: Map<Int, Vertex>): Double {
        return assignment.values.zipWithNext().sumOf { (v1, v2) -> v1.distanceTo(v2) }
    }

}