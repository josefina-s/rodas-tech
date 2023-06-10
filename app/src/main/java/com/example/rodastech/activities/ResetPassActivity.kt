package com.example.rodastech.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.rodastech.R
import com.example.rodastech.databinding.ActivityResetPass2Binding
import com.example.rodastech.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class ResetPassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPass2Binding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPass2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnResetPass.setOnClickListener {
            if (!binding.txtResetPassMail.text.toString().isNullOrEmpty()) {
                try {
                    firebaseAuth.sendPasswordResetEmail(binding.txtResetPassMail.text.toString())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Se envió un enlace para reestablecer su contraseña a : ${binding.txtResetPassMail.text}",
                                    Toast.LENGTH_LONG
                                ).show()
                                val intent = Intent(this, SignInActivity::class.java)
                                startActivity(intent)
                            } else {
                                val exception = task.exception
                                if (exception is FirebaseAuthException) {
                                    val errorCode = exception.errorCode
                                    val errorMessage = exception.localizedMessage
                                    Log.d("MHTEST", "ERROR: ${errorCode} - ${errorMessage}")
                                    Toast.makeText(
                                        this,
                                        "ERROR: ${errorMessage}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                } catch (e: Exception) {
                    Log.d("MHTEST", e.toString())
                    Toast.makeText(
                        this,
                        "ERROR: ${e}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(this, "No se permiten campos vacíos !!", Toast.LENGTH_SHORT).show()
            }


        }
    }
}