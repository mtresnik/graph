package com.resnik.math.graph

class Path : ArrayList<Edge> {

    constructor(edges: Collection<Edge>) : super(edges)

    constructor() : super()

    fun getDistance() : Double = this.map { edge -> edge.getDistance() }.sum()

    override fun toString(): String = "${getDistance()}: ${super.toString()}"

}