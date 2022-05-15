package com.resnik.math.graph.maze.generator

import com.resnik.math.graph.TestSaveDelegate
import com.resnik.math.graph.maze.generator.division.ConstantDivisionMazeGenerator
import com.resnik.math.graph.maze.generator.division.DivisionMazeGenerator
import com.resnik.math.graph.maze.generator.division.RandomDivisionMazeGenerator
import com.resnik.math.graph.maze.objects.Maze
import com.resnik.math.graph.maze.params.MazeParams
import com.resnik.math.graph.maze.ui.MazeRenderer
import org.junit.Test
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class TestMazeGenerator : TestSaveDelegate() {

    private val endFile = File("C:\\Users\\Mike\\Desktop\\maze\\examples")

    private fun saveIfExists(image: BufferedImage, name: String, ext: String = "png") {
        if (!SAVE) return
        if (!endFile.exists()) return
        ImageIO.write(image, ext, File(endFile, "$name.$ext"))
    }

    private fun renderOrSave(maze: Maze, name: String) {
        if (RENDER) {
            val mazeRenderer = MazeRenderer(maze)
            val image = mazeRenderer.render()
            saveIfExists(image, name)
        }
    }

    @Test
    fun testMazeGenerator1() {
        val mazeGenerator = DivisionMazeGenerator(MazeParams(40, 40), numDivide = 1)
        val maze = mazeGenerator.build()
        renderOrSave(maze, "division")
    }

    @Test
    fun testMazeGenerator2() {
        val mazeGenerator = RandomDivisionMazeGenerator(MazeParams(20, 20))
        val maze = mazeGenerator.build()
        renderOrSave(maze, "random")
    }

    @Test
    fun testMazeGenerator3() {
        val mazeGenerator = ConstantDivisionMazeGenerator(MazeParams(20, 20), 0.25, 1)
        val maze = mazeGenerator.build()
        renderOrSave(maze, "constant")
    }

    @Test
    fun testMazeGeneratorRandom() {
        val mazeGenerator = RandomMazeGenerator(MazeParams(40, 40))
        val maze = mazeGenerator.build()
        renderOrSave(maze, "random")
    }

    @Test
    fun testMazeGeneratorPrims() {
        val mazeGenerator = PrimsMazeGenerator(MazeParams(40, 40))
        val maze = mazeGenerator.build()
        renderOrSave(maze, "prims")
    }

    @Test
    fun testMazeGeneratorKruskals() {
        val mazeGenerator = KruskalsMazeGenerator(MazeParams(40, 40))
        val maze = mazeGenerator.build()
        renderOrSave(maze, "kruskals")
    }

    @Test
    fun testMazeGeneratorAldousBroder() {
        val mazeGenerator = AldousBroderMazeGenerator(MazeParams(40, 40))
        val maze = mazeGenerator.build()
        renderOrSave(maze, "aldousbroder")
    }

}