package com.Kmongo.plugins

import com.Kmongo.routes.taskRoute
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        taskRoute()
    }
}
