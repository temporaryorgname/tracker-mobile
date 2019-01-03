package com.temporaryorgname.tracker.api

import ca.allanwang.kit.retrofit.RetrofitApiConfig
import ca.allanwang.kit.retrofit.createRetrofitApi
import com.temporaryorgname.tracker.BuildConfig
import com.temporaryorgname.tracker.utils.TimeUtils
import kotlinx.coroutines.Deferred
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

interface TrackerApi {

    @Multipart
    @POST("data/food/photo")
    fun uploadPhoto(@Part photo: MultipartBody.Part, @Part date: MultipartBody.Part): Deferred<String>
}

fun TrackerApi.uploadPhoto(photo: File): Deferred<String> {
    val photoPart =
        MultipartBody.Part.createFormData("file", photo.name, RequestBody.create(MediaType.parse("image/*"), photo))
    val datePart = MultipartBody.Part.createFormData("date", TimeUtils.currentTime())
    return uploadPhoto(photoPart, datePart)
}

// TODO add url
val api = createRetrofitApi<TrackerApi>("") {
    clientBuilder = {
        if (BuildConfig.DEBUG)
            RetrofitApiConfig.loggingInterceptor()(it)
    }
    addCoroutineAdapter = true
}
