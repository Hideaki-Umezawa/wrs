package com.example.wrs.controller

import com.example.wrs.dto.PositionRequest
import com.example.wrs.dto.PositionResponse
import com.example.wrs.entity.Position
import com.example.wrs.repository.PositionRepository
import com.example.wrs.repository.UserRepository
import com.example.wrs.repository.WalkRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/walks/{walkId}/positions")
class PositionController(
    private val walkRepository: WalkRepository,
    private val positionRepository: PositionRepository,
    private val userRepository: UserRepository
) {

    //めっちゃ呼ばれると思う
    //指定されたwalkIdのデータを追加 (post) /api/walks/{walkId}/positions
    @PostMapping
    fun addPosition(
        @RequestBody request: PositionRequest,
        @PathVariable walkId: Long
    ): ResponseEntity<PositionResponse> {
        val walkOpt = walkRepository.findById(walkId)
        if (walkOpt.isEmpty) {
            println("🟥ユーザー見つからない=${walkId}")
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "ユーザーが見つかりません")
        }

        val walk = walkOpt.get()

        val newPosition = Position(walk = walk, lat = request.lat, lng = request.lng, timestamp = LocalDateTime.now())
        val saved = positionRepository.save(newPosition)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(PositionResponse(id = saved.id!!, lat = saved.lat, lng = saved.lng, timestamp = saved.timestamp!!))
    }


    // walkIdの一覧取得
    @GetMapping
    fun getAllPositions(@PathVariable walkId: Long): ResponseEntity<List<PositionResponse>> {
        val walkOpt = walkRepository.findById(walkId)
        if (walkOpt.isEmpty) {
            println("🟥ユーザー見つからない=${walkId}")
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "ユーザーが見つかりません")
        }
        val walk = walkOpt.get()
        val list = positionRepository.findAllByWalk(walk).map {
            PositionResponse(
                id = it.id!!,
                lat = it.lat,
                lng = it.lng,
                timestamp = it.timestamp!!
            )
        }
        return ResponseEntity.ok(list)
    }


}