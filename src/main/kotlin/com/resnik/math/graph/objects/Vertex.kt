package com.resnik.math.graph.objects

import com.resnik.math.graph.Flaggable
import com.resnik.math.graph.Identifyable
import com.resnik.math.linear.array.ArrayPoint
import java.lang.IllegalStateException

open class Vertex(vararg values: Double, var id : Long? = null) : ArrayPoint(*values), Identifyable, Flaggable {

    private val flags : MutableSet<Long> = mutableSetOf()
    val edges: MutableSet<Edge> = mutableSetOf()
    var previous: Vertex? = null
    var value: Double = 0.0

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

    override fun compareTo(other: ArrayPoint): Int {
        if(other is Vertex){
            return value.compareTo(other.value)
        }
        return super.compareTo(other)
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
        if (!super.equals(other)) return false

        other as Vertex

        if (id != other.id) return false

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

    object ValueComparator : Comparator<Vertex> {

        override fun compare(o1: Vertex?, o2: Vertex?): Int {
            if(o1 == null && o2 == null) return 0
            if(o1 != null && o2 != null) {
                return o1.value.compareTo(o2.value)
            }
            throw IllegalStateException("One of the two is null here: $o1 comp $o2")
        }


    }

}