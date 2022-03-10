package com.resnik.math.graph.storage

import com.resnik.math.graph.storage.file.HeaderDescribed
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintWriter
import java.util.*
import java.util.function.Consumer

abstract class ItemizedStorable<KEY, ITEM> : StringStorable<ITEM>, Iterable<ITEM> {

    private val mapStore = mutableMapOf<KEY, ITEM>()

    override fun writeTo(outputStream: OutputStream, close: Boolean) {
        val printWriter = PrintWriter(outputStream)
        if(this is HeaderDescribed<*>)
            this.getHeader().writeWith(printWriter)
        mapStore.forEach { (_, item) ->
            printWriter.println(toString(item))
        }
        printWriter.close()
    }

    override fun readFrom(inputStream: InputStream, close: Boolean) {
        val header = if(this is HeaderDescribed<*>) getHeader() else null
        val scanner = Scanner(inputStream)
        while(scanner.hasNextLine()) {
            val line = scanner.nextLine()
            if(canMap(line)) {
                consumeIfPossible(line)
            } else {
                header?.consumeIfPossible(line)
            }
        }
        header?.load()
        if(close) inputStream.close()
    }

    abstract fun save(value : ITEM)

    fun saveAll(collection: Collection<ITEM>) {
        collection.forEach { save(it) }
    }

    override fun consumeIfPossible(input: String) {
        val item = fromString(input)
        if(item != null) save(item)
    }

    operator fun get(key: KEY) : ITEM? {
        return this.mapStore[key]
    }

    operator fun set(key: KEY, item: ITEM) {
        this.mapStore[key] = item
    }

    fun getOrDefault(key : KEY, default : ITEM) : ITEM {
        return this.mapStore.getOrDefault(key, default)
    }

    operator fun contains(key: KEY) : Boolean = this.mapStore.contains(key)

    fun clear() { this.mapStore.clear() }

    fun size() : Int = this.mapStore.size

    fun isEmpty() : Boolean = this.size() == 0

    fun isNotEmpty() : Boolean = !this.isEmpty()

    protected fun items() : Collection<ITEM> = mapStore.values

    override fun forEach(action: Consumer<in ITEM>?) {
        items().forEach(action)
    }

    override fun iterator(): Iterator<ITEM> {
        return items().iterator()
    }

    override fun spliterator(): Spliterator<ITEM> {
        return items().spliterator()
    }

}