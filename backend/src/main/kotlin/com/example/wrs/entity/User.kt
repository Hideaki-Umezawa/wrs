package com.example.wrs.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table


//ユーザー情報を管理するエンティティ。
@Entity
@Table(name = "app_user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,


    @Column(unique = true)//重複を防ぐ
    var mail: String,

    var password: String,

    var name: String
)