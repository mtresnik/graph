package com.resnik.math.graph.maze.params

open class MazeCoordinate(val row: Int, val col: Int) {

    val x = col
    val y = row

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MazeCoordinate
        if (row != other.row) return false
        if (col != other.col) return false
        return true
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + col
        return result
    }

    fun toPair(): Pair<Int, Int> {
        return row to col
    }


}
