package com.mansu.shoppingmall.controller

import com.mansu.shoppingmall.dto.MemberFormDto
import com.mansu.shoppingmall.model.Member
import com.mansu.shoppingmall.service.MemberService
import jakarta.validation.Valid
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(val memberService: MemberService, val passwordEncoder: PasswordEncoder) {
    @PostMapping(value = ["/new"])
    suspend fun newMember(@Valid memberFormDto: MemberFormDto): String {
        val member: Member = Member.createMember(memberFormDto, passwordEncoder);
        memberService.saveMember(member);

        return "redirect:/";
    }
}