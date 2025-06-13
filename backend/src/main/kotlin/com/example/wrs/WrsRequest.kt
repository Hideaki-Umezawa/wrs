//「リクエスト」の形を定義するファイル
package com.example.wrs

import java.util.UUID

//データの型の定義  idとcreatedAtは自動生成
data class WrsRequest(
    val text: String,
    val originLat: Double,
    val originLng: Double,
    val destLat: Double,
    val destLng: Double
)
