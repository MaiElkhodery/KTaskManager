package com.Kmongo.data

import com.Kmongo.models.SignupRequest
import com.Kmongo.models.User
import com.mongodb.client.result.InsertOneResult
import com.mongodb.reactivestreams.client.FindPublisher
import org.reactivestreams.Publisher

interface UserInterface {
    suspend fun add(user: SignupRequest):Publisher<InsertOneResult>?
    suspend fun updateUsername(username: String, id: String)
    suspend fun updateEmail(email: String, id: String)

    suspend fun delete(id: String): Publisher<User>?
    suspend fun get(): FindPublisher<User>?
    suspend fun get(id: String): FindPublisher<User>?
    suspend fun get(email: String, password: String): FindPublisher<User>?

}