package com.resnik.math.graph.algorithms

import com.resnik.math.graph.Graph
import com.resnik.math.graph.Path
import com.resnik.math.graph.Traversal
import com.resnik.math.graph.Vertex

abstract class GraphAlgorithm(protected val start: Vertex, protected val dest: Vertex, protected val graph: Graph) : Traversal {

    private val visited: MutableSet<Vertex> = mutableSetOf()

    protected fun Vertex.visit() = visited.add(this)

    protected fun Vertex.hasVisited() = visited.contains(this)

    protected fun backtrack(vertex: Vertex): Path{
        val retPath = Path()
        val vertexList = mutableListOf<Vertex>()
        var currVertex: Vertex = vertex
        while(currVertex.previous != null && currVertex.previous != currVertex){
            vertexList.add(currVertex)
            currVertex = currVertex.previous!!
        }
        vertexList.add(currVertex)
        vertexList.reverse()
        for(i in 0 until vertexList.lastIndex){
            currVertex = vertexList[i]
            val nextVertex = vertexList[i + 1]
            val edge = currVertex.getEdge(nextVertex)
            if(edge != null){
                retPath.add(edge)
            }
        }
        return retPath
    }

}