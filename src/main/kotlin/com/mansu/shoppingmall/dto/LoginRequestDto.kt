package com.mansu.shoppingmall.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequestDto (
    @field:Email(regexp = ".+@.+\\..+")
    @field:NotBlank
    val username: String,
    @field:NotBlank
    @field:Size(min = 8, max = 16)
    val password: String
)