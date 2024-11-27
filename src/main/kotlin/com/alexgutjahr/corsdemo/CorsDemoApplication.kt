package com.alexgutjahr.corsdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class CorsDemoApplication

fun main(args: Array<String>) {
    runApplication<CorsDemoApplication>(*args)
}

@RestController
class PingController {
    @GetMapping("/ping")
    fun ping() = "pong"
}