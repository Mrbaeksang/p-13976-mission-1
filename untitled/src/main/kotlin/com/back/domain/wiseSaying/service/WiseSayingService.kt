package com.back.domain.wiseSaying.service

import com.back.domain.wiseSaying.entity.WiseSaying
import com.back.domain.wiseSaying.repository.WiseSayingRepository

class WiseSayingService(private val wiseSayingRepository: WiseSayingRepository) {

    fun save(content: String, author: String): WiseSaying {
        return wiseSayingRepository.save(content, author)
    }

    fun findAll(keyword: String, keywordType: String, page: Int, pageSize: Int): Pair<List<WiseSaying>, Int> {
        var filtered = wiseSayingRepository.findAll()

        if (keyword.isNotBlank()) {
            filtered = filtered.filter { 
                when (keywordType) {
                    "author" -> it.author.contains(keyword)
                    "content" -> it.content.contains(keyword)
                    else -> true
                }
            }
        }

        val totalCount = filtered.size
        val pagedList = filtered.reversed().chunked(pageSize).getOrNull(page - 1) ?: emptyList()

        return Pair(pagedList, totalCount)
    }

    fun findById(id: Int): WiseSaying? {
        return wiseSayingRepository.findById(id)
    }

    fun remove(wiseSaying: WiseSaying) {
        wiseSayingRepository.remove(wiseSaying)
    }

    fun update(id: Int, content: String, author: String) {
        wiseSayingRepository.update(id, content, author)
    }
    
    fun buildData() {
        wiseSayingRepository.buildData()
    }
}
