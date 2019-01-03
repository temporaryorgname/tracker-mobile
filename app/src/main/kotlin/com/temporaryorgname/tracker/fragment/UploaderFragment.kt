package com.temporaryorgname.tracker.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import ca.allanwang.kau.mediapicker.createPrivateMediaFile
import ca.allanwang.kau.utils.postDelayed
import ca.allanwang.kau.utils.setIcon
import ca.allanwang.kau.utils.toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.temporaryorgname.tracker.BuildConfig
import com.temporaryorgname.tracker.R
import com.temporaryorgname.tracker.utils.L
import com.temporaryorgname.tracker.view.UploaderView
import kotlinx.android.synthetic.main.fragment_camera.*
import java.io.File

class UploaderFragment : BaseFragment() {

    companion object {
        private const val CAMERA_REQUEST = 123
    }

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var uploaderView: UploaderView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_save.setIcon(GoogleMaterial.Icon.gmd_camera)
        fab_save.setOnClickListener {
            requestPhoto()
//            testRequest()
        }

        // Trick to allow peek to equal expanded height
        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val sheetHeight: Int = (displayMetrics.heightPixels * 0.85).toInt()

        uploaderView = UploaderView(view.context)
        uploaderView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, sheetHeight)
        bottomSheetDialog = BottomSheetDialog(view.context)
        bottomSheetDialog.setContentView(uploaderView)

        val bottomSheetInternal = bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet)
        BottomSheetBehavior.from(bottomSheetInternal!!).peekHeight = sheetHeight

        testRequest()
    }

    private var requestFile: File? = null

    private fun testRequest() {
        val file =
            File("/storage/emulated/0/Android/data/com.temporaryorgname.tracker/files/Pictures/tracker_20190102_192716_5233139463757949039.jpg")
        prepareSubmission(file)
    }

    private fun requestPhoto() {
        val c = context ?: return
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(c.packageManager) == null) {
            c.toast("No camera found")
            return
        }
        val file = c.createPrivateMediaFile("tracker", ".jpg")
        requestFile = file
        intent.putExtra(
            MediaStore.EXTRA_OUTPUT,
            FileProvider.getUriForFile(c, BuildConfig.APPLICATION_ID + ".provider", file)
        )
        startActivityForResult(intent, CAMERA_REQUEST)
    }

    private fun prepareSubmission(file: File) {
        L.test { "Launched" }
        bottomSheetDialog.show()
        uploaderView.load(file)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST) {
            val file = requestFile
            if (resultCode == Activity.RESULT_OK && file != null) {
                postDelayed(500) {
                    prepareSubmission(file)
                }
            } else {
                file?.delete()
                requestFile = null
            }
        }
    }
}
