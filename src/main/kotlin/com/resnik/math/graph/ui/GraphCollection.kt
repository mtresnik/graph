package com.resnik.math.graph.ui

import com.resnik.math.Point
import com.resnik.math.graph.Edge
import com.resnik.math.graph.Graph
import com.resnik.math.graph.Path
import com.resnik.math.graph.Vertex
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JOptionPane
import kotlin.math.floor

class GraphCollection(val name: String) {

    private val graphs: MutableMap<Graph, Color> = mutableMapOf()
    private val paths: MutableMap<Path, Color> = mutableMapOf()
    private val points: MutableMap<Point, Color> = mutableMapOf()
    private val bounds: BoundingBox = BoundingBox()
    var background: Color = Color.WHITE
    var width: Int = 1000
    var height: Int = 1000
    var padding = 0.1

    fun addGraph(graph: Graph, color:Color = Color.BLACK) {
        graphs[graph] = color
        updateBounds()
    }

    fun addPath(path: Path, color:Color = Color.BLUE){
        paths[path] = color
        updateBounds()
    }

    fun addPoint(point: Point, color:Color = Color.CYAN){
        points[point] = color
        updateBounds()
    }

    fun addPoints(points: Collection<Point>, color:Color = Color.CYAN){
        points.forEach {
            this.points[it] = color
        }
        updateBounds()
    }

    fun updateBounds() {
        graphs.keys.forEach { graph ->
            graph.vertices.forEach {
                bounds.update(it)
            }
            graph.edges.forEach {
                bounds.update(it.from)
                bounds.update(it.to)
            }
        }
        paths.keys.forEach { path ->
            path.forEach {
                bounds.update(it.from)
                bounds.update(it.to)
            }
        }
        points.keys.forEach { point ->
            bounds.update(point)
        }
    }

    fun drawCenteredCircle(g: Graphics2D, x: Int, y: Int, r: Int) = g.fillOval(x - r / 2, y - r / 2, r, r)

    fun drawPoint(color: Color, point: Point, graphics2D: Graphics2D){
        val coordinate = convertPixels(point)
        graphics2D.paint = color
        drawCenteredCircle(graphics2D, coordinate.first, coordinate.second, 20)
    }

    fun drawLine(color: Color, edge: Edge, graphics2D: Graphics2D, stroke: Float = 5f){
        val from = convertPixels(edge.from)
        val to = convertPixels(edge.to)
        graphics2D.paint = color
        graphics2D.stroke = BasicStroke(stroke)
        graphics2D.drawLine(from.first, from.second, to.first, to.second)
    }

    fun convertPixels(point: Point) : Pair<Int, Int>{
        if(point !in bounds){
            throw RuntimeException("Point is out of current bounds of graph.")
        }
        val x = point.values[0]
        val y = point.values[1]
        val relX = (x - bounds.minX) / (bounds.maxX - bounds.minX)
        val relY = (y - bounds.minY) / (bounds.maxY - bounds.minY)
        return Pair(floor(relX*width).toInt(), floor(relY*height).toInt())
    }

    fun padBounds(){
        val dx = bounds.maxX - bounds.minX
        val dy = bounds.maxY - bounds.minY
        bounds.minX-=dx*padding
        bounds.minY-=dy*padding
        bounds.maxX+=dx*padding
        bounds.maxY+=dy*padding
    }

    fun build(): BufferedImage {
        println("Bounds $bounds")
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        val graphics: Graphics2D = image.createGraphics()
        graphics.background = background
        graphics.clearRect(0,0, width, height)
        padBounds()
        graphs.forEach { (graph, color) ->
            graph.edges.forEach { edge ->
                drawLine(color, edge, graphics)
            }
            graph.vertices.forEach { vertex ->
                drawPoint(color, vertex, graphics)
            }
        }
        points.forEach { (point, color) ->
            drawPoint(color, point, graphics)
        }
        paths.entries.forEachIndexed{ index, (path, color) ->
            path.forEach { edge ->
                drawLine(color, edge, graphics)
                drawPoint(color, edge.from, graphics)
                drawPoint(color, edge.to, graphics)
            }
        }
        graphics.dispose()
        return image
    }

    fun render() {
        val image = build()
        val icon = ImageIcon(image)
        val label = JLabel(icon)
        JOptionPane.showMessageDialog(null, label)
    }
}
