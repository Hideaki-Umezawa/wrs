// SQLを書かずに Java APIを使用しオブジェクト ("エンティティ") をあたかも 直接データベースに保存 (save)、読み込み (find) するような処理を書くことができる。
package com.example.wrs

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

//アノーテーション データベースのテーブルとして扱うことを命令
@Entity
class WrsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Longはjavaのプリミティブ型(64ビット符号付き整数)
    var id: Long? = null,
    var text: String,
    var originLat: Double,
    var originLng: Double,
    var destLat: Double,
    var destLng: Double,

//自動で時間がセットされる
    @CreationTimestamp
    @Column(updatable = false)
    var createdAt: LocalDateTime? = null
)
