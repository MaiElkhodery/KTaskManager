package com.Kmongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients

object MongoDatabase {
    var CLIENT: MongoClient
    val uri = "mongodb://localhost:27017/"

    init {
        val serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1).build()
        val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(uri))
            .serverApi(serverApi)
            .build()
        CLIENT = MongoClients.create(settings)
    }
}