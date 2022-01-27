package com.resnik.math.graph

import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.linear.array.geometry.BoundingBox

class Path : ArrayList<Edge> {

    constructor(edges: Collection<Edge>) : super(edges)

    constructor() : super()

    fun getDistance() : Double = this.map { edge -> edge.getDistance() }.sum()

    override fun toString(): String = "${getDistance()}: ${super.toString()}"

    fun wrap() = this.add(Edge(this.last().to, this.first().from))

    fun getBounds() : BoundingBox {
        val pointlist = mutableListOf<ArrayPoint>()
        this.forEach {
            pointlist.add(it.from)
            pointlist.add(it.to)
        }
        return BoundingBox(*pointlist.toTypedArray())
    }

    companion object {

        fun fromPoints(points: Collection<ArrayPoint>) : Path = Path(points.map { pt -> Vertex(*pt.values) }.zipWithNext { a, b -> Edge(a, b) })

    }

}