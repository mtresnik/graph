package com.resnik.math.graph.tsp

import com.resnik.math.Point
import com.resnik.math.graph.Graph
import com.resnik.math.graph.Traversal

abstract class TSP(vararg points: Point) : Traversal {

    protected val graph: Graph = Graph(*points)

}