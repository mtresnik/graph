package com.resnik.math.graph.algorithms

import com.resnik.math.graph.objects.Vertex

open class GAParams(private val start: Vertex, private val destination: Vertex) : GraphAlgorithmParameterInterface {

    constructor(other: GraphAlgorithmParameterInterface) : this(other.getStart(), other.getDestination())

    override fun getStart(): Vertex {
        return start
    }

    override fun getDestination(): Vertex {
        return destination
    }

}
