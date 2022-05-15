package com.resnik.math.graph.algorithms

import com.resnik.math.graph.algorithms.cost.VertexWrapper
import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.objects.Traversal
import com.resnik.math.graph.objects.Vertex

abstract class GraphAlgorithm<T : VertexWrapper<T>>(private val parameters: GraphAlgorithmParameterInterface) :
    VertexProducer(), Traversal, GraphAlgorithmParameterInterface {

    private val _visited: Function1<Vertex, Boolean>
    private val _visit: Function1<Vertex, Boolean>
    private val vertexSet = mutableSetOf<Vertex>()
    private val idSet = mutableSetOf<Long>()

    init {
        if (getStart().id != null && getDestination().id != null) {
            _visited = { v ->
                if (v.id == null) throw IllegalStateException("Missing id.")
                idSet.contains(v.id)
            }
            _visit = { v ->
                if (v.id == null) throw IllegalStateException("Missing id.")
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

    protected fun backtrack(wrapper: T): Path {
        val retPath = Path()
        val vertexSet = mutableSetOf<Vertex>()
        var currVertexWrapper: VertexWrapper<T> = wrapper
        while (currVertexWrapper.previous != null && !vertexSet.contains(currVertexWrapper.inner)) {
            vertexSet.add(currVertexWrapper.inner)
            currVertexWrapper = currVertexWrapper.previous!!
        }
        val vertexList = vertexSet.toMutableList()
        vertexList.add(currVertexWrapper.inner)
        vertexList.reverse()
        for (i in 0 until vertexList.lastIndex) {
            val currVertex = vertexList[i]
            val nextVertex = vertexList[i + 1]
            val edge = currVertex.getEdge(nextVertex)
            if (edge != null) {
                retPath.add(edge)
            }
        }
        return retPath
    }

    fun onVisit(wrapper: T) = onVisit(wrapper.inner)

    override fun onVisit(vertex: Vertex) {
        super.onVisit(vertex)
        this._visit(vertex)
    }

    fun hasVisited(wrapper: T): Boolean = hasVisited(wrapper.inner)

    fun hasVisited(vertex: Vertex): Boolean = this._visited(vertex)

    fun numVisited(): Int = vertexSet.size + idSet.size

    final override fun getStart(): Vertex {
        return parameters.getStart()
    }

    final override fun getDestination(): Vertex {
        return parameters.getDestination()
    }
}