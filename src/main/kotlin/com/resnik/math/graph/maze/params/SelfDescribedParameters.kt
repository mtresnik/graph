package com.resnik.math.graph.maze.params

open class SelfDescribedParameters(protected val params : MazeParameterInterface) : MazeParameterInterface {

    override fun getWidth(): Int {
        return params.getWidth()
    }

    override fun getHeight(): Int {
        return params.getHeight()
    }

}