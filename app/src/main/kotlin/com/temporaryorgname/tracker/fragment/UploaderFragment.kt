package com.temporaryorgname.tracker.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import ca.allanwang.kau.mediapicker.createPrivateMediaFile
import ca.allanwang.kau.utils.setIcon
import ca.allanwang.kau.utils.snackbar
import ca.allanwang.kau.utils.toast
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.temporaryorgname.tracker.BuildConfig
import com.temporaryorgname.tracker.R
import com.temporaryorgname.tracker.api.api
import com.temporaryorgname.tracker.api.uploadPhoto
import com.temporaryorgname.tracker.utils.L
import kotlinx.android.synthetic.main.fragment_camera.*
import kotlinx.coroutines.launch
import java.io.File

class UploaderFragment : BaseFragment() {

    companion object {
        private const val CAMERA_REQUEST = 123
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_save.setIcon(GoogleMaterial.Icon.gmd_camera)
        fab_save.setOnClickListener {
            requestPhoto()
        }
    }

    private var requestFile: File? = null

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

    private suspend fun sendPhoto(file: File) {
        L.d { "Sending photo" }
        coordinator.snackbar(R.string.photo_uploading)
        val id = api.uploadPhoto(file)
        if (id == null) {
            coordinator.snackbar(R.string.photo_upload_fail)
        } else {
            coordinator.snackbar(R.string.photo_upload_success)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST) {
            val file = requestFile
//            L.d { "Camera req ${resultCode} ${Activity.RESULT_OK} ${file?.length()}" }
            if (resultCode == Activity.RESULT_OK && file != null && file.length() > 0) {
                launch {
                    sendPhoto(file)
                }
            } else {
                L.d { "Camera capture error" }
                file?.delete()
                requestFile = null
            }
        }
    }
}
