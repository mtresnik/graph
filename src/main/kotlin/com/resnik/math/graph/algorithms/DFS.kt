package com.resnik.math.graph.algorithms

import com.resnik.math.graph.algorithms.cost.DefaultVertexWrapper
import com.resnik.math.graph.objects.Path
import java.util.*

class DFS(parameters : GraphAlgorithmParameterInterface) : GraphAlgorithm<DefaultVertexWrapper>(parameters) {

    override fun evaluate(): Path {
        val startWrapper = DefaultVertexWrapper(getStart(), 0.0)
        val destWrapper = DefaultVertexWrapper(getDestination())
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
                val successor = if(edge.to == getDestination()) destWrapper else DefaultVertexWrapper(edge.to)
                if(!hasVisited(successor)) {
                    successor.previous = curr
                    children.add(successor)
                }
            }
        }
        return backtrack(destWrapper)
    }

}