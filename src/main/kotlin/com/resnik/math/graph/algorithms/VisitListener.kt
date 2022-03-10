package com.resnik.math.graph.algorithms

import com.resnik.math.graph.objects.Vertex

interface VisitListener {

    fun onVisit(vertex : Vertex)

}