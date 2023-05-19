package com.example.rodastech.fragments.Client

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.rodastech.entities.Client
import com.example.rodastech.entities.Cloth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class DetailClientViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val db = Firebase.firestore

    suspend fun deleteClient(client: Client) = coroutineScope {
        try {
            val clothQuery =
                db.collection("clients").whereEqualTo("id", client.id.toString()).get().await()
            Log.d("MHTEST", "ESTOY EN deleteClient")
            for (dbClient in clothQuery) {
                Log.d("MHTEST", "ESTOY EN for para deleteClient ${dbClient.id}")
                db.collection("clients").document(dbClient.id).delete().await()
            }
        } catch (e: Exception) {
            Log.d("MHTEST", "ESTOY EN exception del deleteClient ${e.message}")
        }
    }
}