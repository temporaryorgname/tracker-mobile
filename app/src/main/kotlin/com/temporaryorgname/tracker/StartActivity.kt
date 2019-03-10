package com.temporaryorgname.tracker

import android.os.Bundle
import ca.allanwang.kau.internal.KauBaseActivity
import ca.allanwang.kau.utils.startActivity
import com.temporaryorgname.tracker.activities.LoginActivity
import com.temporaryorgname.tracker.activities.MainActivity
import com.temporaryorgname.tracker.utils.Prefs

class StartActivity : KauBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        if (Prefs.cookie.isBlank()) {
            startActivity<LoginActivity>()
        } else {
            startActivity<MainActivity>()
        }
    }
}
