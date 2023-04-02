package com.mansu.shoppingmall

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RestController

@RestController
@SpringBootApplication
class ShoppingmallApplication

fun main(args: Array<String>) {
    runApplication<ShoppingmallApplication>(*args)
}
