package com.resnik.math.graph

import com.resnik.math.Point

class ConnectedGraph(vararg points: Point) : Graph(*points) {

    init {
        vertices.forEach { curr ->
            vertices.forEach { other ->
                if(curr != other){
                    curr.connectBidirectional(other)
                }
            }
        }
        edges = vertices.flatMap { nd -> nd.edges }.toSet()
    }


}