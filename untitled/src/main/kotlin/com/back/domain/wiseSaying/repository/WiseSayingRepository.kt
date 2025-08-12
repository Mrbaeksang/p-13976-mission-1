package com.back.domain.wiseSaying.repository

import com.back.domain.wiseSaying.entity.WiseSaying
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class WiseSayingRepository {
    private val wiseSayings = mutableListOf<WiseSaying>()
    private var lastId = 0
    private val dataDir = "db/wiseSaying"
    private val lastIdFile = File("$dataDir/lastId.txt")

    init {
        File(dataDir).mkdirs()
        if (lastIdFile.exists()) {
            lastId = lastIdFile.readText().toIntOrNull() ?: 0
        }

        val files = File(dataDir).listFiles { _, name -> name.endsWith(".json") } ?: emptyArray()
        for (file in files) {
            try {
                val jsonString = file.readText()
                val wiseSaying = Json.decodeFromString<WiseSaying>(jsonString)
                wiseSayings.add(wiseSaying)
            } catch (e: Exception) {
            }
        }

        if (wiseSayings.isEmpty()) {
            for (i in 1..10) {
                save("명언 $i", "작가 $i")
            }
        }
    }

    fun findById(id: Int): WiseSaying? {
        return wiseSayings.find { it.id == id }
    }

    fun findAll(): List<WiseSaying> {
        return wiseSayings.toList()
    }

    fun save(content: String, author: String): WiseSaying {
        val id = ++lastId
        val wiseSaying = WiseSaying(id, content, author)
        wiseSayings.add(wiseSaying)

        val jsonString = Json.encodeToString(wiseSaying)
        File("$dataDir/$id.json").writeText(jsonString)
        lastIdFile.writeText(id.toString())

        return wiseSaying
    }

    fun update(id: Int, content: String, author: String) {
        val wiseSaying = findById(id)!!
        wiseSaying.content = content
        wiseSaying.author = author

        val jsonString = Json.encodeToString(wiseSaying)
        File("$dataDir/$id.json").writeText(jsonString)
    }

    fun remove(wiseSaying: WiseSaying) {
        wiseSayings.remove(wiseSaying)
        File("$dataDir/${wiseSaying.id}.json").delete()
    }
    
    fun buildData() {
        val jsonString = Json.encodeToString(wiseSayings)
        File("data.json").writeText(jsonString)
    }
}
