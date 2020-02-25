package com.nice.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import com.linecorp.linesdk.LoginDelegate
import com.linecorp.linesdk.LoginListener
import com.linecorp.linesdk.Scope
import com.linecorp.linesdk.auth.LineAuthenticationParams
import com.linecorp.linesdk.auth.LineLoginApi
import com.linecorp.linesdk.auth.LineLoginResult
import com.linecorp.linesdk.widget.LoginButton
import com.nice.myapplication.R
import java.util.*

class LoginwithLine : AppCompatActivity() {
    private val REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginwith_line)
        val loginDelegate = LoginDelegate.Factory.create()

        val loginButton = findViewById(R.id.line_login_btn) as LoginButton


        loginButton.setChannelId("1653882343")
// configure whether login process should be done by Line App, or inside WebView.
        loginButton.enableLineAppAuthentication(true)

// set up required scopes and nonce.
        loginButton.setAuthenticationParams(
            LineAuthenticationParams.Builder()
                .scopes(Arrays.asList(Scope.PROFILE))
                // .nonce("<a randomly-generated string>") // nonce can be used to improve security
                .build()
        )
        loginButton.setLoginDelegate(loginDelegate)
        loginButton.addLoginListener(object : LoginListener {
            override fun onLoginSuccess(result: LineLoginResult) {
                Toast.makeText(this@LoginwithLine, "Login success", Toast.LENGTH_SHORT).show()
            }

            override fun onLoginFailure(@Nullable result: LineLoginResult?) {
                Toast.makeText(this@LoginwithLine, "Login failure", Toast.LENGTH_SHORT).show()
            }
        })

        loginButton.setOnClickListener{
                try {
                    // App-to-app login
                    val loginIntent = LineLoginApi.getLoginIntent(
                        this,
                        "1653882343",
                        LineAuthenticationParams.Builder()
                            .scopes(Arrays.asList(Scope.PROFILE))
                            // .nonce("<a randomly-generated string>") // nonce can be used to improve security
                            .build()
                    )
                    startActivityForResult(loginIntent, REQUEST_CODE)

                } catch (e: Exception) {
                    Log.e("ERROR", e.toString())
                }

    }
    }

}
