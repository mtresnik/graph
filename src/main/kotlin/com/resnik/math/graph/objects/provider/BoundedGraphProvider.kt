package com.resnik.math.graph.objects.provider

import com.resnik.math.graph.objects.Graph
import com.resnik.math.graph.objects.Vertex
import com.resnik.math.linear.array.geometry.BoundingBox

class BoundedGraphProvider(val boundingBox: BoundingBox, val width : Int, val height : Int) : GraphProvider {

    override fun build(): Graph {
        val lengthX = boundingBox.maxX() - boundingBox.minX()
        val lengthY = boundingBox.maxY() - boundingBox.minY()
        val dX = lengthX / width
        val dY = lengthY / height

        val graph = Graph()
        val vertices = Array(height) { row ->
            Array(width) { col ->
                Vertex(col * dX + boundingBox.minX(), row * dY + boundingBox.minY() )
            }
        }
        repeat(height) { row ->
            repeat(width) { col ->
                // Connect all in the row
                val next = col + 1
                if(next < width) {
                    vertices[row][col].connectBidirectional(vertices[row][next])
                }
            }
        }
        repeat(width) { col ->
            repeat(height) { row ->
                // Connect all in the col
                val next = row + 1
                if(next < height) {
                    vertices[row][col].connectBidirectional(vertices[next][col])
                }
            }
        }
        graph.storage.vertexStorage.saveAll(vertices.flatten())
        graph.storage.edgeStorage.saveAll(graph.storage.vertexStorage.flatMap { it.edges })
        graph.storage.edgeStorage.forEach { edge -> edge.weight = 1.0 }
        return graph
    }

}