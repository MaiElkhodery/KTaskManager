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

            val getUser = userRepo.get(
                email = user.email,
                password = user.password
            )
            if (getUser != null) {

                val token = JWTConfig.getToken(user.email)
                call.respond(mapOf("token" to token))

            } else {

                throw IllegalArgumentException("Invalid email or password")

            }
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

            userRepo.add(user)?.collect {
                println("insertion result: ${it.insertedId}")
                println("insertion result: ${it.wasAcknowledged()}")
            }

            val token = JWTConfig.getToken(user.email)

            call.respond(mapOf("token" to token))

        } catch (e: Exception) {
            call.respond(e.message.toString())
        }
    }
}