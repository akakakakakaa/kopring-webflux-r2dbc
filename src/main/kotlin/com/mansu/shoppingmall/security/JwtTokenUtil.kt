package com.mansu.shoppingmall.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtTokenUtil(
    @Value("#{jwt.access.secret}")
    val accessSecret: String,
    @Value("#{jwt.access.expirationTimeMillis}")
    val accessExpirationTimeMillis: Int,
    @Value("#{jwt.refresh.secret}")
    val refreshSecret: String,
    @Value("#{jwt.refresh.expirationTimeMillis}")
    val refreshExpirationTimeMillis: Int,
) {
    fun accessToken(username: String, roles: Array<String>): String {
        return generate(username, accessExpirationTimeMillis, roles, accessSecret)
    }

    fun decodeAccessToken(accessToken: String): DecodedJWT {
        return decode(accessSecret, accessToken)
    }

    fun refreshToken(username: String, roles: Array<String>): String {
        return generate(username, refreshExpirationTimeMillis, roles, refreshSecret)
    }

    fun decodeRefreshToken(refreshToken: String): DecodedJWT {
        return decode(refreshSecret, refreshToken)
    }

    fun getRoles(decodedJWT: DecodedJWT) = decodedJWT.getClaim("role").asList(String::class.java)
        .map { SimpleGrantedAuthority(it) }

    private fun generate(username: String, expirationInMillis: Int, roles: Array<String>, signature: String): String {
        return JWT.create()
            .withSubject(username)
            .withExpiresAt(Date(System.currentTimeMillis() + expirationInMillis))
            .withArrayClaim("role", roles)
            .sign(Algorithm.HMAC512(signature.toByteArray()))
    }

    private fun decode(signature: String, token: String): DecodedJWT {
        return JWT.require(Algorithm.HMAC512(signature.toByteArray()))
            .build()
            .verify(token.replace("Bearer ", ""))
    }
}