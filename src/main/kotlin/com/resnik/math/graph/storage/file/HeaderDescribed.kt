package com.resnik.math.graph.storage.file

interface HeaderDescribed<T : Header> {

    fun getHeader(): T

    fun loadFrom(header: T) {}

}