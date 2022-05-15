package com.resnik.math.graph.objects

import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.linear.array.geometry.BoundingBox

open class PolyEdge(
    val geometry: List<Vertex>,
    private val edgeConstructor: Function2<Vertex, Vertex, Edge> = { a, b -> Edge(a, b) },
    weight: Double = 0.0,
    id: Long? = null
) : Edge(geometry.first(), geometry.last(), weight = weight, id = id) {

    private val innerEdges: List<Edge> = geometry.zipWithNext { a, b -> edgeConstructor.invoke(a, b) }

    override fun getDistance(): Double = innerEdges.sumOf { edge -> edge.getDistance() }

    override fun getBounds(): BoundingBox {
        val pointList = mutableListOf<ArrayPoint>()
        geometry.forEach { pointList.add(it) }
        return BoundingBox(*pointList.toTypedArray())
    }

    override fun clone(): PolyEdge {
        return PolyEdge(geometry.map { it.clone() }, edgeConstructor = edgeConstructor, weight = weight, id = id)
    }
}