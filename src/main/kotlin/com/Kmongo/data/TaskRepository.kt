package com.Kmongo.data

import com.Kmongo.models.Task
import com.Kmongo.models.TaskRequest
import com.Kmongo.utils.*
import com.mongodb.client.model.Filters
import com.mongodb.client.result.InsertOneResult
import com.mongodb.reactivestreams.client.FindPublisher
import org.bson.types.ObjectId
import org.litote.kmongo.reactivestreams.getCollection
import org.reactivestreams.Publisher

class TaskRepository : TaskInterface {

    private val taskCollection = Database.getDatabase().getCollection<Task>()
    override suspend fun add(task: TaskRequest,userId:String): Publisher<InsertOneResult>? {
        return taskCollection.insertOne(
            Task(
                _id = ObjectId().toHexString(),
                userId = userId,
                title = task.title,
                description = task.description,
                startDate = task.startDate,
                endDate = task.endDate,
                priority = task.priority,
                status = task.status
            )
        )
    }

    override suspend fun update(updatedTask: Task, userId: String): Publisher<Task>? {

        return taskCollection.findOneAndReplace(
            Filters.and(
                Filters.eq(Task::_id.name, updatedTask._id),
                Filters.eq(Task::userId.name, userId)
            ),
            updatedTask
        )
    }

    override suspend fun delete(taskId: String, userId: String): Publisher<Task>? {

        return taskCollection.findOneAndDelete(
            Filters.and(
                Filters.eq(Task::_id.name, taskId),
                Filters.eq(Task::userId.name, userId)
            )
        )
    }

    override suspend fun get( userId: String): FindPublisher<Task>? {
        return taskCollection.find(
            Filters.eq(Task::userId.name, userId)
        )
    }

    override suspend fun get(priority: Priority, userId: String): FindPublisher<Task>? {
        return taskCollection.find(
            Filters.and(
                Filters.eq(Task::priority.name, priority.name),
                Filters.eq(Task::userId.name, userId)
            )
        )
    }

    override suspend fun get(status: Status, userId: String): FindPublisher<Task>? {
        return taskCollection.find(
            Filters.and(
                Filters.eq(STATUS, status.name),
                Filters.eq(Task::userId.name, userId)
            )
        )
    }

    override suspend fun get(priority: Priority, status: Status, userId: String): FindPublisher<Task>? {
        return taskCollection.find(
            Filters.and(
                Filters.eq(PRIORITY, priority.name),
                Filters.eq(STATUS, status.name),
                Filters.eq(Task::userId.name, userId)
            )
        )
    }

}