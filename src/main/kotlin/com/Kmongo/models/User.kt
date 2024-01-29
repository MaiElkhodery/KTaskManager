package com.Kmongo.models

import org.bson.BsonType
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonRepresentation

data class User(
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    val id: String,
    val email: String,
    val name: String,
    val password: String
)
