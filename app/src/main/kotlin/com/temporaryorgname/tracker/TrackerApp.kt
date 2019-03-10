package com.temporaryorgname.tracker

import android.app.Application
import com.temporaryorgname.tracker.utils.Prefs

class TrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Prefs.initialize(this, "tracker_prefs")
    }
}
