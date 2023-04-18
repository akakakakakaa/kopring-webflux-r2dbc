package com.mansu.shoppingmall.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import org.hibernate.validator.constraints.Length

class MemberFormDto(
    @field:NotBlank(message = "이름은 필수 입력 값입니다.")
    val name: String,
    @field:NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @field:Email(message = "이메일 형식으로 입력해주세요.")
    val email: String,
    @field:NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @field:Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
    val password: String,
    @field:NotEmpty(message = "주소는 필수 입력 값입니다.")
    val address: String,
)