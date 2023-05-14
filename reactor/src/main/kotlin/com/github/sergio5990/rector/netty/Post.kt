package com.github.sergio5990.rector.netty

import java.time.LocalDateTime
import java.util.*

data class Post(val id: UUID,
                val title: String,
                val content: String,
                val createdAt: LocalDateTime)