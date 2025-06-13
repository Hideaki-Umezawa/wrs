package com.example.wrs.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime


//散歩の開始・終了情報を管理するエンティティ。
@Entity
data class Walk(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    //多対1を定義　user.idで取得可能になる
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,

    //散歩開始時刻
    @CreationTimestamp
    @Column(updatable = false)
    var startTime: LocalDateTime? = null,

    var endTime: LocalDateTime? = null
)