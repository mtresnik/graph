package com.resnik.math.graph

class Path(edges: Collection<Edge>) : ArrayList<Edge>(edges) {

    fun getDistance() : Double = this.map { edge -> edge.getDistance() }.sum()

    override fun toString(): String = "${getDistance()}: ${super.toString()}"

}