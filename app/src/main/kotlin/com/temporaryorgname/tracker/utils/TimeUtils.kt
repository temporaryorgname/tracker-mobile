package com.temporaryorgname.tracker.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeUtils {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CANADA)
    private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.CANADA)

    fun currentDate(): String =
        dateFormat.format(Date())

    fun currentTime(): String =
        timeFormat.format(Date())
}
