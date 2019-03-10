package com.temporaryorgname.tracker.activities

import android.os.Bundle
import ca.allanwang.kau.internal.KauBaseActivity
import ca.allanwang.kau.utils.snackbar
import ca.allanwang.kau.utils.startActivity
import ca.allanwang.kau.utils.string
import com.temporaryorgname.tracker.R
import com.temporaryorgname.tracker.api.api
import com.temporaryorgname.tracker.api.login
import com.temporaryorgname.tracker.utils.Prefs
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch

class LoginActivity : KauBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener {
            launch {
                attemptLogin()
            }
        }
    }

    private suspend fun attemptLogin() {
        val email = login_email.text?.toString()?.trim()?.takeIf {
            it.isNotEmpty()
        } ?: return login_email.setError(string(R.string.login_email_error), null)

        val password = login_password.text?.toString()?.trim()?.takeIf {
            it.isNotEmpty()
        } ?: return login_password.setError(string(R.string.login_password_error), null)

        val result = api.login(email, password)

        if (result == null) {
            snackbar(R.string.login_failed)
        } else {
            Prefs.cookie = result.cookie
            Prefs.userId = result.id
            startActivity<MainActivity>()
        }
    }
}
