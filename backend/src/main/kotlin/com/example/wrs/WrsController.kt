package com.example.wrs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
// /listのエンドポイントを一括りにする
@RequestMapping("/api/lists")
class WrsController(@Autowired private val repository: WrsRepository) {

    // GET /lists 叩くと全件取得
    // Jackson が自動で WrsEntity → JSON に変換して返却
    @GetMapping
    fun getLists() = repository.findAll().toList()


    // POST /lists 叩くとリクエストを受け取って保存、保存したエンティティを返却
    // Jackson が自動で JSON → WrsRequest に変換
    @PostMapping
    fun saveLists(@RequestBody req: WrsRequest): Long {
        val lists = WrsEntity(
            text = req.text,
            originLat = req.originLat,
            originLng = req.originLng,
            destLat = req.destLat,
            destLng = req.destLng
            // createdAtは　@CreationTimestampで自動設定
        )
        val saved = repository.save(lists)
        println("🟦${saved.id}")
        return saved.id!!
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)//明示的に200を返す
    //urlのidを取り足して引数として使用する　id=5L　とか受け取る
    fun getLists(@PathVariable id: Long): WrsEntity {
        //.orElseThrowはエラーのときにだけ動く
        return repository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "ID $id が見つかりません")
        }
    }

    //curlで実施するとCSRF違反
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204を返す（削除成功時に返す）
    fun deleteList(@PathVariable id: Long) {
        val entity = repository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "ID $id が見つかりません")
        }
        repository.delete(entity)
    }
}