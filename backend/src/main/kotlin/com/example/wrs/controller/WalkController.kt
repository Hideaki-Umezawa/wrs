package com.example.wrs.controller

import com.example.wrs.dto.EndWalkResponse
import com.example.wrs.dto.PositionResponse
import com.example.wrs.dto.StartWalkResponse
import com.example.wrs.dto.UserResponse
import com.example.wrs.dto.WalkRequest
import com.example.wrs.entity.User
import com.example.wrs.entity.Walk
import com.example.wrs.repository.PositionRepository
import com.example.wrs.repository.UserRepository
import com.example.wrs.repository.WalkRepository
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.util.Optional

@RestController
@RequestMapping("/api/walks")
class WalkController(
    private val walkRepository: WalkRepository,
    private val positionRepository: PositionRepository,
    private val userRepository: UserRepository
) {

    //散歩開始 userIdからユーザーを探してsaveする
    @PostMapping
    fun startWalk(@RequestBody request: WalkRequest): ResponseEntity<StartWalkResponse> {
        val userOpt = userRepository.findById(request.userId)
        if (userOpt.isEmpty) {
            println("🟥ユーザー見つからない=${request.userId}")
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "ユーザーが見つかりません")
        }
        val user = userOpt.get()
        val walk = Walk(user = user)
        val saved = walkRepository.save(walk)
        //登録完了ステータス201を返す
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(StartWalkResponse(id = saved.id!!, startTime = saved.startTime!!))
    }


    //散歩終了 (put) /api/walks/{id}/end
    @PutMapping("/{id}/end")
    fun endWalk(@PathVariable id: Long): ResponseEntity<EndWalkResponse> {
        val walkOpt = walkRepository.findById(id)
        if (walkOpt.isEmpty) {
            println("🟥Walkが見つからない=${id}")
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Walk ID $id が見つかりません")
        }
        val walk = walkOpt.get()
        walk.endTime = LocalDateTime.now()
        val saved = walkRepository.save(walk)
        println("✅ endTime更新後: ${saved.endTime}")
        //更新は200
        return ResponseEntity.ok(EndWalkResponse(id = saved.id!!, endTime = saved.endTime!!))
    }

    //散歩削除  (delete) /api/{id}
    @DeleteMapping("/{id}")
    fun deleteWalk(@PathVariable id: Long): ResponseEntity<Void> {
        val walkOpt = walkRepository.findById(id)
        if (walkOpt.isEmpty) {
            println("🟥Walkが見つからない=${id}")
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Walk ID $id が見つかりません")
        }
        walkRepository.deleteById(id)
        println("ユーザーデータ削除id=${id}")
        //204返す
        return ResponseEntity.noContent().build()
    }


}