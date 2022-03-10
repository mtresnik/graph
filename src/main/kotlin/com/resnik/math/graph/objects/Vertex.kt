package com.resnik.math.graph.objects

import com.resnik.math.graph.Flaggable
import com.resnik.math.graph.Identifyable
import com.resnik.math.linear.array.ArrayPoint
import java.lang.IllegalStateException

open class Vertex(vararg values: Double, var id : Long? = null) : ArrayPoint(*values), Identifyable, Flaggable, Cloneable {

    private val flags : MutableSet<Long> = mutableSetOf()
    val edges: MutableSet<Edge> = mutableSetOf()

    fun connectBidirectional(other: Vertex) : Edge {
        this.connect(other)
        return other.connect(this)
    }

    open fun connect(other: Vertex): Edge {
        val edge = Edge(this, other)
        edges.add(edge)
        return edge
    }

    open fun connectMultiple(vararg others: Vertex): Set<Edge> = others.map { connect(it) }.toSet()

    fun getEdge(other: Vertex): Edge? = edges.firstOrNull { it.to == other }

    override fun getID(): Long? {
        return id
    }

    override fun setID(newID: Long) {
        this.id = newID
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Vertex
        if (id != other.id) return super.equals(other)
        if (!super.equals(other)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

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

    public override fun clone(): Vertex {
        val ret = Vertex(*this.values.copyOf(), id=this.id)
        ret.setFlags(this.flags)
        return ret
    }

    override fun toString(): String {
        return "(id=$id : ${super.toString()})"
    }


}