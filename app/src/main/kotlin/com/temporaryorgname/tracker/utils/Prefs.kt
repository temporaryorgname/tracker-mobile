package com.temporaryorgname.tracker.utils

import ca.allanwang.kau.kpref.KPref
import ca.allanwang.kau.kpref.kpref

object Prefs : KPref() {
    var cookie: String by kpref("tracker_cookie", "")
}