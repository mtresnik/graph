package com.resnik.math.graph.maze.generator

import com.resnik.math.graph.maze.objects.Maze
import com.resnik.math.graph.maze.objects.MazeBorder
import com.resnik.math.graph.maze.objects.MazeConnection
import com.resnik.math.graph.maze.params.MazeCoordinate
import com.resnik.math.graph.maze.params.MazeParameterInterface

class KruskalsMazeGenerator(params: MazeParameterInterface) : MazeGenerator(params) {

    override fun build(): Maze {
        val retMaze = Maze(params)
        val allWalls = mutableListOf<MazeConnection>()
        val allSets = mutableListOf<MutableSet<MazeCoordinate>>()
        retMaze.cells.flatten().forEach { cell ->
            val connections = retMaze.getConnections(cell).filter { it !in allWalls }
            allWalls.addAll(connections)
            allSets.add(mutableSetOf(cell))
        }
        // All walls are edges with a weight of 1.0... meaning sorting is moot
        while(allWalls.isNotEmpty() && allSets.size > 1) {
            val toRemove = allWalls.indices.random()
            val currWall = allWalls.removeAt(toRemove)
            val fromIndex = allSets.indexOfFirst { set -> currWall.from in set }
            if(fromIndex != -1) {
                val fromSet = allSets.removeAt(fromIndex)
                val toIndex = allSets.indexOfFirst { set -> currWall.to in set }
                if(toIndex != -1) {
                    val toSet = allSets.removeAt(toIndex)
                    val joined = mutableSetOf<MazeCoordinate>()
                    retMaze.setWall(currWall.from, currWall.direction, MazeBorder.PASSAGE)
                    joined.addAll(fromSet)
                    joined.addAll(toSet)
                    allSets.add(joined)
                } else {
                    allSets.add(fromSet)
                }
            }
        }
        return retMaze
    }

}