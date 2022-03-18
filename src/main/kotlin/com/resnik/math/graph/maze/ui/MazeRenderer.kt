package com.resnik.math.graph.maze.ui

import com.resnik.math.graph.maze.objects.Maze
import com.resnik.math.graph.maze.objects.MazeBorder
import com.resnik.math.graph.maze.objects.MazeConnection
import com.resnik.math.graph.maze.params.MazeCoordinate
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JOptionPane

class MazeRenderer(var maze : Maze, private val cellSize : Int = 20) {

    private val wallSize : Int = cellSize /8
    var lineStroke = 5.0f

    var visitedColor : Color? = Color.BLUE

    val visited = mutableSetOf<Pair<Int, Int>>()
    private val backgrounds = mutableMapOf<Pair<Int, Int>, Color>()
    private val connections = mutableMapOf<MazeConnection, Color>()

    fun setConnection(connection: MazeConnection, color: Color) {
        this.connections[connection] = color
    }

    fun setBackground(coordinate: MazeCoordinate, color: Color) {
        this.backgrounds[coordinate.toPair()] = color
    }

    private fun drawMazeCell(imageColStart : Int, imageColEnd : Int, imageRowStart : Int, imageRowEnd : Int,
                             initial : BufferedImage,
                             mazeRow : Int, mazeCol : Int) {
        val coordinate = MazeCoordinate(mazeRow, mazeCol)
        val background = when (coordinate.toPair()) {
            in backgrounds -> {
                backgrounds.getOrDefault(coordinate.toPair(), Color.WHITE)
            }
            in visited -> {
                visitedColor ?: Color.WHITE
            }
            else -> {
                Color.WHITE
            }
        }
        (imageColStart until imageColEnd).forEach { imageCol ->
            (imageRowStart until imageRowEnd).forEach { imageRow ->
                initial.setRGB(imageCol, imageRow, background.rgb)
            }
        }
        if (maze.getLeftWall(mazeRow, mazeCol) == MazeBorder.WALL) {
            (imageRowStart until imageRowEnd).forEach { imageRow ->
                repeat(wallSize) { wallIndex ->
                    val imageCol = imageColStart + wallIndex
                    initial.setRGB(imageCol, imageRow, Color.BLACK.rgb)
                }
            }
        }
        if (maze.getRightWall(mazeRow, mazeCol) == MazeBorder.WALL) {
            (imageRowStart until imageRowEnd).forEach { imageRow ->
                repeat(wallSize) { wallIndex ->
                    val imageCol = imageColEnd - wallIndex - 1
                    initial.setRGB(imageCol, imageRow, Color.BLACK.rgb)
                }
            }
        }
        if (maze.getTopWall(mazeRow, mazeCol) == MazeBorder.WALL) {
            (imageColStart until imageColEnd).forEach { imageCol ->
                repeat(wallSize) { wallIndex ->
                    val imageRow = imageRowEnd - wallIndex - 1
                    initial.setRGB(imageCol, imageRow, Color.BLACK.rgb)
                }
            }
        }
        if (maze.getBottomWall(mazeRow, mazeCol) == MazeBorder.WALL) {
            (imageColStart until imageColEnd).forEach { imageCol ->
                repeat(wallSize) { wallIndex ->
                    val imageRow = imageRowStart + wallIndex
                    initial.setRGB(imageCol, imageRow, Color.BLACK.rgb)
                }
            }
        }
    }

    private fun build() : BufferedImage {
        val retImage = BufferedImage(maze.getWidth() * cellSize, maze.getHeight() * cellSize, BufferedImage.TYPE_INT_ARGB)
        drawCells(retImage)
        drawConnections(retImage)
        return retImage
    }

    fun getImageRowCol(mazeCoordinate: MazeCoordinate) : Pair<Int, Int>{
        return mazeCoordinate.row * cellSize to mazeCoordinate.col * cellSize
    }

    fun getCenterRowCol(mazeCoordinate: MazeCoordinate) : Pair<Int, Int> {
        return (mazeCoordinate.row * cellSize + cellSize/2) to (mazeCoordinate.col * cellSize + cellSize/2)
    }

    fun drawCells(retImage: BufferedImage) {
        repeat(maze.getHeight()) { mazeRow ->
            val imageRowStart = mazeRow * cellSize
            val imageRowEnd = imageRowStart + cellSize
            repeat(maze.getWidth()) { mazeCol ->
                val imageColStart = mazeCol * cellSize
                val imageColEnd = imageColStart + cellSize
                val curr = maze.getIfValid(mazeRow, mazeCol)
                if(curr != null) {
                    // Fill in left if left
                    drawMazeCell(
                        imageColStart, imageColEnd, imageRowStart, imageRowEnd,
                        retImage,
                        mazeRow, mazeCol
                    )
                }
            }
        }
    }

    fun drawLine(color: Color, connection: MazeConnection, graphics2D: Graphics2D, stroke: Float = lineStroke){
        val (y1, x1) = getCenterRowCol(connection.from)
        val (y2, x2) = getCenterRowCol(connection.to)
        graphics2D.paint = color
        graphics2D.stroke = BasicStroke(stroke)
        graphics2D.drawLine(x1, y1, x2, y2)
    }

    fun drawConnections(image : BufferedImage) {
        val graphics = image.createGraphics()
        connections.forEach { (mazeConnection, color) ->  drawLine(color, mazeConnection, graphics) }
        graphics.dispose()
    }

    fun render() : BufferedImage {
        val image = build()
        val icon = ImageIcon(image)
        val label = JLabel(icon)
        JOptionPane.showMessageDialog(null, label)
        return image
    }

}