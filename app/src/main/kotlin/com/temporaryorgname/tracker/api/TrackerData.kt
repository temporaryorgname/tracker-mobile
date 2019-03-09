package com.temporaryorgname.tracker.api

data class TrackerLogin(val email: String, val password: String) {
    override fun toString(): String = "TrackerLogin(email=$email, password=${"*".repeat(password.length)}"
}

data class TrackerSignUp(val email: String, val name: String, val password: String) {
    override fun toString(): String = "TrackerSignUp(email=$email, name=$name, password=${"*".repeat(password.length)}"
}

data class TrackerId(val id: String)

data class TrackerMessage(val message: String)