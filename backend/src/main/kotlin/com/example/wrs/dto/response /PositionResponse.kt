package com.example.wrs.dto

import java.time.LocalDateTime

data class PositionResponse(
    val id: Long,
    val lat : Double,
    val lng : Double,
    val timestamp: LocalDateTime,
)