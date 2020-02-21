package com.nice.myapplication.view

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.nice.myapplication.R
class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var UsersRef: DatabaseReference? = null
    private var currentUser: FirebaseUser? = null
    private var UserEmail: EditText? = null
    private var registerButton: Button? = null
    private var loginButton: Button? = null
    private var loadingBar: ProgressDialog? = null
    private var UserPassword: EditText? = null
    private lateinit var toolbar : Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        UsersRef = FirebaseDatabase.getInstance().reference.child("Users")

        InitializeFields()
        currentUser = mAuth!!.currentUser
        loginButton!!.setOnClickListener{ AllowUserToLogin() }

        registerButton!!.setOnClickListener{ SendUserToRegisterActivity() }
    }

    private fun SendUserToRegisterActivity() {
        val registerIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(registerIntent)
    }

    private fun InitializeFields() {
        registerButton = findViewById(R.id.btRegister)
        loginButton =findViewById(R.id.btLogin)
        UserEmail = findViewById(R.id.tietEmailLogin)
        UserPassword = findViewById(R.id.tietPasswordLogin)
        toolbar = findViewById(R.id.login_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        loadingBar = ProgressDialog(this)
    }
    private fun AllowUserToLogin() {
        val email = UserEmail!!.text.toString()
        val password = UserPassword!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email....", Toast.LENGTH_SHORT).show()
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password....", Toast.LENGTH_SHORT).show()
        } else {
            loadingBar!!.setTitle("Sign In")
            loadingBar!!.setMessage("Please wait....")
            loadingBar!!.setCanceledOnTouchOutside(false)
            loadingBar!!.show()

            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val CurrentUserID = mAuth!!.currentUser!!.uid
                    val deviceToken = FirebaseInstanceId.getInstance().token

                    UsersRef!!.child(CurrentUserID).child("device_token")
                        .setValue(deviceToken)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                SendUserToProfileActivity()
                                Toast.makeText(this@LoginActivity, "Logged in  Successful....", Toast.LENGTH_SHORT).show()
                                loadingBar!!.dismiss()
                            }
                        }
                } else {
                    val message = task.exception!!.toString()
                    Toast.makeText(this@LoginActivity, "Error :$message", Toast.LENGTH_SHORT).show()
                    loadingBar!!.dismiss()
                }
            }
        }

    }
    private fun SendUserToProfileActivity() {
        val mainIntent = Intent(this@LoginActivity, ProfileActivity::class.java)
        startActivity(mainIntent)
    }
}
