package com.ahr.gigihfinalproject.util

import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

fun emptyString(): String = ""

fun String.toDisasterTimeFormat(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    val outputFormat = SimpleDateFormat("dd MMMM yyyy : HH:mm z", Locale.getDefault())
    outputFormat.timeZone = TimeZone.getTimeZone("WIB")

    val date = inputFormat.parse(this)
    return outputFormat.format(date)
}

fun String.toTmaMonitoringTimeFormat(): String {
    val parsedDate = OffsetDateTime.parse(this)
    val customFormatter = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm", Locale("id", "ID"))
    return parsedDate.format(customFormatter)
}