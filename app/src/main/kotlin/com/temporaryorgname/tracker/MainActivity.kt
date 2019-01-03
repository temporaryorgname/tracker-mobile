package com.temporaryorgname.tracker

import android.os.Bundle
import ca.allanwang.kau.internal.KauBaseActivity
import com.temporaryorgname.tracker.fragment.UploaderFragment

class MainActivity : KauBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

//        camera_button.setOnClickListener {
//            startActivityForResult<CameraActivity>(123)
//        }

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = UploaderFragment()
        fragmentTransaction.add(R.id.frame_container, fragment)
        fragmentTransaction.commit()
    }
}
