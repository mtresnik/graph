package com.resnik.math.graph.maze.generator.division

import com.resnik.math.graph.maze.generator.MazeGenerator
import com.resnik.math.graph.maze.objects.Maze
import com.resnik.math.graph.maze.objects.MazeBorder
import com.resnik.math.graph.maze.objects.RelativeDirection
import com.resnik.math.graph.maze.params.MazeParameterInterface
import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.linear.array.geometry.BoundingBox
import java.util.*
import kotlin.math.max
import kotlin.math.min

open class DivisionMazeGenerator(params : MazeParameterInterface, val numDivide : Int = 1) : MazeGenerator(params) {

    data class DivisionLine(val startX : Double, val startY : Double, val endX : Double, val endY : Double) {

        val minX = min(startX, endX)
        val maxX = max(startX, endX)
        val minY = min(startY, endY)
        val maxY = max(startY, endY)

        fun randomLocationXY() : Pair<Double, Double> {
            val x = ((maxX - minX) * Math.random() + minX).coerceIn(minX, maxX)
            val y = ((maxY - minY) * Math.random() + minY).coerceIn(minY, maxY)
            return x to y
        }

    }

    data class MazeSplit(val lower : MazeDivision, val upper : MazeDivision)

    class MazeDivision(val splitDirection : RelativeDirection,
                       val bbox : BoundingBox,
                       val divisionLine : DivisionLine? = null,
                       val indexGenerator : (length : Double, min : Double) -> Double = { length, min -> length / 2.0 + min }) {

        fun split() : MazeSplit? {
            if(bbox.width() < 2 || bbox.height() < 2) return null
            val nextDirection = if(splitDirection == RelativeDirection.VERTICAL) RelativeDirection.HORIZONTAL else RelativeDirection.VERTICAL
            // Divide remaining space into two
            if(nextDirection == RelativeDirection.VERTICAL) {
                /*
                * LEFT | RIGHT
                * */
                val midX = indexGenerator(bbox.width(), bbox.minX())
                val leftBBox = BoundingBox(ArrayPoint(bbox.minX(), bbox.minY()), ArrayPoint(midX, bbox.maxY()))
                val rightBBox = BoundingBox(ArrayPoint(midX, bbox.minY()), ArrayPoint(bbox.maxX(), bbox.maxY()))
                val divisionLine = DivisionLine(midX, bbox.minY(), midX, bbox.maxY())
                return MazeSplit(MazeDivision(nextDirection, leftBBox, divisionLine, indexGenerator=indexGenerator), MazeDivision(nextDirection, rightBBox, divisionLine, indexGenerator=indexGenerator))
            } else {
                /*
                * UP
                * --
                * DOWN
                * */
                val midY = indexGenerator(bbox.height(), bbox.minY())
                val topBBox = BoundingBox(ArrayPoint(bbox.minX(), bbox.minY()), ArrayPoint(bbox.maxX(), midY))
                val bottomBBox = BoundingBox(ArrayPoint(bbox.minX(), midY), ArrayPoint(bbox.maxX(), bbox.maxY()))
                val divisionLine = DivisionLine(bbox.minX(), midY, bbox.maxX(), midY)
                return MazeSplit(MazeDivision(nextDirection, topBBox, divisionLine, indexGenerator=indexGenerator), MazeDivision(nextDirection, bottomBBox, divisionLine, indexGenerator=indexGenerator))
            }
        }
    }

    open fun indexGenerator(length: Double, min : Double) : Double {
        return length / 2.0 + min
    }

    override fun build(): Maze {
        // Divide the maze alternating between vertical and horizontal
        // Have a queue / stack of maze divisions to visit instead of calling recursively...
        val retMaze = Maze(params)
        val children = Stack<MazeDivision>()
        val bbox = BoundingBox(ArrayPoint(0.0, 0.0), ArrayPoint(getWidth().toDouble(), getHeight().toDouble()))
        val midX = indexGenerator(bbox.width() - 1, bbox.minX())
        val startDivisionLine = DivisionLine(midX, bbox.minY(), midX, bbox.maxY())
        val startDivision = MazeDivision(RelativeDirection.HORIZONTAL, bbox, startDivisionLine, indexGenerator = { a, b -> indexGenerator(a, b)})
        children.add(startDivision)
        while(children.isNotEmpty()) {
            val currDivision = children.pop()
            val divisionLine = currDivision.divisionLine
            if(divisionLine != null) {
                val currDirection = currDivision.splitDirection
                // Set walls along division line, randomly set value to clear cell
                (divisionLine.minX.toInt() .. divisionLine.maxX.toInt()).forEach { x ->
                    (divisionLine.minY.toInt()..divisionLine.maxY.toInt()).forEach { y ->
                        if(currDirection == RelativeDirection.VERTICAL) {
                            retMaze.setLeftWall(y, x, MazeBorder.WALL)
                        } else {
                            retMaze.setTopWall(y, x, MazeBorder.WALL)
                        }
                    }
                }
                repeat(numDivide) {
                    val (xD, yD) = divisionLine.randomLocationXY()
                    var x = xD.toInt()
                    var y = yD.toInt()
                    val possibleCell = retMaze.getIfValidXY(x, y)
                    if(possibleCell == null) {
                        val minX = divisionLine.minX.toInt().coerceIn(0, retMaze.getWidth() - 1)
                        val maxX = divisionLine.maxX.toInt().coerceIn(0, retMaze.getWidth() - 1)
                        val minY = divisionLine.minY.toInt().coerceIn(0, retMaze.getHeight() - 1)
                        val maxY = divisionLine.maxY.toInt().coerceIn(0, retMaze.getHeight() - 1)
                        x = (minX..maxX).random()
                        y = (minY..maxY).random()
                    }
                    if(currDirection == RelativeDirection.VERTICAL) {
                        retMaze.setTopWall(y, x, MazeBorder.PASSAGE)
                        retMaze.setLeftWall(y, x, MazeBorder.PASSAGE)
                    }
                }
            }
            val currSplit = currDivision.split()
            if(currSplit != null) {
                children.add(currSplit.lower)
                children.add(currSplit.upper)
            }
        }
        val allWalls = retMaze.cells.flatten().filter { retMaze.allWalls(it) }
        allWalls.forEach { cell ->
            repeat(numDivide + 1) {
                val border = MazeBorder.PASSAGE
                val (row, col) = cell.row to cell.col
                when((0..3).random()) {
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
