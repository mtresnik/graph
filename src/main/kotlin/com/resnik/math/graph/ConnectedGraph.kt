package com.resnik.math.graph

import com.resnik.math.linear.array.ArrayPoint

class ConnectedGraph(vararg points: ArrayPoint) : Graph(*points) {

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