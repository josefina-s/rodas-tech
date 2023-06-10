package com.example.rodastech.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.rodastech.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()
        binding.txtNewUser.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.txtResetPass.setOnClickListener {
            val intent = Intent(this, ResetPassActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.txtLoginMail.text.toString()
            val pass = binding.txtLoginPass.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                try {
                    firebaseAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(this, MainActivity::class.java)
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
                }catch(e:Exception){
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

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}

//                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        val intent = Intent(this, MainActivity::class.java)
//                        startActivity(intent)
//                    } else {
//                        Log.d("MHTEST", "signInWithEmail:failure ${it.exception.toString()}")
//                        Toast.makeText(this, "Login incorrecto, verifique sus credenciales", Toast.LENGTH_LONG).show()
//
//                    }
//                }