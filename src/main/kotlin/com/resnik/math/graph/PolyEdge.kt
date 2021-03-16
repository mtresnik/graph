package com.resnik.math.graph

open class PolyEdge(val geometry: List<Vertex>, edgeConstructor: Function2<Vertex, Vertex, Edge> = {a,b -> Edge(a,b)})
    : Edge(geometry.first(), geometry.last()) {

    private val innerEdges: List<Edge> = geometry.zipWithNext { a, b -> edgeConstructor.invoke(a,b) }

    override fun getDistance(): Double = innerEdges.sumByDouble { edge -> edge.getDistance() }

}