package com.resnik.math.graph

import com.resnik.math.Point

class Graph(vararg points: Point) {

    var vertices: Set<Vertex> = points.map { Vertex(*it.values) }.toSet()
    var edges: Set<Edge>

    init {
        vertices.forEach { curr ->
            vertices.forEach { other ->
                if(curr != other){
                    curr.connect(other)
                }
            }
        }
        edges = vertices.flatMap { nd -> nd.edges }.toSet()
    }

}