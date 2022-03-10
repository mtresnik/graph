package com.resnik.math.graph.algorithms

import com.resnik.math.graph.objects.Graph
import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.objects.Vertex
import java.util.*

class DFS(start: Vertex, dest: Vertex, graph: Graph) : GraphAlgorithm(start, dest, graph) {

    override fun evaluate(): Path {
        val children = Stack<Vertex>()
        children.add(start)
        graph.storage.vertexStorage.forEach {
            it.previous = null
            it.value = Double.MAX_VALUE
        }
        start.previous = null
        start.value = 0.0
        var curr : Vertex
        while(children.isNotEmpty()) {
            curr = children.pop()
            onVisit(curr)
            if(curr == dest)
                break
            curr.edges.forEach { edge ->
                val neighbor = edge.to
                if(!hasVisited(neighbor)) {
                    val combinedValue = curr.value + edge.getDistance()
                    if(combinedValue < neighbor.value){
                        neighbor.previous = curr
                        neighbor.value = combinedValue
                    }
                    children.add(neighbor)
                }
            }
        }
        return backtrack(dest)
    }

}