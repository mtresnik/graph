package com.resnik.math.graph.maze.objects

import com.resnik.math.graph.maze.params.MazeCoordinate

class MazeCell(row: Int, col: Int) : MazeCoordinate(row, col) {

    var left: MazeBorder = MazeBorder.WALL
    var top: MazeBorder = MazeBorder.WALL

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MazeCell

        if (row != other.row) return false
        if (col != other.col) return false
        return true
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + col
        return result
    }

}