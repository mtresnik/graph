package com.resnik.math.graph.objects

import com.resnik.math.linear.array.ArrayPoint

class ConnectedGraph(vararg points: ArrayPoint) : Graph(*points) {

    init {
        storage.vertexStorage.forEach { curr ->
            storage.vertexStorage.forEach { other ->
                if(curr != other){
                    curr.connectBidirectional(other)
                }
            }
        }
        storage.edgeStorage.clear()
        storage.edgeStorage.saveAll(storage.vertexStorage.flatMap { nd -> nd.edges }.toSet())
    }


}