package com.Kmongo.models

import kotlinx.serialization.Serializable


@Serializable
data class TaskRequest(
    var title: String,
    var description: String,
    var startDate: String,
    var endDate: String,
    var priority: String,
    var status: String
)
