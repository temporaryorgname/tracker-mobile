package com.temporaryorgname.tracker.api

import ca.allanwang.kit.retrofit.RetrofitApiConfig
import ca.allanwang.kit.retrofit.createRetrofitApi
import kotlinx.coroutines.runBlocking
import org.junit.BeforeClass
import java.io.File
import java.util.concurrent.atomic.AtomicReference
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.fail

class TrackerApiTest {

    companion object {

        lateinit var api: TrackerApi
        lateinit var user: TrackerLoginResponse

        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            val cookieRef = AtomicReference<String>()
            api = createRetrofitApi(TrackerApi.BASE_URL) {

                cookieRetriever = { cookieRef.get() }

                clientBuilder = {
                    RetrofitApiConfig.loggingInterceptor()(it)
                }
            }
            runBlocking {
                user = api.login("android", "unittest") ?: fail("Login result not found")
                cookieRef.set(user.cookie)
            }
        }
    }

    @Ignore("Ignore during full execution")
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

    @Test
    fun photoList() {
        runBlocking {
            val result = api.getPhotosForUser(user.id).await()
            assertNotNull(result, "Photo retrieval failed")
            print(result)
        }
    }
}