package com.example.wrs

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

//欲しいデータを宣言している
@Repository
interface WrsRepository : CrudRepository<WrsEntity, Long>