package com.mansu.shoppingmall.model

import com.mansu.shoppingmall.dto.MemberFormDto
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import type.Role
import org.springframework.security.crypto.password.PasswordEncoder

@Table("member")
class Member (
    @Id var id: Long? = null,
    val name: String,
    val email: String,
    val password: String,
    val address: String,
    val role: Role,
) {
    companion object {
        fun createMember(memberFormDto: MemberFormDto, passwordEncoder: PasswordEncoder): Member {
            return Member(
                name = memberFormDto.name,
                email = memberFormDto.email,
                address = memberFormDto.address,
                password = passwordEncoder.encode(memberFormDto.password),
                role = Role.USER
            )
        }
    }
}