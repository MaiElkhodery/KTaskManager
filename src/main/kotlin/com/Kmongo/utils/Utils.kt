package com.Kmongo.utils

import java.security.SecureRandom
import java.util.*

const val TASK_DB = "task_manger"
const val PRIORITY = "priority"
const val STATUS = "status"
const val ID = "_id"

fun validateUsername(name: String) {
    if (name.length < 3) {
        throw IllegalArgumentException("Invalid user name: must be at least 3 characters")
    }
}

fun validateEmail(email: String) {
    if (!email.matches(Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))) {
        throw IllegalArgumentException("Invalid email address")
    }
}

fun validatePassword(password: String) {
    if (password.length < 8) {
        throw IllegalArgumentException("Invalid password: must be at least 8 characters")
    }
    if (!password.any { it.isDigit() }) {
        throw IllegalArgumentException("Invalid password: must contain at least one digit")
    }
}

fun generateRandomSecret(length: Int): String {
    val random = SecureRandom()
    val bytes = ByteArray(length)
    random.nextBytes(bytes)
    return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
}

val strongRandomSecret = generateRandomSecret(32)