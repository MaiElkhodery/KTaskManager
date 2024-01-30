package com.Kmongo.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import java.util.*

object JWTConfig {

    private val config = HoconApplicationConfig(ConfigFactory.load())

    val SECRET = config.property("jwt.secret").getString()
    val ISSUER = config.property("jwt.issuer").getString()
    val AUDIENCE = config.property("jwt.audience").getString()
    val REALM = config.property("jwt.realm").getString()
    val EXPIRE_TIME = 86400000

    fun getToken(id: String): String {
        return JWT.create()
            .withAudience(AUDIENCE)
            .withIssuer(ISSUER)
            .withClaim("id", id)
            .withExpiresAt(Date(System.currentTimeMillis() + EXPIRE_TIME))
            .sign(Algorithm.HMAC256(SECRET))
    }
}