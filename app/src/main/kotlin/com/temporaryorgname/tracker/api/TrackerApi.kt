package com.temporaryorgname.tracker.api

import ca.allanwang.kit.retrofit.RetrofitApiConfig
import ca.allanwang.kit.retrofit.createRetrofitApi
import com.temporaryorgname.tracker.BuildConfig
import com.temporaryorgname.tracker.utils.L
import com.temporaryorgname.tracker.utils.Prefs
import com.temporaryorgname.tracker.utils.TimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

// See https://logs.hhixl.net:5000/apidocs/
interface TrackerApi {

    @POST("data/users")
    fun signup(@Body signup: TrackerSignUp): Call<TrackerMessage>

    @POST("auth/login")
    fun login(@Body login: TrackerLogin): Call<String>

    @Multipart
    @POST("data/photos")
    fun uploadPhoto(@Part photo: MultipartBody.Part, @Part date: MultipartBody.Part, @Part time: MultipartBody.Part): Call<TrackerId>
}

internal suspend fun <T> Call<T>.await(): T? = tryOrNull {
    execute().body()
}

private suspend inline fun <T> tryOrNull(crossinline action: () -> T?): T? = withContext(Dispatchers.IO) {
    try {
        action()
    } catch (e: Exception) {
        L.e(e) { "Api call failed" }
        null
    }
}

suspend fun TrackerApi.login(email: String, password: String): String? = tryOrNull {
    login(TrackerLogin(email, password)).execute().headers().get("Set-Cookie")
}

suspend fun TrackerApi.uploadPhoto(photo: File): TrackerId? = tryOrNull {
    val photoPart =
        MultipartBody.Part.createFormData("file", photo.name, RequestBody.create(MediaType.parse("image/*"), photo))
    val datePart = MultipartBody.Part.createFormData("date", TimeUtils.currentDate())
    val timePart = MultipartBody.Part.createFormData("time", TimeUtils.currentTime())
    uploadPhoto(photoPart, datePart, timePart).execute().body()
}

val api = createRetrofitApi<TrackerApi>("https://logs.hhixl.net:5000/api/") {
    cookieRetriever = { Prefs.cookie }
    clientBuilder = {
        if (BuildConfig.DEBUG)
            RetrofitApiConfig.loggingInterceptor()(it)
        // TODO go back to login if unauthorized?
    }
}
