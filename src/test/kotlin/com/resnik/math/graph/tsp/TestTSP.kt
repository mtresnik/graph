package com.resnik.math.graph.tsp

import com.resnik.math.graph.TestRenderDelegate
import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.ui.GraphCollection
import com.resnik.math.linear.array.ArrayPoint
import org.junit.jupiter.api.Test
import java.awt.Color
import java.lang.Math.random

class TestTSP : TestRenderDelegate() {

    fun randomPoints(max:Int = 8): Array<ArrayPoint> {
        return Array(max) { ArrayPoint(random(), random())}
    }

    @ExperimentalStdlibApi
    @Test
    fun testAll(){

        // Scales a lot better for higher amounts of vertices
        println("Random TSP")
        var start = System.currentTimeMillis()
        val points = randomPoints()
        val random: TSP = RandomTSP(*points)
        val randomResult = random.evaluate()
        println(randomResult)
        println("Time: ${System.currentTimeMillis() - start}")

        // Works well for a small amounts of vertices...
        println()
        println("Brute TSP")
        start = System.currentTimeMillis()
        val brute: TSP = BruteForceTSP(*points)
        val bruteResult = brute.evaluate()
        println(bruteResult)
        println("Time: ${System.currentTimeMillis() - start}")

        start = System.currentTimeMillis()
        val permutationTSP = PermutationTSP(*points)
        val permutationTSPResult = permutationTSP.evaluate()
        println(permutationTSPResult)
        println("Time: ${System.currentTimeMillis() - start}")

        // Works well for a large amount of vertices
        start = System.currentTimeMillis()
        val greedyTSP = GreedyTSP(*points)
        val greedyTSPResult = greedyTSP.evaluate()
        println(greedyTSPResult)
        println("Time: ${System.currentTimeMillis() - start}")

        if (RENDER){
            val collection = GraphCollection("Test TSP")
            collection.addGraph(random.graph)
            // collection.addPath(randomResult, Color.GREEN)
            collection.addPath(bruteResult, Color.RED)
            collection.addPath(permutationTSPResult, Color.CYAN)
            collection.addPath(greedyTSPResult, Color.GREEN)
            collection.render()
        }
    }

    @Test
    fun testPermutationTSP() {
        val points = randomPoints(13)
        val start = System.currentTimeMillis()
        val permutationTSP = PermutationTSP(*points)
        val permutationTSPResult = permutationTSP.evaluate()
        permutationTSPResult.wrap()
        println(permutationTSPResult)
        println("Time: ${System.currentTimeMillis() - start}")
        if (RENDER) {
            val collection = GraphCollection("Test TSP")
            collection.addGraph(permutationTSP.graph)
            collection.addPath(permutationTSPResult, Color.RED)
            collection.render()
        }
    }


    @Test
    fun testDoubleTreeTSP() {
        println("DoubleTreeTSP")
        val points = randomPoints(8)
        var start = System.currentTimeMillis()
        val greedyTwiceAroundTSP = GreedyTwiceAroundTSP(*points)
        val tspResult = greedyTwiceAroundTSP.evaluate()
        printVerticesIndex(tspResult)

        println("Brute TSP")
        start = System.currentTimeMillis()
        val brute: TSP = BruteForceTSP(*points)
        val bruteResult = brute.evaluate()
        printVerticesIndex(bruteResult)
        println("Time: ${System.currentTimeMillis() - start}")

        println("GreedyTSP")
        start = System.currentTimeMillis()
        val greedyTSP = GreedyTSP(*points)
        val greedyTSPResult = greedyTSP.evaluate()
        println(greedyTSPResult)
        println("Time: ${System.currentTimeMillis() - start}")
    }

    @Test
    fun testTwoOpt(){
        val points = randomPoints(20)
        println("GreedyTSP")
        var start = System.currentTimeMillis()
        val greedyTSP = GreedyTSP(*points)
        val greedyTSPResult = greedyTSP.evaluate()
        println("size: ${greedyTSPResult.size}")
        println(greedyTSPResult)
        printVerticesIndex(greedyTSPResult)
        println("Time: ${System.currentTimeMillis() - start}")

        if(RENDER) {
            val greedyCollection = GraphCollection("Test TSP")
            //greedyCollection.addGraph(greedyTSP.graph)
            greedyCollection.addPath(greedyTSPResult, Color.RED)
            greedyCollection.render()
        }

        start = System.currentTimeMillis()
        val twoOptTSP = TwoOptTSP(*points)
        val twoOptTSPResult = twoOptTSP.evaluate()
        val twoOptSize = twoOptTSPResult.size
        println("size: $twoOptSize")
        println(twoOptTSPResult)
        printVerticesIndex(twoOptTSPResult)
        println("Time: ${System.currentTimeMillis() - start}")

       if(RENDER) {
           val collection = GraphCollection("Test TSP")
           // collection.addGraph(twoOptTSP.graph)
           collection.addPath(twoOptTSPResult, Color.RED)
           collection.render()
       }
    }


    private fun printVerticesIndex(path : Path) {
        val vertices = path.map { it.from.id }.toMutableList()
        println("${path.getDistance()} $vertices")
    }



}