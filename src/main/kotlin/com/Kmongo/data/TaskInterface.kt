package com.Kmongo.data

import com.Kmongo.models.Task
import com.Kmongo.models.TaskRequest
import com.Kmongo.utils.Priority
import com.Kmongo.utils.Status
import com.mongodb.reactivestreams.client.FindPublisher
import org.bson.types.ObjectId
import org.reactivestreams.Publisher

interface TaskInterface {
    suspend fun add(task: TaskRequest)
    suspend fun update(updatedTask: Task)

    suspend fun delete(id: String): Publisher<Task>?
    suspend fun get(): FindPublisher<Task>?

    suspend fun get(priority: Priority): FindPublisher<Task>?
    suspend fun get(status: Status): FindPublisher<Task>?
    suspend fun get(priority: Priority, status: Status): FindPublisher<Task>?

}