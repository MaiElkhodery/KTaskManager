package com.Kmongo.plugins

import com.Kmongo.routes.taskRoute
import com.Kmongo.routes.userRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        taskRoute()
        userRoute()
    }
}
