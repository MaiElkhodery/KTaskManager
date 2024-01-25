package com.Kmongo.models

import com.Kmongo.utils.Priority
import com.Kmongo.utils.Status
import kotlinx.serialization.Serializable
import org.bson.BsonType
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonRepresentation
import org.bson.types.ObjectId
import java.time.LocalDate


data class Task(
    @BsonId
    val id: String,
    var title: String,
    var description: String,
    var startDate: String,
    var endDate: String,
    var priority: Priority,
    var status: Status
)
