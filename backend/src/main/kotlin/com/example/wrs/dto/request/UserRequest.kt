//requestの型定義

package com.example.wrs.dto
//新規登録
data class UserRequest(
    var name: String,
    val mail: String,
    val password: String
)
//ログイン
data class LoginRequest(
    val mail: String,
    val password: String
)