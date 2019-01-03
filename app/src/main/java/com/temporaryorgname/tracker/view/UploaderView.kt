package com.temporaryorgname.tracker.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import ca.allanwang.kau.utils.inflate
import ca.allanwang.kau.utils.setIcon
import ca.allanwang.kau.utils.string
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.temporaryorgname.tracker.R
import kotlinx.android.synthetic.main.view_uploader.view.*
import java.io.File

class UploaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var file: File? = null

    init {
        inflate(R.layout.view_uploader, true)
        fab_send.setIcon(GoogleMaterial.Icon.gmd_send)
        fab_send.setOnClickListener { upload() }
    }

    fun load(file: File) {
        this.file = file
        Glide.with(this@UploaderView).load(file).transition(withCrossFade()).into(img_preview)
    }

    private fun upload() {
        val title: String = upload_title.text.trim().toString().replace(" ", "_")
        if (title.isBlank()) {
            upload_title.error = context.string(R.string.uploader_title_error)
            upload_title.requestFocus()
            return
        }
    }

}