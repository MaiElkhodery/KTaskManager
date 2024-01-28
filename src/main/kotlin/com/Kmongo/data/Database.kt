package com.Kmongo.data

import com.Kmongo.utils.TASK_DB
import com.mongodb.reactivestreams.client.MongoDatabase
import org.litote.kmongo.reactivestreams.KMongo


object Database {

    private var mongoDatabase: MongoDatabase? = null

    fun getDatabase(): MongoDatabase {
        synchronized(this) {
            if (mongoDatabase == null) {
                val client = KMongo.createClient()
                mongoDatabase = client.getDatabase(TASK_DB)
            }
            return mongoDatabase!!
        }
    }

}