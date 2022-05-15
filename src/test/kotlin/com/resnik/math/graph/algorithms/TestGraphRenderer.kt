package com.resnik.math.graph.algorithms

import com.resnik.math.graph.TestRenderDelegate
import com.resnik.math.graph.objects.Graph
import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.objects.Vertex
import com.resnik.math.graph.ui.GraphCollection
import java.awt.Color

abstract class TestGraphRenderer : TestRenderDelegate() {

    protected fun renderIfSet(graph: Graph, start: Vertex, dest: Vertex, path: Path, visitRecorder: VisitRecorder? = null) {
        if(!RENDER) return
        val collection = GraphCollection()
        collection.pointRadius = 10
        collection.lineStroke = 1.0f
        collection.addGraph(graph, Color.BLUE)
        if (visitRecorder != null) collection.addPoints(visitRecorder.toList(), Color.GREEN)
        collection.addPoint(start, color = Color.RED)
        collection.addPoint(dest, color = Color.RED)
        collection.addPath(path, Color.RED)
        collection.render()
    }

}