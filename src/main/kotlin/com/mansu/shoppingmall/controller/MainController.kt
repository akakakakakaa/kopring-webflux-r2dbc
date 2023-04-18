package com.mansu.shoppingmall.controller

import com.mansu.shoppingmall.dto.UserDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {
    @GetMapping(value = ["/"])
    fun test(): String = "main"
}