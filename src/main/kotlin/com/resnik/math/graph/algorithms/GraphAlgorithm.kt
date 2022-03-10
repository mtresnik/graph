package com.resnik.math.graph.algorithms

import com.resnik.math.graph.objects.Graph
import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.objects.Traversal
import com.resnik.math.graph.objects.Vertex
import java.lang.IllegalStateException

abstract class GraphAlgorithm(protected val start: Vertex, protected val dest: Vertex, protected val graph: Graph) : VertexProducer(),
    Traversal {

    private val _visited : Function1<Vertex, Boolean>
    private val _visit : Function1<Vertex, Boolean>
    private val vertexSet = mutableSetOf<Vertex>()
    private val idSet = mutableSetOf<Long>()

    init {
        if(start.id != null && dest.id != null) {
            _visited = { v ->
                if(v.id == null) throw IllegalStateException("Missing id.")
                idSet.contains(v.id)
            }
            _visit = { v ->
                if(v.id == null) throw IllegalStateException("Missing id.")
                idSet.add(v.id!!)
            }
        } else {
            _visited = { v ->
                vertexSet.contains(v)
            }
            _visit = { v ->
                vertexSet.add(v)
            }
        }
    }


    protected fun backtrack(vertex: Vertex): Path {
        val retPath = Path()
        val vertexSet = mutableSetOf<Vertex>()
        var currVertex: Vertex = vertex
        while(currVertex.previous != null && !vertexSet.contains(currVertex)) {
            vertexSet.add(currVertex)
            currVertex = currVertex.previous!!
        }
        val vertexList = vertexSet.toMutableList()
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

    override fun onVisit(vertex: Vertex) {
        super.onVisit(vertex)
        this._visit(vertex)
    }

    fun hasVisited(vertex: Vertex) : Boolean = this._visited(vertex)

    fun numVisited() : Int = vertexSet.size + idSet.size

}