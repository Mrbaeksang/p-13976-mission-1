package com.back.standard.util

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.Scanner

object TestUtil {
    private val originalIn = System.`in`
    private val originalOut = System.out
    private var currentOut: PrintStream? = null

    fun genScanner(input: String): Scanner {
        val newIn = ByteArrayInputStream(input.toByteArray())
        System.setIn(newIn)
        return Scanner(System.`in`)
    }

    fun clearScanner() {
        System.setIn(originalIn)
    }

    fun setOutToByteArray(): ByteArrayOutputStream {
        val byteArrayOutputStream = ByteArrayOutputStream()
        currentOut = PrintStream(byteArrayOutputStream, true)
        System.setOut(currentOut!!)
        return byteArrayOutputStream
    }

    fun clearSetOutToByteArray() {
        System.setOut(originalOut)
        currentOut?.close()
        currentOut = null
    }
}
