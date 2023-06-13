package com.example.rodastech.fragments.Client

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.rodastech.entities.Client
import com.example.rodastech.entities.Cloth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class EditClientViewModel : ViewModel() {
    val db = Firebase.firestore
    suspend fun updateClient(client: Client)= coroutineScope {
        try {
            val clothQuery=db.collection("clients").whereEqualTo("id",client.id.toString()).get().await()
            Log.d("RODASTECH", "ESTOY EN get id num EDIT CLIENT")
            val updateMap = mapOf(
                "id" to client.id,
                "name" to client.name.toString(),
                "cuit" to client.cuit.toString(),
                "contactPerson" to client.contactPerson.toString(),
                "telephone" to client.telephone,
                "email" to client.email.toString(),
                "address" to client.address.toString(),
                "cp" to client.cp
            )
            for (dbClient in clothQuery){
                Log.d("RODASTECH", "ESTOY EN for para EDIT CLIENT ${dbClient.id}")
                db.collection("clients").document(dbClient.id).update(updateMap).await()

            }
        }catch (e:Exception){
            Log.d("RODASTECH", "EXCEPTION EN EDIT CLIENT VIEW MODEL ${e.message}")
        }
    }

}