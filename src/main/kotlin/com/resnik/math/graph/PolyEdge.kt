package com.resnik.math.graph

import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.linear.array.geometry.BoundingBox

open class PolyEdge(val geometry: List<Vertex>, edgeConstructor: Function2<Vertex, Vertex, Edge> = {a,b -> Edge(a,b)})
    : Edge(geometry.first(), geometry.last()) {

    private val innerEdges: List<Edge> = geometry.zipWithNext { a, b -> edgeConstructor.invoke(a,b) }

    override fun getDistance(): Double = innerEdges.sumByDouble { edge -> edge.getDistance() }

    override fun getBounds() : BoundingBox {
        val pointList = mutableListOf<ArrayPoint>()
        geometry.forEach { pointList.add(it) }
        return BoundingBox(*pointList.toTypedArray())
    }

}