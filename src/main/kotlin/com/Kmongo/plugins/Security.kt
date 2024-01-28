package com.Kmongo.plugins

import com.Kmongo.utils.JWTConfig
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureSecurity() {
    authentication {
        jwt(name = "jwt-auth") {
            realm = JWTConfig.REALM
            verifier(
                JWT
                    .require(Algorithm.HMAC256(JWTConfig.SECRET))
                    .withAudience(JWTConfig.AUDIENCE)
                    .withIssuer(JWTConfig.ISSUER)
                    .build()
            )
            validate { credential ->
                if (!credential.payload.getClaim("email").asString().isNullOrEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}
