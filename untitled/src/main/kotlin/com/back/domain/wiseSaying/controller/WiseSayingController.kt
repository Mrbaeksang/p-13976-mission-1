package com.back.domain.wiseSaying.controller

import com.back.domain.wiseSaying.service.WiseSayingService

class WiseSayingController(private val wiseSayingService: WiseSayingService) {

    fun save() {
        print("명언 : ")
        val content = readLine()!!.trim()
        print("작가 : ")
        val author = readLine()!!.trim()

        val wiseSaying = wiseSayingService.save(content, author)
        println("${wiseSaying.id}번 명언이 등록되었습니다.")
    }

    fun list(params: String) {
        val paramMap = params.split("&").associate { param ->
            param.split("=", 2).let { it[0] to it.getOrNull(1) }
        }

        val keyword = paramMap["keyword"] ?: ""
        val keywordType = paramMap["keywordType"] ?: ""
        val page = paramMap["page"]?.toIntOrNull() ?: 1
        val pageSize = 5

        val (pagedWiseSayings, totalCount) = wiseSayingService.findAll(keyword, keywordType, page, pageSize)
        val totalPages = (totalCount + pageSize - 1) / pageSize

        if (keyword.isNotBlank()) {
            println("----------------------")
            println("검색타입 : $keywordType")
            println("검색어 : $keyword")
            println("----------------------")
        }

        println("번호 / 작가 / 명언")
        println("----------------------")
        for (wiseSaying in pagedWiseSayings) {
            println("${wiseSaying.id} / ${wiseSaying.author} / ${wiseSaying.content}")
        }
        
        if (totalCount > 0) {
            val pageLine = (1..totalPages).joinToString(" / ") { pageNum ->
                if (pageNum == page) "[$pageNum]" else pageNum.toString()
            }
            println("----------------------")
            println("페이지 : $pageLine")
        }
    }

    fun remove(params: String) {
        val id = params.split("=", 2).getOrNull(1)?.toIntOrNull()

        if (id == null) {
            println("id를 정확히 입력해주세요.")
            return
        }

        val wiseSaying = wiseSayingService.findById(id)

        if (wiseSaying != null) {
            wiseSayingService.remove(wiseSaying)
            println("${id}번 명언이 삭제되었습니다.")
        } else {
            println("${id}번 명언은 존재하지 않습니다.")
        }
    }

    fun update(params: String) {
        val id = params.split("=", 2).getOrNull(1)?.toIntOrNull()

        if (id == null) {
            println("id를 정확히 입력해주세요.")
            return
        }

        val wiseSaying = wiseSayingService.findById(id)

        if (wiseSaying != null) {
            println("명언(기존) : ${wiseSaying.content}")
            print("명언 : ")
            val newContent = readLine()!!.trim()

            println("작가(기존) : ${wiseSaying.author}")
            print("작가 : ")
            val newAuthor = readLine()!!.trim()

            wiseSayingService.update(id, newContent, newAuthor)
            println("${id}번 명언이 수정되었습니다.")
        } else {
            println("${id}번 명언은 존재하지 않습니다.")
        }
    }

    fun build() {
        wiseSayingService.buildData()
        println("data.json 파일의 내용이 갱신되었습니다.")
    }
}
