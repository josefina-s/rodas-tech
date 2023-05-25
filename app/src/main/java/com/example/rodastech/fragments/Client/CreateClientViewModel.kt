package com.example.rodastech.fragments.Client

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.rodastech.entities.Client
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class CreateClientViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val db = Firebase.firestore


    suspend fun insertClient(client: Client) = coroutineScope {
        try {
            val insertMap = mapOf(
                "id" to client.id,
                "name" to client.name.toString(),
                "cuit" to client.cuit.toString(),
                "contactPerson" to client.contactPerson.toString(),
                "telephone" to client.telephone,
                "email" to client.email.toString(),
                "address" to client.address.toString(),
                "cp" to client.cp
            )
            db.collection("clients").add(insertMap).await()
            Log.d("MHTEST", "ESTOY EN CREATE CLIENT VIEW MODEL ${client.name.toString()}")

        } catch (e: Exception) {
            Log.d("MHTEST", "EXCEPTION EN CREATE CLIENT VIEW MODEL ${e.message}")
        }
    }
}