package com.resnik.math.graph.maze.generator

import com.resnik.math.graph.maze.objects.Maze
import com.resnik.math.graph.maze.objects.MazeBorder
import com.resnik.math.graph.maze.objects.MazeConnection
import com.resnik.math.graph.maze.params.MazeCoordinate
import com.resnik.math.graph.maze.params.MazeParameterInterface

class PrimsMazeGenerator(params: MazeParameterInterface) : MazeGenerator(params) {

    override fun build(): Maze {
        val retMaze = Maze(params)

        // Visited is a unique set
        val visited = mutableSetOf<MazeCoordinate>()
        // Walls is a unique list... checked on addition
        val wallList = mutableListOf<MazeConnection>()

        val startCell = retMaze.cells.flatten().random()
        // "Add to Maze" logic
        visited.add(startCell)
        wallList.addAll(retMaze.getConnections(startCell))
        while (wallList.isNotEmpty()) {
            val index = wallList.indices.random()
            val currConnection = wallList.removeAt(index)
            val visitedFrom = currConnection.from in visited
            val visitedTo = currConnection.to in visited

            if ((visitedFrom && !visitedTo) || (!visitedFrom && visitedTo)) {
                retMaze.setWall(currConnection.from, currConnection.direction, MazeBorder.PASSAGE)
                val toAdd = if (visitedFrom) currConnection.to else currConnection.from
                visited.add(toAdd)
                val toVisit = retMaze.getConnections(toAdd).filter { it !in wallList }
                wallList.addAll(toVisit)
            }
        }

        return retMaze
    }

}