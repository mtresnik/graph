package com.resnik.math.graph.maze.params

interface MazeGenerationResult : MazeParameterInterface {

    fun getStart() : MazeCoordinate

    fun getDestination() : MazeCoordinate

}