package com.resnik.math.graph.tsp

import com.resnik.math.graph.objects.ConnectedGraph
import com.resnik.math.graph.objects.Traversal
import com.resnik.math.linear.array.ArrayPoint

abstract class TSP(vararg points: ArrayPoint) : Traversal {

    val connectedGraph: ConnectedGraph = ConnectedGraph(*points)

}