package com.resnik.math.graph.maze.params

open class MazeParams(private val width : Int, private val height : Int) : MazeParameterInterface {

    override fun getWidth(): Int {
        return width
    }

    override fun getHeight(): Int {
        return height
    }

}