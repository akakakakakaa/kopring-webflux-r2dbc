package com.mansu.shoppingmall.security.authentication

import com.mansu.shoppingmall.security.JwtTokenUtil
import kotlinx.coroutines.reactor.mono
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Component
class JWTServerAuthenticationSuccessHandler(private val jwtTokenUtil: JwtTokenUtil) : ServerAuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(webFilterExchange: WebFilterExchange?, authentication: Authentication?): Mono<Void> = mono {
        val principal = authentication?.principal ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized")

        when (principal) {
            is User -> {
                val roles = principal.authorities.map { it.authority }.toTypedArray()
                val accessToken = jwtTokenUtil.accessToken(principal.username, roles)
                val refreshToken = jwtTokenUtil.refreshToken(principal.username, roles)
                webFilterExchange?.exchange?.response?.headers?.set("Authorization", accessToken)
                webFilterExchange?.exchange?.response?.headers?.set("JWT-Refresh-Token", refreshToken)
            }
        }

        return@mono null
    }
}
