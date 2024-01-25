package com.Kmongo.routes

import com.Kmongo.MongoDatabase
import com.Kmongo.models.Task
import com.Kmongo.utils.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.bson.Document
import org.bson.types.ObjectId

fun Route.taskRoute() {
    route("/tasks") {
        val database = MongoDatabase.CLIENT.getDatabase(TASK_DB)
        database.createCollection(TASK_COLLECTION)
        val collection = database.getCollection(TASK_COLLECTION)
        get {

        }
        post {
            val task = call.receive<Task>()
            collection.insertOne(
                Document("_id", ObjectId())
                    .append(TITLE, task.title)
                    .append(DESCRIPTION, task.description)
                    .append(START_DATE, task.startDate)
                    .append(END_DATE, task.endDate)
                    .append(PRIORITY, task.priority)
                    .append(START_DATE, task.status)
            )
        }
    }
}