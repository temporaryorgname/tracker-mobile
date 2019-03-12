package com.temporaryorgname.tracker.utils

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

fun <T> RequestBuilder<T>.listen(continuation: Continuation<Boolean>): RequestBuilder<T> =
    addListener(object : RequestListener<T> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<T>?,
            isFirstResource: Boolean
        ): Boolean {
            continuation.resume(false)
            return false
        }

        override fun onResourceReady(
            resource: T,
            model: Any?,
            target: Target<T>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            continuation.resume(true)
            return false
        }
    })

@GlideModule
class TrackerGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
//        registry.replace(GlideUrl::class.java,
//                InputStream::class.java,
//                OkHttpUrlLoader.Factory(getFrostHttpClient()))
//        registry.prepend(HdImageMaybe::class.java, InputStream::class.java, HdImageLoadingFactory())
    }
}