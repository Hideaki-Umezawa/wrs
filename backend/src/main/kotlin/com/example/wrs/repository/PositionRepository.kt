//データベースとのやりとりsplの代わりJPIが自動で実施してくれる
package com.example.wrs.repository

import com.example.wrs.entity.Position
import com.example.wrs.entity.User
import com.example.wrs.entity.Walk
import org.springframework.data.jpa.repository.JpaRepository

interface PositionRepository : JpaRepository<Position, Long> {
    fun findAllByWalk(walk: Walk): List<Position>
}
