package com.back

import com.back.domain.system.controller.SystemController
import com.back.domain.wiseSaying.controller.WiseSayingController
import com.back.domain.wiseSaying.repository.WiseSayingRepository
import com.back.domain.wiseSaying.service.WiseSayingService

class App {
    fun run() {
        println("== 명언 앱 ==")

        val systemController = SystemController()
        val wiseSayingRepository = WiseSayingRepository()
        val wiseSayingService = WiseSayingService(wiseSayingRepository)
        val wiseSayingController = WiseSayingController(wiseSayingService)

        while (true) {
            print("명령) ")
            val command = readLine()!!.trim()

            val commandBits = command.split("?", 2)
            val action = commandBits[0]
            val params = if (commandBits.size > 1) commandBits[1] else ""

            when (action) {
                "종료" -> {
                    systemController.exit()
                    break
                }
                "등록" -> wiseSayingController.save()
                "목록" -> wiseSayingController.list(params)
                "삭제" -> wiseSayingController.remove(params)
                "수정" -> wiseSayingController.update(params)
                "빌드" -> wiseSayingController.build()
            }
        }
    }
}
