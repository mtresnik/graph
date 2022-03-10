package com.resnik.math.graph.objects

import com.resnik.math.graph.Flaggable
import com.resnik.math.graph.Identifyable
import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.linear.array.geometry.BoundingBox

class Path(collection: Collection<Edge> = mutableListOf(), var id : Long? = null) : ArrayList<Edge>(collection),
    Identifyable, Flaggable, Cloneable {

    private val flags = mutableSetOf<Long>()
    var values = mutableListOf<Double>()

    fun getDistance() : Double = this.sumOf { edge -> edge.getDistance() }

    override fun getID(): Long? {
        return this.id
    }

    override fun setID(newID: Long) {
        this.id = newID
    }

    override fun setFlags(flags: Collection<Long>) {
        this.flags.clear()
        this.flags.addAll(flags)
    }

    override fun getFlags(): Set<Long> {
        return flags
    }

    override fun clearFlags() {
        this.flags.clear()
    }

    fun hasValues() : Boolean = values.isNotEmpty()

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

    public override fun clone(): Path {
        return Path(this.map { edge -> edge.clone() }, id=id)
    }

    companion object {

        fun fromPoints(points: Collection<ArrayPoint>) : Path = Path(collection = points.map { pt -> Vertex(*pt.values) }.zipWithNext { a, b -> Edge(a, b) })

    }

}