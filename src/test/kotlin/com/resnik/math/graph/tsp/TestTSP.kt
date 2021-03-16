package com.resnik.math.graph.tsp

import com.resnik.math.Point
import com.resnik.math.graph.ui.GraphCollection
import org.junit.jupiter.api.Test
import java.awt.Color
import java.lang.Math.random

class TestTSP {

    fun randomPoints(max:Int = 9): Array<Point> {
        val points: Array<Point> = Array(max) {index -> Point()}
        for(i in 0 until max){
            points[i] = Point(random(), random())
        }
        return points
    }

    @ExperimentalStdlibApi
    @Test
    fun testBoth(){

        // Scales a lot better for higher amounts of vertices
        println("Random TSP")
        var start = System.currentTimeMillis()
        val points = randomPoints()
        val random: TSP = RandomTSP(*points)
        val randomResult = random.evaluate()
        println(randomResult)
        println("Time: ${System.currentTimeMillis() - start}")


        // Works really well for small amounts of vertices
        println()
        println("Brute TSP")
        start = System.currentTimeMillis()
        val brute: TSP = BruteForceTSP(*points)
        val bruteResult = brute.evaluate()
        println(bruteResult)
        println("Time: ${System.currentTimeMillis() - start}")

        var collection = GraphCollection("Test TSP")
        collection.addGraph(random.connectedGraph)
        // collection.addPath(randomResult, Color.GREEN)
        collection.addPath(bruteResult, Color.RED)
        collection.render()

    }



}