package com.ahr.gigihfinalproject.util

import java.text.SimpleDateFormat
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