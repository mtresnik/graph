package com.resnik.math.graph.maze.objects

import com.resnik.math.graph.maze.params.MazeCoordinate
import com.resnik.math.graph.maze.params.MazeParameterInterface
import com.resnik.math.graph.maze.params.SelfDescribedParameters
import com.resnik.math.linear.array.ArrayPoint

class Maze(params: MazeParameterInterface) : SelfDescribedParameters(params) {

    val cells = Array(params.getHeight()) { row -> Array(params.getWidth()) { col -> MazeCell(row, col) } }

    private fun isValid(coordinate: MazeCoordinate): Boolean {
        return isValidRowCol(coordinate.row, coordinate.col)
    }

    fun isValidXY(x: Int, y: Int): Boolean {
        return isValidRowCol(y, x)
    }

    private fun isValidRowCol(row: Int, col: Int): Boolean {
        if (row < 0 || row >= getHeight()) return false
        if (col < 0 || col >= getWidth()) return false
        return true
    }

    fun getIfValid(coordinate: MazeCoordinate): MazeCell? {
        return getIfValid(coordinate.row, coordinate.col)
    }

    fun getIfValidXY(x: Int, y: Int): MazeCell? {
        return getIfValid(y, x)
    }

    fun getIfValid(row: Int, col: Int): MazeCell? {
        if (!isValidRowCol(row, col)) return null
        return cells[row][col]
    }

    private fun getLeftWall(coordinate: MazeCoordinate): MazeBorder? {
        return getLeftWall(coordinate.row, coordinate.col)
    }

    fun getLeftWall(row: Int, col: Int): MazeBorder? {
        val cell = getIfValid(row, col) ?: return null
        return cell.left
    }

    private fun getRightWall(coordinate: MazeCoordinate): MazeBorder? {
        return getRightWall(coordinate.row, coordinate.col)
    }

    fun getRightWall(row: Int, col: Int): MazeBorder? {
        if (!isValidRowCol(row, col)) return null
        val rightCell = getRight(row, col) ?: return MazeBorder.WALL
        return rightCell.left
    }

    private fun getTopWall(coordinate: MazeCoordinate): MazeBorder? {
        return getTopWall(coordinate.row, coordinate.col)
    }

    fun getTopWall(row: Int, col: Int): MazeBorder? {
        val cell = getIfValid(row, col) ?: return null
        return cell.top
    }

    private fun getBottomWall(coordinate: MazeCoordinate): MazeBorder? {
        return getBottomWall(coordinate.row, coordinate.col)
    }

    fun getBottomWall(row: Int, col: Int): MazeBorder? {
        if (!isValidRowCol(row, col)) return null
        val bottomCell = getDown(row, col) ?: return MazeBorder.WALL
        return bottomCell.top
    }

    fun allWalls(coordinate: MazeCoordinate): Boolean {
        return allWalls(coordinate.row, coordinate.col)
    }

    private fun allWalls(row: Int, col: Int): Boolean {
        if (getTopWall(row, col) != MazeBorder.WALL) return false
        if (getLeftWall(row, col) != MazeBorder.WALL) return false
        if (getRightWall(row, col) != MazeBorder.WALL) return false
        if (getBottomWall(row, col) != MazeBorder.WALL) return false
        return true
    }

    fun setWall(coordinate: MazeCoordinate, direction: AbsoluteDirection, border: MazeBorder) {
        setWall(coordinate.row, coordinate.col, direction, border)
    }

    private fun setWall(row: Int, col: Int, direction: AbsoluteDirection, border: MazeBorder) {
        if (!isValidRowCol(row, col)) return
        when (direction) {
            AbsoluteDirection.RIGHT -> setRightWall(row, col, border)
            AbsoluteDirection.UP -> setTopWall(row, col, border)
            AbsoluteDirection.LEFT -> setLeftWall(row, col, border)
            AbsoluteDirection.DOWN -> setBottomWall(row, col, border)
        }
    }

    private fun setLeftWall(coordinate: MazeCoordinate) {
        this.setLeftWall(coordinate.row, coordinate.col, MazeBorder.WALL)
    }

    fun setLeftWall(row: Int, col: Int, border: MazeBorder) {
        val cell = getIfValid(row, col) ?: return
        cell.left = border
    }

    fun setRightWall(coordinate: MazeCoordinate, border: MazeBorder) {
        setRightWall(coordinate.row, coordinate.col, border)
    }

    fun setRightWall(row: Int, col: Int, border: MazeBorder) {
        if (!isValidRowCol(row, col)) return
        val rightCell = getRight(row, col) ?: return
        rightCell.left = border
    }

    private fun setTopWall(coordinate: MazeCoordinate) {
        this.setTopWall(coordinate.row, coordinate.col, MazeBorder.WALL)
    }

    fun setTopWall(row: Int, col: Int, border: MazeBorder) {
        val cell = getIfValid(row, col) ?: return
        cell.top = border
    }

    fun setBottomWall(coordinate: MazeCoordinate, border: MazeBorder) {
        setBottomWall(coordinate.row, coordinate.col, border)
    }

    fun setBottomWall(row: Int, col: Int, border: MazeBorder) {
        if (!isValidRowCol(row, col)) return
        val bottomCell = getDown(row, col) ?: return
        bottomCell.top = border
    }

    fun getConnections(coordinate: MazeCoordinate): List<MazeConnection> {
        return getConnections(coordinate.row, coordinate.col)
    }

    private fun getConnections(row: Int, col: Int): List<MazeConnection> {
        val retList = mutableListOf<MazeConnection>()
        if (!isValidRowCol(row, col)) return retList
        AbsoluteDirection.values().forEach { direction ->
            val connection = getConnection(row, col, direction)
            if (connection != null) retList.add(connection)
        }
        return retList
    }

    private fun getConnection(row: Int, col: Int, direction: AbsoluteDirection): MazeConnection? {
        val from = getIfValid(row, col) ?: return null
        val to = getNeighbor(from, direction) ?: return null
        val wall = getWall(from, direction) ?: return null
        return MazeConnection(from, to, direction, wall)
    }

    private fun getWall(coordinate: MazeCoordinate, direction: AbsoluteDirection): MazeBorder? {
        return getWall(coordinate.row, coordinate.col, direction)
    }

    private fun getWall(row: Int, col: Int, direction: AbsoluteDirection): MazeBorder? {
        return when (direction) {
            AbsoluteDirection.RIGHT -> getRightWall(row, col)
            AbsoluteDirection.UP -> getTopWall(row, col)
            AbsoluteDirection.LEFT -> getLeftWall(row, col)
            AbsoluteDirection.DOWN -> getBottomWall(row, col)
        }
    }

    private fun getNeighbor(coordinate: MazeCoordinate, direction: AbsoluteDirection): MazeCell? {
        return getNeighbor(coordinate.row, coordinate.col, direction)
    }

    private fun getNeighbor(row: Int, col: Int, direction: AbsoluteDirection): MazeCell? {
        return when (direction) {
            AbsoluteDirection.RIGHT -> getRight(row, col)
            AbsoluteDirection.UP -> getUp(row, col)
            AbsoluteDirection.LEFT -> getLeft(row, col)
            AbsoluteDirection.DOWN -> getDown(row, col)
        }
    }

    private fun getLeft(row: Int, col: Int): MazeCell? = getIfValid(row, col - 1)

    private fun getRight(row: Int, col: Int): MazeCell? = getIfValid(row, col + 1)

    private fun getUp(row: Int, col: Int): MazeCell? = getIfValid(row + 1, col)

    private fun getDown(row: Int, col: Int): MazeCell? = getIfValid(row - 1, col)

    fun getNeighbors(mazeCell: MazeCell): List<MazeCell> {
        return getNeighbors(mazeCell.row, mazeCell.col)
    }

    private fun getNeighbors(row: Int, col: Int): List<MazeCell> {
        val ret = mutableListOf<MazeCell>()
        fun addIfNonNull(mazeCell: MazeCell?) {
            if (mazeCell != null) ret.add(mazeCell)
        }
        addIfNonNull(getRight(row, col))
        addIfNonNull(getUp(row, col))
        addIfNonNull(getLeft(row, col))
        addIfNonNull(getDown(row, col))
        return ret
    }

    fun getAllBottom(): List<MazeCell> {
        val retList = mutableListOf<MazeCell>()
        retList.addAll(cells.first())
        return retList
    }

    private fun getAllTop(): List<MazeCell> {
        val retList = mutableListOf<MazeCell>()
        retList.addAll(cells.last())
        return retList
    }

    private fun getAllLeft(): List<MazeCell> {
        val retList = mutableListOf<MazeCell>()
        retList.addAll(cells.map { row -> row.first() })
        return retList
    }

    fun getAllRight(): List<MazeCell> {
        val retList = mutableListOf<MazeCell>()
        retList.addAll(cells.map { row -> row.last() })
        return retList
    }

    fun numWalls(coordinate: MazeCoordinate): Int {
        if (!isValid(coordinate)) return -1
        var ret = 0
        if (getRightWall(coordinate) == MazeBorder.WALL) ret++
        if (getTopWall(coordinate) == MazeBorder.WALL) ret++
        if (getLeftWall(coordinate) == MazeBorder.WALL) ret++
        if (getBottomWall(coordinate) == MazeBorder.WALL) ret++
        return ret
    }

    fun wrap() {
        getAllTop().forEach { top -> setTopWall(top) }
        getAllLeft().forEach { left -> setLeftWall(left) }
    }

    fun getClosestMazeCell(point: ArrayPoint): MazeCell {
        return this.cells.flatten().minByOrNull { cell ->
            val pointRep = ArrayPoint(cell.x.toDouble(), cell.y.toDouble())
            pointRep.distanceTo(point)
        }!!
    }

}