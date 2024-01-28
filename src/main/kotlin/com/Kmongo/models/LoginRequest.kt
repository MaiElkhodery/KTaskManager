package com.Kmongo.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email:String,
    val password: String
)
