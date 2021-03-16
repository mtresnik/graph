package com.resnik.math

import com.resnik.math.graph.Edge
import com.resnik.math.graph.Vertex

fun <T> permuteRecursive(set: Set<T>) : Set<List<T>> {
    if(set.isEmpty()) return emptySet()
    fun <T> _permuteRecursive(list: List<T>): Set<List<T>> {
        if(list.isEmpty()) return setOf(emptyList())
        val res: MutableSet<List<T>> = mutableSetOf()
        for (i in list.indices){
            _permuteRecursive(list - list[i]).forEach {
                res.add(it + list[i])
            }
        }
        return res
    }
    return _permuteRecursive(set.toList())
}

fun getEdge(from: Vertex, to: Vertex): Edge = from.edges.first { it.to == to }

fun verticesToEdges(vertices: List<Vertex>): List<Edge> {
    val ret = mutableListOf<Edge>()
    for(i in vertices.indices){
        if(i < vertices.size - 1){
            ret += getEdge(vertices[i], vertices[i+1])
        }
    }
    return ret
}