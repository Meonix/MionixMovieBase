package com.nice.myapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.linecorp.linesdk.api.LineApiClient
import com.nice.myapplication.R

class ProfileActivity : AppCompatActivity() {
    private lateinit var toolbar : Toolbar
    private lateinit var btLogout: Button
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initView()
        mAuth = FirebaseAuth.getInstance()

        btLogout.setOnClickListener {
            mAuth!!.signOut()
            LoginManager.getInstance().logOut()
            Toast.makeText(this,"Log out Success..",Toast.LENGTH_SHORT).show()
            btLogout.visibility = View.GONE

            val intent = Intent(this@ProfileActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initView(){
        mAuth = FirebaseAuth.getInstance()
        toolbar = findViewById(R.id.profile_toolbar)
        btLogout = findViewById(R.id.btLogout)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}