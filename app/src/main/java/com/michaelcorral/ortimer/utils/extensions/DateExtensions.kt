package com.michaelcorral.ortimer.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.today(): String {
    val dateFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.US)
    return dateFormatter.format(this)
}

fun Date.currentTime(is24HourClock: Boolean = true): String {
    val timePattern = if (is24HourClock) "hh:mm:ss" else "hh:mm:ss aa"
    val timeFormatter = SimpleDateFormat(timePattern, Locale.US)
    return timeFormatter.format(this)
}