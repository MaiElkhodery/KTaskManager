package com.Kmongo.data

import com.Kmongo.models.SignupRequest
import com.Kmongo.models.User
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.reactivestreams.client.FindPublisher
import kotlinx.coroutines.reactive.collect
import org.bson.types.ObjectId
import org.litote.kmongo.reactivestreams.getCollection
import org.reactivestreams.Publisher

class UserRepository : UserInterface {

    private val userCollection = Database.getDatabase().getCollection<User>()
    override suspend fun add(user: SignupRequest): String? {
        val userId = ObjectId().toHexString()
        userCollection.insertOne(
            User(
                id = userId,
                name = user.name,
                password = user.password,
                email = user.email
            )
        ).collect { result ->
            if (result.wasAcknowledged()) {
                return userId
            }
        }
        return null
    }

    override suspend fun updateUsername(username: String, id: String) {
        userCollection.updateOne(
            Filters.eq(User::id.name, id),
            Updates.set(User::name.name, username)
        )
    }

    override suspend fun updateEmail(email: String, id: String) {
        userCollection.updateOne(
            Filters.eq(User::id.name, id),
            Updates.set(User::email.name, email)
        )
    }

    override suspend fun delete(id: String): Publisher<User>? {
        return userCollection.findOneAndDelete(
            Filters.eq(User::id.name, id)
        )
    }

    override suspend fun get(): FindPublisher<User>? {
        return userCollection.find()
    }

    override suspend fun get(id: String): FindPublisher<User>? {
        return userCollection.find(
            Filters.eq(User::id.name, id)
        )
    }

    override suspend fun get(email: String, password: String): FindPublisher<User>? {
        return userCollection.find(
            Filters.and(
                Filters.eq(User::email.name, email),
                Filters.eq(User::password.name, password)
            )
        )
    }
}