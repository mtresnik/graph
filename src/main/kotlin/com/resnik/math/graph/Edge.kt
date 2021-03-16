package com.resnik.math.graph

class Edge(val from: Vertex, val to: Vertex) {

    fun getDistance() : Double = from.distanceTo(to)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Edge
        if(from == other.from && to == other.to) return true
        if(from == other.to && to == other.from) return true
        return true
    }

    override fun hashCode(): Int {
        var result = from.hashCode()
        result = 31 * result + to.hashCode()
        return result
    }

    override fun toString(): String = "$from --> $to"

}