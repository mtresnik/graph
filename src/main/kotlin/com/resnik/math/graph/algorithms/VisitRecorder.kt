package com.resnik.math.graph.algorithms

import com.resnik.math.graph.objects.Vertex

class VisitRecorder : VertexProducer(), VisitListener {

    private val records = LinkedHashMap<Long, MutableList<Vertex>>()

    override fun onVisit(vertex: Vertex) {
        val currTime = System.currentTimeMillis()
        val currList = records[currTime] ?: mutableListOf()
        currList.add(vertex)
        records[currTime] = currList
    }

    fun toList(): List<Vertex> = records.values.flatten()

}