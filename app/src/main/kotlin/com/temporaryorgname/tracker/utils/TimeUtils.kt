package com.temporaryorgname.tracker.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeUtils {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CANADA)

    fun currentTime(): String =
        dateFormat.format(Date())
}
