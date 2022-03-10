package com.resnik.math.graph.algorithms

import com.resnik.math.graph.objects.Vertex

open class VertexProducer : VisitListener {

    private val listeners = mutableListOf<VisitListener>()

    override fun onVisit(vertex: Vertex) {
        this.listeners.forEach { listener -> listener.onVisit(vertex) }
    }

    fun addListener(listener: VisitListener) {
        this.listeners.add(listener)
    }

    fun removeListener(listener: VisitListener) {
        this.listeners.remove(listener)
    }

    fun clearListeners() {
        this.listeners.clear()
    }

}