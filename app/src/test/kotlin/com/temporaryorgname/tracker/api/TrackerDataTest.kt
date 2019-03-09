package com.temporaryorgname.tracker.api

import org.junit.Test
import kotlin.test.assertFalse

class TrackerDataTest {

    @Test
    fun redactedLogin() {
        val data = TrackerLogin(email = "email", password = "hidden")
        assertFalse(data.toString().contains("hidden"), "TrackerLogin password not redacted")
    }

    @Test
    fun redactedSignUp() {
        val data = TrackerSignUp(email = "email", name = "name", password = "hidden")
        assertFalse(data.toString().contains("hidden"), "TrackerSignUp password not redacted")
    }
}