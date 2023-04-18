package com.mansu.shoppingmall.repository

import com.mansu.shoppingmall.model.Member
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository: CoroutineCrudRepository<Member, Long> {
    suspend fun findByEmail(email: String): Member?
}