package com.back.domain.wiseSaying.entity

import kotlinx.serialization.Serializable

@Serializable
data class WiseSaying(
    val id: Int,
    var content: String,
    var author: String
)
