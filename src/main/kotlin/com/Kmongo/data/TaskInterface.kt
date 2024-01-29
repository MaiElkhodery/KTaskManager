package com.Kmongo.data

import com.Kmongo.models.Task
import com.Kmongo.models.TaskRequest
import com.Kmongo.utils.Priority
import com.Kmongo.utils.Status
import com.mongodb.client.result.InsertOneResult
import com.mongodb.reactivestreams.client.FindPublisher
import org.reactivestreams.Publisher

interface TaskInterface {
    suspend fun add(task: TaskRequest,userId:String):Publisher<InsertOneResult>?
    suspend fun update(updatedTask: Task,userId:String):Publisher<Task>?

    suspend fun delete(taskId: String,userId:String): Publisher<Task>?
    suspend fun get(userId: String): FindPublisher<Task>?

    suspend fun get(priority: Priority,userId: String): FindPublisher<Task>?
    suspend fun get(status: Status,userId: String): FindPublisher<Task>?
    suspend fun get(priority: Priority, status: Status,userId: String): FindPublisher<Task>?

}