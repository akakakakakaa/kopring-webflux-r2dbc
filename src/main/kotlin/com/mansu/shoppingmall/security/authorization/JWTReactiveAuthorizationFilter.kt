package com.mansu.shoppingmall.security.authorization

import com.mansu.shoppingmall.security.JwtTokenUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

class JWTReactiveAuthorizationFilter(private val jwtTokenUtil: JwtTokenUtil) : WebFilter {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val authHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION) ?: return chain.filter(exchange)

        if (!authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange)
        }

        try {
            val token = jwtTokenUtil.decodeAccessToken(authHeader)
            val auth = UsernamePasswordAuthenticationToken(token.subject, null, jwtTokenUtil.getRoles(token))
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth))
        } catch (e: Exception) {
            logger.error("JWT exception", e)
        }

        return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.clearContext())
    }
}