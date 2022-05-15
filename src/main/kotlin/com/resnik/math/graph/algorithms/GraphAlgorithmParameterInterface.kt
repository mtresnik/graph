package com.resnik.math.graph.algorithms

import com.resnik.math.graph.objects.Vertex

interface GraphAlgorithmParameterInterface {

    fun getStart(): Vertex

    fun getOrigin(): Vertex = getStart()

    fun getDestination(): Vertex

    // TODO : Add async graphing algorithms
    fun isAsync(): Boolean = false

}