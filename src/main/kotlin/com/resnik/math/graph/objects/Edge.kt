package com.resnik.math.graph.objects

import com.resnik.math.graph.Flaggable
import com.resnik.math.graph.Identifyable
import com.resnik.math.linear.array.geometry.BoundingBox
import kotlin.math.acos
import kotlin.math.atan2

open class Edge(var from: Vertex, var to: Vertex, var weight: Double = 0.0, var id : Long? = null)
    : Identifyable, Flaggable, Cloneable {

    private val flags : MutableSet<Long> = mutableSetOf()

    open fun getDistance() : Double {
        return from.distanceTo(to)
    }

    fun getTheta() : Double {
        return atan2(to.y() - from.y(), to.x() - from.x()) + 2 * Math.PI
    }

    open fun getBounds() : BoundingBox {
        return BoundingBox(from, to)
    }

    override fun getID(): Long? {
        return id
    }

    override fun setID(newID: Long) {
        this.id = newID
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Edge
        if(this.id != other.id) return false
        if(from == other.from && to == other.to) return true
        if(from == other.to && to == other.from) return true
        return false
    }

    override fun hashCode(): Int {
        var result = from.hashCode()
        result = 31 * result + to.hashCode()
        return result
    }

    override fun toString(): String = "$from --> $to"

    override fun setFlags(flags : Collection<Long>) {
        this.flags.clear()
        this.flags.addAll(flags)
    }

    override fun getFlags(): Set<Long> {
        return flags
    }

    override fun clearFlags() {
        this.flags.clear()
    }

    public override fun clone(): Edge {
        return Edge(from.clone(), to.clone(), weight=weight, id=id)
    }

}