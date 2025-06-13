package com.example.wrs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WrsApplication

fun main(args: Array<String>) {
// ここでSpringBoot起動する
	runApplication<WrsApplication>(*args)
}
