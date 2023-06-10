package com.example.rodastech.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rodastech.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSaveSignUp.setOnClickListener {
            val email = binding.txtNewMail.text.toString()
            val pass = binding.txtNewUserPass.text.toString()
            val confirmPass = binding.txtNewConfUserPass.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    try {
                        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
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
                    Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No se permiten campos vacíos !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}