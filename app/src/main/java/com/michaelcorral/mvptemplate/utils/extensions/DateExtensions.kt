package com.michaelcorral.mvptemplate.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.today(): String {
    val dateFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.US)
    return dateFormatter.format(this)
}