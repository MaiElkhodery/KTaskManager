package com.Kmongo.models

import kotlinx.serialization.Serializable
import org.bson.BsonType
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonRepresentation


@Serializable
data class Task(
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    val _id: String? = null,
    var title: String,
    var description: String,
    var startDate: String,
    var endDate: String,
    var priority: String,
    var status: String
)
