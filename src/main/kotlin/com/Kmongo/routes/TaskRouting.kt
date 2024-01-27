package com.Kmongo.routes

import com.Kmongo.data.TaskRepository
import com.Kmongo.models.Task
import com.Kmongo.models.TaskRequest
import com.Kmongo.utils.PRIORITY
import com.Kmongo.utils.Priority
import com.Kmongo.utils.STATUS
import com.Kmongo.utils.Status
import com.mongodb.reactivestreams.client.FindPublisher
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactive.collect
import kotlinx.coroutines.withContext
import org.litote.kmongo.coroutine.toList

fun Route.taskRoute() {
    val taskRepo = TaskRepository()
    route("/tasks") {

        get {

            try {

                val priorityParam = call.request.queryParameters[PRIORITY]?.uppercase()
                val statusParam = call.request.queryParameters[STATUS]?.uppercase()
                val tasks: FindPublisher<Task>?

                if (priorityParam != null && statusParam != null) {
                    tasks = taskRepo.get(
                        priority = Priority.valueOf(priorityParam),
                        status = Status.valueOf(statusParam)
                    )
                } else if (priorityParam != null) {
                    tasks = taskRepo.get(priority = Priority.valueOf(priorityParam))
                } else if (statusParam != null) {
                    tasks = taskRepo.get(status = Status.valueOf(statusParam))
                } else {
                    tasks = taskRepo.get()
                }
                if (tasks != null) {
                    call.respond(tasks.limit(20).toList())
                } else
                    call.respond("nothing matched")

            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, "Error Occurred")
                println(e.message)

            }
        }
        post {
            withContext(Dispatchers.IO) {
                try {

                    val task = call.receive<TaskRequest>()
                    taskRepo.add(task)
                    call.respond(HttpStatusCode.Created, "done")

                } catch (e: Exception) {

                    call.respond(HttpStatusCode.BadRequest, "Error Occurred")
                    println(e.message)

                }
            }
        }

        delete("/{id}") {

            try {
                val id = call.parameters["id"]
                if (id != null) {
                    val deletedTask = taskRepo.delete(id)
                    deletedTask?.collect {
                        call.respond(HttpStatusCode.OK, "${it.title} Task is deleted")
                    }
                } else
                    call.respond(HttpStatusCode.BadRequest)

            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error Occurred")
                println(e.message)
            }

        }

        put {
            try {
                val task = call.receive<Task>()
                val updatedTask = taskRepo.update(task)

                updatedTask?.collect {
                    call.respond(HttpStatusCode.OK, "${it.title} Task is Updated")
                }

            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error Occurred")
                println(e.message)
            }
        }
    }
}