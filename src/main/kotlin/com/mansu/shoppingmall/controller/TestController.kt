package com.mansu.shoppingmall.controller

import com.mansu.shoppingmall.dto.UserDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping(value = ["/test"])
    fun test(): UserDto = UserDto(name = "hoon", age = 20)
}