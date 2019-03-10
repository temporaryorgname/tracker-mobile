package com.temporaryorgname.tracker.utils

import ca.allanwang.kau.kpref.KPref
import ca.allanwang.kau.kpref.kpref

object Prefs : KPref() {
    var cookie: String by kpref("tracker_cookie", "")
    var userId: Long by kpref("tracker_user_id", -1L)

    fun logout() {
        cookie = ""
        userId = -1L
    }
}