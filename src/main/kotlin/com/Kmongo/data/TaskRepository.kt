package com.Kmongo.data

import com.Kmongo.models.Task
import com.Kmongo.models.TaskRequest
import com.Kmongo.utils.*
import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.FindPublisher
import org.bson.types.ObjectId
import org.litote.kmongo.reactivestreams.getCollection

class TaskRepository : TaskInterface {

    private val collection = Database.getDatabase()?.getCollection<Task>()
    override suspend fun add(task: TaskRequest) {
        val result = collection?.insertOne(
            Task(
                _id = ObjectId().toHexString(),
                title = task.title,
                description = task.description,
                startDate = task.startDate,
                endDate = task.endDate,
                priority = task.priority,
                status = task.status
            )
        )
    }

    override suspend fun update(updatedTask: Task) {

        collection?.replaceOne(
            Filters.eq(ID, updatedTask._id),
            updatedTask
        )
    }

    override suspend fun delete(id: ObjectId) {

        collection?.deleteOne(
            Filters.eq(
                ID, id
            )
        )
    }

    override suspend fun get(): FindPublisher<Task>? {
        return collection?.find()
    }

    override suspend fun get(priority: Priority): FindPublisher<Task>? {
        return collection?.find(
            Filters.eq(Task::priority.name, priority.name)
        )
    }

    override suspend fun get(status: Status): FindPublisher<Task>? {
        return collection?.find(
            Filters.eq(STATUS, status.name)
        )
    }

    override suspend fun get(priority: Priority, status: Status): FindPublisher<Task>? {
        return collection?.find(
            Filters.and(
                Filters.eq(PRIORITY, priority.name),
                Filters.eq(STATUS, status.name)
            )
        )
    }

}