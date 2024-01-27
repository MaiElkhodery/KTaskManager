package com.Kmongo.data

import com.Kmongo.utils.TASK_DB
import org.litote.kmongo.reactivestreams.KMongo


object Database {

    fun getDatabase(): com.mongodb.reactivestreams.client.MongoDatabase? {

        val client = KMongo.createClient()


        return client.getDatabase(TASK_DB)
    }

}