package com.resnik.math.graph.tsp

import com.resnik.math.graph.ConnectedGraph
import com.resnik.math.graph.Traversal
import com.resnik.math.linear.array.ArrayPoint

abstract class TSP(vararg points: ArrayPoint) : Traversal {

    val connectedGraph: ConnectedGraph = ConnectedGraph(*points)

}