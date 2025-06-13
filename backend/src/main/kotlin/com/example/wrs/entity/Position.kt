package com.example.wrs.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime


//散歩中に取得した緯度・経度を30秒ごとに記録するエンティティ。
@Entity
data class Position(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "walk_id", nullable = false)
    var walk: Walk? = null,

    var lat: Double,
    var lng: Double,

    @CreationTimestamp
    @Column(updatable = false)
    var timestamp: LocalDateTime? = null
)