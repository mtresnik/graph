package com.resnik.math.graph.maze.objects

import com.resnik.math.graph.maze.params.MazeCoordinate

class MazeConnection(
    val from: MazeCoordinate,
    val to: MazeCoordinate,
    val direction: AbsoluteDirection,
    val border: MazeBorder
) {

    fun getOpposite(): MazeConnection = MazeConnection(to, from, direction.getOpposite(), border)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MazeConnection
        if (from == other.from && to == other.to) return true
        if (from == other.to && to == other.from) return true
        return false
    }

    override fun hashCode(): Int {
        var result = from.hashCode()
        result = 31 * result + to.hashCode()
        return result
    }


}