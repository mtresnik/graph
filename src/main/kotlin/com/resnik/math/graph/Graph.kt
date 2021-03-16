package com.resnik.math.graph

import com.resnik.math.Point

open class Graph {

    var vertices: Set<Vertex>
    var edges: Set<Edge> = mutableSetOf()

    constructor(vararg points: Point){
        this.vertices = points.map { Vertex(*it.values) }.toSet()
    }

    constructor(vertices: Set<Vertex>, edges: Set<Edge>){
        this.vertices = vertices
        this.edges = edges
    }

}