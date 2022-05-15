package com.resnik.math.graph.maze.objects.provider

import com.resnik.math.graph.TestSaveDelegate
import com.resnik.math.graph.algorithms.Dijkstra
import com.resnik.math.graph.algorithms.GAParams
import com.resnik.math.graph.maze.generator.AldousBroderMazeGenerator
import com.resnik.math.graph.maze.objects.Maze
import com.resnik.math.graph.maze.params.MazeParams
import com.resnik.math.graph.maze.ui.MazeRenderer
import com.resnik.math.graph.objects.Graph
import com.resnik.math.graph.ui.GraphCollection
import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.linear.array.geometry.BoundingBox
import org.junit.jupiter.api.Test
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class TestMazeGeneratorProvider : TestSaveDelegate() {

    private val endFile = File("C:\\Users\\Mike\\Desktop\\maze")

    private fun saveIfExists(image : BufferedImage, name : String, ext : String = "png") {
        if(!SAVE) return
        if(!endFile.exists()) return
        ImageIO.write(image, ext, File(endFile, "$name.$ext"))
    }

    private fun renderOrSave(maze: Maze, name: String) : MazeRenderer {
        val mazeRenderer = MazeRenderer(maze)
        if(RENDER) {
            val image = mazeRenderer.render()
            saveIfExists(image, name)
        }
        return mazeRenderer
    }

    private fun renderOrSave(graph: Graph, name: String, collection: GraphCollection = GraphCollection()) : GraphCollection {
        if(RENDER) {
            collection.pointRadius = 10
            collection.addGraph(graph, color = Color.BLACK)
            val image = collection.render()
            saveIfExists(image, name)
        }
        return collection
    }

    @Test
    fun testMazeGeneratorProvider1() {

        val mazeGenerator = AldousBroderMazeGenerator(MazeParams(20, 20))
        val maze = mazeGenerator.build()
        val mazeRenderer = renderOrSave(maze, "maze")

        val mazeToGraphProvider = MazeToGraphProvider(maze)
        val graph = mazeToGraphProvider.build()
        val collection = renderOrSave(graph, "graph")

        val vertices = graph.storage.vertexStorage.toList()
        val bbox = BoundingBox(*vertices.toTypedArray())

        val minVertex = vertices.minByOrNull { vertex -> vertex.distanceTo(ArrayPoint(bbox.minX(), bbox.minY())) }
        val maxVertex = vertices.minByOrNull {  vertex -> vertex.distanceTo(ArrayPoint(bbox.maxX(), bbox.maxY())) }
        assert(minVertex != null)
        minVertex!!
        assert(maxVertex != null)
        maxVertex!!

        val algorithm = Dijkstra(GAParams(minVertex, maxVertex))
        val path = algorithm.evaluate()
        collection.addPath(path, color = Color.RED)
        if(RENDER) {
            val pathImage = collection.render()
            if(SAVE) {
                ImageIO.write(pathImage, "png", File(endFile, "pathImage.png"))
            }
        }

        // Convert path back to maze cells
        path.map { edge ->
            val fromMazeCell = maze.getClosestMazeCell(edge.from)
            val toMazeCell = maze.getClosestMazeCell(edge.to)
            val connection = maze.getConnections(fromMazeCell).firstOrNull { connection -> connection.to == toMazeCell }
            if(connection != null) {
                mazeRenderer.setConnection(connection, Color.RED)
            }
        }

        if(RENDER) {
            val mazePath = mazeRenderer.render()
            if (SAVE) ImageIO.write(mazePath, "png", File(endFile, "mazePath.png"))
        }

    }

}