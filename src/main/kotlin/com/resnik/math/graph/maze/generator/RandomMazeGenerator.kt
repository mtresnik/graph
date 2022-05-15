package com.resnik.math.graph.maze.generator

import com.resnik.math.graph.maze.objects.Maze
import com.resnik.math.graph.maze.objects.MazeBorder
import com.resnik.math.graph.maze.params.MazeParameterInterface

class RandomMazeGenerator(params: MazeParameterInterface, val numRemove: Int = 2) : MazeGenerator(params) {

    override fun build(): Maze {
        val retMaze = Maze(params)
        val allCells = retMaze.cells.flatten()
        allCells.forEach { cell ->
            val (row, col) = cell.row to cell.col
            repeat(numRemove) {
                val numWalls = retMaze.numWalls(cell)
                val border = if (numWalls > 2) MazeBorder.PASSAGE else MazeBorder.WALL
                when ((0..3).random()) {
                    0 -> retMaze.setRightWall(row, col, border)
                    1 -> retMaze.setTopWall(row, col, border)
                    2 -> retMaze.setLeftWall(row, col, border)
                    3 -> retMaze.setBottomWall(row, col, border)
                }
            }
        }
        retMaze.wrap()
        return retMaze
    }

}