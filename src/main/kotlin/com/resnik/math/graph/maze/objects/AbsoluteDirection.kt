package com.resnik.math.graph.maze.objects

enum class AbsoluteDirection {

    RIGHT, UP, LEFT, DOWN;

    fun getOpposite() : AbsoluteDirection {
        return when(this) {
            RIGHT -> LEFT
            UP -> DOWN
            LEFT -> RIGHT
            DOWN -> UP
            else -> throw IllegalStateException("Opposite not defined for: $this")
        }
    }

}