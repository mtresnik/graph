package com.resnik.math.graph.maze.ui

import com.resnik.math.graph.maze.objects.Maze
import com.resnik.math.graph.maze.params.MazeParams
import org.junit.jupiter.api.Test

class TestMazeRenderer {

    @Test
    fun testMazeToImage1(){
        val maze = Maze(MazeParams(20, 20))
        val mazeRenderer = MazeRenderer(maze)
        mazeRenderer.render()
    }

}