package com.resnik.math

import com.resnik.math.graph.Edge
import com.resnik.math.graph.Vertex
import kotlin.math.pow

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

fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2:Double): Double{
    val EARTH_RADIUS_METERS = 6371e3
    val lat1Rads = Math.toRadians(lat1)
    val lat2Rads = Math.toRadians(lat2)
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a: Double = Math.sin(dLat / 2).pow(2) +
            Math.cos(lat1Rads) * Math.cos(lat2Rads) * Math.sin(dLon / 2).pow(2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    return EARTH_RADIUS_METERS * c
}
