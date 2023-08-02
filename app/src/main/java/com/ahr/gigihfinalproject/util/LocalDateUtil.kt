package com.ahr.gigihfinalproject.util

import java.time.LocalDateTime

fun getCurrentTimeSeconds(): Long {
    return (LocalDateTime.now().hour.plus(1) * 60).toLong() * 60
}