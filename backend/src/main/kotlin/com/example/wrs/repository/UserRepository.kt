//データベースとのやりとりsplの代わりJPIが自動で実施してくれる

package com.example.wrs.repository

import com.example.wrs.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

//メールアドレスとパスワードを検索する関数定義しとく　使うかわからんけどね
@Repository
interface UserRepository : JpaRepository<User, Long> {
   fun findByMailAndPassword(mail: String, password: String): User?
}