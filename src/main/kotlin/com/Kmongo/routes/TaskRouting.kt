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
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.reactive.collect
import org.litote.kmongo.coroutine.toList

fun Route.taskRoute() {
    val taskRepo = TaskRepository()
    authenticate("jwt-auth") {
        route("/tasks") {

            get {

                val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("id").asString()

                try {

                    val priorityParam = call.request.queryParameters[PRIORITY]?.uppercase()
                    val statusParam = call.request.queryParameters[STATUS]?.uppercase()

                    val tasks: FindPublisher<Task>? = if (priorityParam != null && statusParam != null) {

                        taskRepo.get(
                            priority = Priority.valueOf(priorityParam),
                            status = Status.valueOf(statusParam),
                            userId = userId
                        )

                    } else if (priorityParam != null) {

                        taskRepo.get(priority = Priority.valueOf(priorityParam), userId = userId)

                    } else if (statusParam != null) {

                        taskRepo.get(status = Status.valueOf(statusParam), userId = userId)

                    } else {

                        taskRepo.get(userId)

                    }
                    tasks?.collect {
                        call.respond(tasks.limit(20).toList())
                    } ?: throw IllegalArgumentException("No tasks found")

                } catch (e: Exception) {

                    call.respond(HttpStatusCode.BadRequest, e.message.toString())

                }
            }
            post {

                try {

                    val task = call.receive<TaskRequest>()
                    val principal = call.principal<JWTPrincipal>()


                    val userId = principal!!.payload.getClaim("id").asString()
                    val result = taskRepo.add(task, userId)
                    result?.collect {
                        if (it.wasAcknowledged())
                            call.respond(HttpStatusCode.Created, "${task.title} is Created")
                        else
                            call.respond(HttpStatusCode.NotImplemented, "Try Again")
                    }

                } catch (e: Exception) {

                    call.respond(HttpStatusCode.BadRequest, e.message.toString())

                }

            }

            delete("/{id}") {

                try {

                    val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("id").asString()
                    val taskId = call.parameters["id"]
                    println("task id:$taskId")
                    if (taskId != null) {

                        val deletedTask = taskRepo.delete(
                            taskId = taskId,
                            userId = userId
                        )
                        deletedTask?.collect { task ->
                            call.respond(HttpStatusCode.OK, "${task.title} Task is deleted")
                        }

                    } else
                        call.respond("Invalid Task ID")

                } catch (e: Exception) {

                    call.respond(HttpStatusCode.BadRequest, e.message.toString())

                }

            }

            put {
                try {

                    val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("id").asString()
                    val task = call.receive<Task>()
                    val updatedTask = taskRepo.update(task, userId)

                    updatedTask?.collect {
                        call.respond(HttpStatusCode.OK, "${it.title} Task is Updated")
                    }

                } catch (e: Exception) {

                    call.respond(HttpStatusCode.BadRequest, e.message.toString())

                }
            }
        }
    }
}
