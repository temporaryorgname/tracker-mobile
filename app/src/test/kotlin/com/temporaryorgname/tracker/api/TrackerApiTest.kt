package com.temporaryorgname.tracker.api

import ca.allanwang.kit.retrofit.RetrofitApiConfig
import ca.allanwang.kit.retrofit.createRetrofitApi
import kotlinx.coroutines.runBlocking
import org.junit.BeforeClass
import java.io.File
import java.util.concurrent.atomic.AtomicReference
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.fail

class TrackerApiTest {

    companion object {

        lateinit var api: TrackerApi

        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            val cookieRef = AtomicReference<String>()
            api = createRetrofitApi("https://logs.hhixl.net:5000/api/") {

                cookieRetriever = { cookieRef.get() }

                clientBuilder = {
                    RetrofitApiConfig.loggingInterceptor()(it)
                }
            }
            runBlocking {
                val cookie = api.login("android", "unittest") ?: fail("Cookie not found")
                cookieRef.set(cookie)
            }
        }
    }

    @Test
    fun photoUpload() {
        runBlocking {
            val imageFile: File =
                TrackerApiTest::class.java.classLoader?.getResource("img/android-pie.jpg")?.file?.let { File(it) }
                    ?: fail("Could not get image reference")
            val result = api.uploadPhoto(imageFile)
            assertNotNull(result, "Photo upload failed")
        }
    }
}