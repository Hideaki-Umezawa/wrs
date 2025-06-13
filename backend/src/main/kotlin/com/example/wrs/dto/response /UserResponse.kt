package com.example.wrs.dto


//responseはpasswordを返したくないから型定義
data class UserResponse(
    val id: Long,
    val name: String,
    val mail: String
)