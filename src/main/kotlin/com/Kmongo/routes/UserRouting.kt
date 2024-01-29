package com.Kmongo.routes

import com.Kmongo.data.UserRepository
import com.Kmongo.models.LoginRequest
import com.Kmongo.models.SignupRequest
import com.Kmongo.utils.JWTConfig
import com.Kmongo.utils.validateEmail
import com.Kmongo.utils.validatePassword
import com.Kmongo.utils.validateUsername
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.reactive.collect

fun Route.userRoute() {

    val userRepo = UserRepository()

    post("/login") {
        try {

            val user = call.receive<LoginRequest>()
            validateEmail(user.email)
            validatePassword(user.password)

            userRepo.get(
                email = user.email,
                password = user.password
            )?.collect { loggedUser ->
                println("user id: ${loggedUser.id}")
                val token = JWTConfig.getToken(loggedUser.id)
                call.respond(mapOf("token" to token))

            } ?: throw IllegalArgumentException("Invalid email or password")

        } catch (e: Exception) {

            call.respond(e.message.toString())

        }
    }
    post("/signup") {

        try {

            val user = call.receive<SignupRequest>()

            validateUsername(user.name)
            validateEmail(user.email)
            validatePassword(user.password)

            val userId = userRepo.add(user)

            if (userId != null) {
                val token = JWTConfig.getToken(userId)
                call.respond(mapOf("token" to token))
            } else {
                throw IllegalArgumentException("Failed to retrieve insertedId after user creation")
            }

        } catch (e: Exception) {
            call.respond(e.message.toString())
        }
    }
}