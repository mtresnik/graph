package com.resnik.math.graph.tsp

import com.resnik.math.Point
import com.resnik.math.graph.ConnectedGraph
import com.resnik.math.graph.Traversal

abstract class TSP(vararg points: Point) : Traversal {

    val connectedGraph: ConnectedGraph = ConnectedGraph(*points)

}