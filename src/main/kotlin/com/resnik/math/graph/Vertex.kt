package com.resnik.math.graph

import com.resnik.math.linear.array.ArrayPoint

open class Vertex(vararg values: Double) : ArrayPoint(*values) {

    val edges: MutableSet<Edge> = mutableSetOf()
    var previous: Vertex? = null
    var value: Double = 0.0

    fun connectBidirectional(other: Vertex) : Edge{
        this.connect(other)
        return other.connect(this)
    }

    open fun connect(other: Vertex): Edge {
        val edge = Edge(this, other)
        edges.add(edge)
        return edge
    }

    open fun connectMultiple(vararg others: Vertex): Set<Edge> = others.map { connect(it) }.toSet()

    fun getEdge(other: Vertex): Edge? = edges.firstOrNull { it.to == other }

    override fun compareTo(other: ArrayPoint): Int {
        if(other is Vertex){
            return value.compareTo(other.value)
        }
        return super.compareTo(other)
    }

}