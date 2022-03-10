package com.resnik.math.graph.algorithms.cost

import com.resnik.math.graph.objects.Vertex

abstract class VertexWrapper<T : VertexWrapper<T>>(val inner : Vertex, val state : CostState) : Comparable<T> {

    var previous : VertexWrapper<T>? = null

    constructor(inner: Vertex, defaultCost : Double = 0.0) : this(inner, CostState(defaultCost))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as VertexWrapper<*>
        if (inner != other.inner) return false
        return true
    }

    override fun hashCode(): Int {
        return inner.hashCode()
    }

}