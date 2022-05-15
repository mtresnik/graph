package com.resnik.math.graph.maze.objects.provider

import com.resnik.math.graph.maze.objects.Maze
import com.resnik.math.graph.maze.objects.MazeBorder
import com.resnik.math.graph.maze.objects.MazeConnection
import com.resnik.math.graph.maze.params.MazeCoordinate
import com.resnik.math.graph.objects.Edge
import com.resnik.math.graph.objects.Graph
import com.resnik.math.graph.objects.Vertex
import com.resnik.math.graph.objects.provider.GraphProvider
import com.resnik.math.graph.storage.objects.graph.GraphStorage

class MazeToGraphProvider(val maze: Maze) : GraphProvider {

    override fun build(): Graph {
        val graphStorage = GraphStorage()

        // Convert all maze cells into vertices
        val cellToVertexMap = mutableMapOf<MazeCoordinate, Vertex>()
        maze.cells.flatten().forEach { cell ->
            val vertex = Vertex(cell.x.toDouble(), cell.y.toDouble())
            graphStorage.vertexStorage.save(vertex)
            cellToVertexMap[cell] = vertex
        }

        val connectionToEdgeMap = mutableMapOf<MazeConnection, Edge>()
        val connections = maze.cells.flatten()
            .flatMap { cell -> maze.getConnections(cell) }
            .filter { it.border == MazeBorder.PASSAGE }
        connections.forEach { connection ->
            val from = cellToVertexMap[connection.from]
            val to = cellToVertexMap[connection.to]
            if (from != null && to != null) {
                val edge = Edge(from, to)
                graphStorage.edgeStorage.save(edge)
                from.edges.add(edge)
                connectionToEdgeMap[connection] = edge
            }
        }

        return Graph(storage = graphStorage)
    }

}