package com.resnik.math.graph

import com.resnik.math.Point
import com.resnik.math.graph.ui.BoundingBox

class Path : ArrayList<Edge> {

    constructor(edges: Collection<Edge>) : super(edges)

    constructor() : super()

    fun getDistance() : Double = this.map { edge -> edge.getDistance() }.sum()

    override fun toString(): String = "${getDistance()}: ${super.toString()}"

    fun wrap() = this.add(Edge(this.last().to, this.first().from))

    fun getBounds() : BoundingBox {
        val ret = BoundingBox()
        this.forEach {
            ret.update(it.from)
            ret.update(it.to)
        }
        return ret
    }

    companion object {

        fun fromPoints(points: Collection<Point>) : Path = Path(points.map { pt -> Vertex(*pt.values) }.zipWithNext { a, b -> Edge(a, b) })

    }

}