package com.resnik.math.graph.objects

import com.resnik.math.linear.array.ArrayPoint

class PartiallyConnectedGraph(vararg points: ArrayPoint, numConnections : Int = 3) : Graph(*points) {

    init {
        storage.vertexStorage.forEach { curr ->
            this.storage.vertexStorage.kNearestNeighbors(curr, numConnections)?.forEach { other ->
                if(curr != other){
                    curr.connectBidirectional(other)
                }
            }
        }
        storage.edgeStorage.clear()
        storage.edgeStorage.saveAll(storage.vertexStorage.flatMap { nd -> nd.edges }.toSet())
    }


}