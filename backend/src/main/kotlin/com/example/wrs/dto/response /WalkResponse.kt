package com.example.wrs.dto

import java.time.LocalDateTime

//idと開始時間
data class StartWalkResponse(
    val id: Long,
    val startTime: LocalDateTime
)

//idと終了時間
data class EndWalkResponse(
    val id: Long,
    val endTime: LocalDateTime
)

data class WalkResponse(
    val id: Long,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime?
)