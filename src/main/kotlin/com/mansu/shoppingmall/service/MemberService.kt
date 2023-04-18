package com.mansu.shoppingmall.service

import com.mansu.shoppingmall.model.Member
import com.mansu.shoppingmall.repository.MemberRepository
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
@Transactional
class MemberService(val memberRepository: MemberRepository): ReactiveUserDetailsService {
    suspend fun saveMember(member: Member): Member {
        validateDuplicateMember(member)
        return memberRepository.save(member)
    }

    suspend fun validateDuplicateMember(member: Member) {
        val findMember: Member? = memberRepository.findByEmail(member.email)
        if (findMember != null) {
            throw IllegalStateException("이미 가입한 회원입니다.")
        }
    }

    @Throws(UsernameNotFoundException::class)
    override fun findByUsername(username: String?): Mono<UserDetails> = mono {
        val member = memberRepository.findByEmail(username!!) ?: throw UsernameNotFoundException("email not found")

        return@mono User(member.email, member.password, listOf(member))
    }
}