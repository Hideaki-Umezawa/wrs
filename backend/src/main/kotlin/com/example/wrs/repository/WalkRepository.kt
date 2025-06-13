//データベースとのやりとりsplの代わりJPIが自動で実施してくれる
package com.example.wrs.repository

import com.example.wrs.entity.User
import com.example.wrs.entity.Walk
import org.springframework.data.jpa.repository.JpaRepository


interface WalkRepository : JpaRepository<Walk, Long> {
    fun findAllByUser(user: User): List<Walk>
}
