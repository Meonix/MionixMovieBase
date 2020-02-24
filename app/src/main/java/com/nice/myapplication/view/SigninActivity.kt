package com.nice.myapplication.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.nice.myapplication.R
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class SigninActivity : AppCompatActivity() {
    lateinit var providers : List<AuthUI.IdpConfig>
    val MY_REQUEST_CODE = 1
    private lateinit var bgSignOut : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        bgSignOut = findViewById(R.id.bgSignOut)
        providers = Arrays.asList<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        showSignInOptions()
        bgSignOut.setOnClickListener {
            AuthUI.getInstance().signOut(this@SigninActivity)
                .addOnCompleteListener{
                    bgSignOut.visibility = View.INVISIBLE
                            showSignInOptions()
                }
                .addOnFailureListener{
                    Toast.makeText(this@SigninActivity,it.message,Toast.LENGTH_SHORT).show()
                }
        }
        try {
            val info = packageManager.getPackageInfo(
                "com.nice.myapplication",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", android.util.Base64
                    .encodeToString(md.digest(), android.util.Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }
    }
    private fun showSignInOptions(){
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.SiginTheme)
            .build(),MY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== MY_REQUEST_CODE){
            val response = IdpResponse.fromResultIntent(data)
            if(resultCode == Activity.RESULT_OK){
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(this,"welcome" + user!!.email,Toast.LENGTH_SHORT).show()
                bgSignOut.visibility = View.VISIBLE
            }
            else{
                Toast.makeText(this,"welcome" + response!!.error,Toast.LENGTH_SHORT).show()
            }
        }
    }
}
