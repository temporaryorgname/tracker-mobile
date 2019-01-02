package com.temporaryorgname.tracker

import android.os.Bundle
import ca.allanwang.kau.internal.KauBaseActivity

class MainActivity : KauBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
