//package com.example.rodastech.activities
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import com.example.rodastech.R
//
//class LoginActivity : AppCompatActivity() {
//
//    lateinit var btnSubmit : Button
//    lateinit var inputUser : EditText
//    lateinit var inputPass : EditText
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sign_in)
//
//        btnSubmit = findViewById(R.id.btnSubmit)
//        inputUser = findViewById(R.id.inputUser)
//        inputPass = findViewById(R.id.inputPass)
//
//    }
//    override fun onStart() {
//        super.onStart()
//        btnSubmit.setOnClickListener {
//
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
//    }
//
//    }
