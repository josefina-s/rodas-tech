package com.example.rodastech.fragments.Cloth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.rodastech.entities.Cloth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class DetailClothViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val db = Firebase.firestore

    suspend fun deleteCloth(cloth: Cloth)= coroutineScope {
        try {
            val clothQuery=db.collection("cloths").whereEqualTo("id",cloth.id.toString()).get().await()
            Log.d("RODASTECH", "ESTOY EN get id num")

            for (dbCloth in clothQuery){
                Log.d("RODASTECH", "ESTOY EN for para delete ${dbCloth.id}")
                db.collection("cloths").document(dbCloth.id).delete().await()
            }
        }catch (e:Exception){
            Log.d("RODASTECH", "ESTOY EN exception del delete ${e.message}")
        }
    }

}