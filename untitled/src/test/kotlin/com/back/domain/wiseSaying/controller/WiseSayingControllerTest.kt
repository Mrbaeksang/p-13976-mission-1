package com.back.domain.wiseSaying.controller

import com.back.AppTestRunner
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import java.io.File
import org.junit.jupiter.api.BeforeEach

class WiseSayingControllerTest {

    @BeforeEach
    fun beforeEach() {
        // 테스트 실행 전 관련 파일 삭제
        val dataDir = File("db")
        if (dataDir.exists()) {
            dataDir.deleteRecursively()
        }
    }

    @Test
    @DisplayName("등록")
    fun t1() {
        val out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                """.trimIndent())

        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.")
    }
    
    @Test
    @DisplayName("등록 후 목록")
    fun t2() {
        val out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                목록
                """.trimIndent())

        assertThat(out)
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("1 / 작자미상 / 현재를 사랑하라.")
    }
}
