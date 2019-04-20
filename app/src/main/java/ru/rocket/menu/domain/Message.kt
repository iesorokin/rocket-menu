package ru.rocket.menu.domain

import java.time.LocalDateTime

data class Message(
    var date: LocalDateTime? = null,
    var from: String? = null,
    var message: String? = null
)