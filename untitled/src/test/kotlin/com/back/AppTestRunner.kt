package com.back

import com.back.standard.util.TestUtil

object AppTestRunner {
    fun run(input: String): String {
        val commands = input.trimIndent() + "\n종료"
        TestUtil.genScanner(commands)

        val out = TestUtil.setOutToByteArray()

        try {
            App().run()
        } finally {
            TestUtil.clearScanner()
            TestUtil.clearSetOutToByteArray()
        }

        return out.toString()
    }
}

