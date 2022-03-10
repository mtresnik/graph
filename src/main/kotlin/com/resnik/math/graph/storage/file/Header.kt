package com.resnik.math.graph.storage.file

import com.resnik.math.graph.storage.GraphConstants
import com.resnik.math.graph.storage.StringStorable
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintWriter
import java.util.*

abstract class Header(private val headerKey : String) : StringStorable<Map.Entry<String, List<String>>> {

    private val tempMap = mutableMapOf<String, List<String>>()

    abstract fun getMap() : Map<String, List<String>>

    protected abstract fun loadFromMap(map: Map<String, List<String>>)

    fun load() {
        loadFromMap(tempMap)
    }

    override fun writeTo(outputStream: OutputStream, close: Boolean) {
        val printWriter = PrintWriter(outputStream)
        writeWith(printWriter)
        printWriter.flush()
        printWriter.close()
    }

    fun writeWith(printWriter: PrintWriter) {
        val map = getMap()
        map.forEach { entry ->
            printWriter.println(toString(entry))
        }
    }

    override fun readFrom(inputStream: InputStream, close: Boolean) {
        tempMap.clear()
        val scanner = Scanner(inputStream)
        while(scanner.hasNextLine()) {
            consumeIfPossible(scanner.nextLine())
        }
        load()
        if(close) inputStream.close()
    }

    override fun consumeIfPossible(input: String) {
        if(canMap(input)) {
            val possibleEntry = fromString(input)
            if(possibleEntry != null) {
                tempMap[possibleEntry.key] = possibleEntry.value
            }
        }
    }

    override fun toString(value: Map.Entry<String, List<String>>): String {
        val (k, v) = value
        var retString = ""
        retString += GraphConstants.Keys.HEADER
        retString += GraphConstants.Keys.SPACE
        retString += headerKey
        retString += GraphConstants.Keys.SPACE
        retString += k
        retString += GraphConstants.Keys.SPACE
        v.forEach {
            retString += it
            retString += GraphConstants.Keys.SPACE
        }
        return retString
    }

    override fun fromString(input: String): Map.Entry<String, List<String>>? {
        val split = input.split(GraphConstants.Keys.SPACE)
        // val headerTag = split[0]
        // val hKey = split[1]
        if(split.size < 3) return null
        val key = split[2]
        val stringList = mutableListOf<String>()
        (3 .. split.lastIndex).forEach { index -> stringList.add(split[index]) }
        return AbstractMap.SimpleEntry(key, stringList)
    }

    override fun canMap(input: String): Boolean {
        val split = input.split(GraphConstants.Keys.SPACE)
        if(split.size < 2) return false
        if(split[0] != GraphConstants.Keys.HEADER) return false
        if(split[1] != this.headerKey) return false
        return true
    }

}