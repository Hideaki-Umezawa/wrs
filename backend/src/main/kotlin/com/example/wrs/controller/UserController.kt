//エンドポイントを作成
package com.example.wrs.controller

import com.example.wrs.entity.User
import com.example.wrs.repository.UserRepository
import com.example.wrs.dto.LoginRequest
import com.example.wrs.dto.PositionResponse
import com.example.wrs.dto.UserRequest
import com.example.wrs.dto.UserResponse
import com.example.wrs.dto.WalkResponse
import com.example.wrs.repository.PositionRepository
import com.example.wrs.repository.WalkRepository
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

@RestController
@RequestMapping("/api/users")
class UserController(
    private val walkRepository: WalkRepository,
    private val positionRepository: PositionRepository,
    private val userRepository: UserRepository
) {

    //新規登録 (post) /api/users
    @PostMapping
    fun createUser(@RequestBody request: UserRequest): ResponseEntity<UserResponse> {
        //requestデータをdbに保存するためUserエンティティに変換
        val user = User(mail = request.mail, password = request.password, name = request.name)
        val saved = userRepository.save(user)
        return ResponseEntity.ok(UserResponse(saved.id!!, saved.name, saved.mail))
    }

    //ユーザー全取得 (get) /api/users
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        val usersArray = userRepository.findAll()
        val res = usersArray.map { UserResponse(it.id!!, it.name, it.mail) }
        return ResponseEntity.ok(res)
    }

    //ログイン (post) /api/users/login
    @PostMapping("/login")
    fun loginUser(@RequestBody request: LoginRequest): ResponseEntity<UserResponse> {
        val res = userRepository.findByMailAndPassword(request.mail, request.password)
        //optじゃないからnull比較
        if (res == null) {
            println("ログイン失敗:メール=${request.mail}")
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "メールアドレスまたはパスワードが違います")
        }
        println("ログイン成功:ユーザーID=${res.id}")
        return ResponseEntity.ok(UserResponse(res.id!!, res.name, res.mail))
    }

    //ユーザー情報取得 (get) /api/users/id　あんまいらないかも管理者用かな
    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") id: Long): ResponseEntity<UserResponse> {
        val resOpt = userRepository.findById(id)
        if (resOpt.isEmpty) {
            println("ユーザー見つからないid=${id}")
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "ユーザーが見つかりません")
        }
        val user = resOpt.get()
        println("ユーザーID=${user}")
        return ResponseEntity.ok(UserResponse(user.id!!, user.name, user.mail))
    }

    //ユーザー更新　(put) /api/users/id
    @PutMapping("/{id}")
    fun updateUserById(@PathVariable("id") id: Long, @RequestBody request: UserRequest): ResponseEntity<UserResponse> {
        val resOpt = userRepository.findById(id)
        if (resOpt.isEmpty) {
            println("ユーザー見つからないid==${id}")
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "ユーザー見つかリマ戦")
        }
        val res = resOpt.get()
        res.name = request.name
        res.mail = request.mail
        res.password = request.password
        val updated = userRepository.save(res)
        return ResponseEntity.ok(UserResponse(updated.id!!, updated.name, updated.mail))
    }

    //アカウント削除　(delete)
    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable("id") id: Long): ResponseEntity<Void> {
        val resOpt = userRepository.findById(id)
        if (resOpt.isEmpty) {
            println("ユーザー見つからないid=${id}")
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "ユーザー見つかりません")
        }

        userRepository.deleteById(id)
        println("ユーザーデータ削除id=${id}")
        return ResponseEntity.noContent().build() //204投げる
    }

    @GetMapping("/{userId}/walks")
    fun getWalks(@PathVariable userId: Long): ResponseEntity<List<WalkResponse>> {
        val user = userRepository.findById(userId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "ユーザーが見つかりません") }

        val walks = walkRepository.findAllByUser(user)

        val response = walks.map {
            WalkResponse(
                id = it.id!!,
                startTime = it.startTime!!,
                endTime = it.endTime
            )
        }

        return ResponseEntity.ok(response)
    }
}

