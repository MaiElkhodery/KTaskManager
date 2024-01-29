package com.Kmongo.data

import com.Kmongo.utils.TASK_DB
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoDatabase
import io.ktor.server.application.*
import org.litote.kmongo.reactivestreams.KMongo


object Database {

    private var mongoDatabase: MongoDatabase? = null
    var mongoClient: MongoClient? = null

    fun getDatabase(): MongoDatabase {
        synchronized(this) {
            if (mongoDatabase == null) {
                mongoClient = KMongo.createClient()
                mongoDatabase = mongoClient!!.getDatabase(TASK_DB)
            }
            return mongoDatabase!!
        }
    }

}