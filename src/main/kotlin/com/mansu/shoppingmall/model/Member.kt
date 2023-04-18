package com.mansu.shoppingmall.model

import com.mansu.shoppingmall.dto.MemberFormDto
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.GrantedAuthority
import type.Role
import org.springframework.security.crypto.password.PasswordEncoder

@Table("member")
data class Member (
    @Id var id: Long? = null,
    val name: String,
    val email: String,
    val password: String,
    val address: String,
    val role: Role,
): GrantedAuthority {
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

    override fun equals(other: Any?) = other is Member && EssentialMemberData(this) == EssentialMemberData(other)
    override fun hashCode() = EssentialMemberData(this).hashCode()
    override fun getAuthority(): String {
        return "ROLE_$role"
    }
}

private data class EssentialMemberData(val id: Long?) {
    constructor(member: Member) : this(member.id)
}