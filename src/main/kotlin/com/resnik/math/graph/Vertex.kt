package com.resnik.math.graph

import com.resnik.math.Point

class Vertex(vararg values: Double) : Point(*values) {

    val edges: MutableSet<Edge> = mutableSetOf()

    fun connect(other: Vertex) {
        val edge = Edge(this, other)
        edges.add(edge)
        other.edges.add(edge)
    }

}