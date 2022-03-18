package com.resnik.math.graph.maze.objects.provider

import com.resnik.math.graph.algorithms.Dijkstra
import com.resnik.math.graph.algorithms.GAParams
import com.resnik.math.graph.maze.generator.AldousBroderMazeGenerator
import com.resnik.math.graph.maze.params.MazeParams
import com.resnik.math.graph.maze.ui.MazeRenderer
import com.resnik.math.graph.ui.GraphCollection
import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.linear.array.geometry.BoundingBox
import org.junit.jupiter.api.Test
import java.awt.Color
import java.io.File
import javax.imageio.ImageIO

class TestMazeGeneratorProvider {

    @Test
    fun testMazeGeneratorProvider1() {
        val endFile = File("C:\\Users\\Mike\\Desktop\\maze")
        if(!endFile.exists()) {
            error("Endfile does not exist...")
            return
        }

        val mazeGenerator = AldousBroderMazeGenerator(MazeParams(20, 20))
        val maze = mazeGenerator.build()
        val mazeRenderer = MazeRenderer(maze)
        val mazeImage = mazeRenderer.render()
        ImageIO.write(mazeImage, "png", File(endFile, "maze.png"))


        val mazeToGraphProvider = MazeToGraphProvider(maze)
        val graph = mazeToGraphProvider.build()
        val collection = GraphCollection()
        collection.pointRadius = 10
        collection.addGraph(graph, color = Color.BLACK)
        val graphImage = collection.render()
        ImageIO.write(graphImage, "png", File(endFile, "graph.png"))

        val vertices = graph.storage.vertexStorage.toList()
        val bbox = BoundingBox(*vertices.toTypedArray())

        val minVertex = vertices.minByOrNull { vertex -> vertex.distanceTo(ArrayPoint(bbox.minX(), bbox.minY())) }
        val maxVertex = vertices.minByOrNull {  vertex -> vertex.distanceTo(ArrayPoint(bbox.maxX(), bbox.maxY())) }
        assert(minVertex != null)
        minVertex!!
        assert(maxVertex != null)
        maxVertex!!
        val start = minVertex
        val dest = maxVertex

        val algorithm = Dijkstra(GAParams(start, dest))
        val path = algorithm.evaluate()
        collection.addPath(path, color = Color.RED)
        val pathImage = collection.render()
        ImageIO.write(pathImage, "png", File(endFile, "pathImage.png"))

        // Convert path back to maze cells
        path.map { edge ->
            val fromMazeCell = maze.getClosestMazeCell(edge.from)
            val toMazeCell = maze.getClosestMazeCell(edge.to)
            val connection = maze.getConnections(fromMazeCell).firstOrNull { connection -> connection.to == toMazeCell }
            if(connection != null) {
                mazeRenderer.setConnection(connection, Color.RED)
            }
        }

        val mazePath = mazeRenderer.render()
        ImageIO.write(mazePath, "png", File(endFile, "mazePath.png"))

    }

}