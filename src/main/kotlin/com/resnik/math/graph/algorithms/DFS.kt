package com.resnik.math.graph.algorithms

import com.resnik.math.graph.algorithms.cost.DefaultVertexWrapper
import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.objects.Vertex
import java.util.*

class DFS(start: Vertex, dest: Vertex) : GraphAlgorithm<DefaultVertexWrapper>(start, dest) {

    override fun evaluate(): Path {
        val startWrapper = DefaultVertexWrapper(start, 0.0)
        val destWrapper = DefaultVertexWrapper(dest)
        startWrapper.previous = startWrapper
        val children = Stack<DefaultVertexWrapper>()
        children.add(startWrapper)
        var curr : DefaultVertexWrapper
        while(children.isNotEmpty()) {
            curr = children.pop()
            onVisit(curr)
            if(curr == destWrapper)
                break
            curr.inner.edges.sortedBy { edge -> edge.getTheta() }.forEach { edge ->
                val successor = if(edge.to == dest) destWrapper else DefaultVertexWrapper(edge.to)
                if(!hasVisited(successor)) {
                    successor.previous = curr
                    children.add(successor)
                }
            }
        }
        return backtrack(destWrapper)
    }

}