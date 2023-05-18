package com.example.rodastech.fragments.Cloth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.rodastech.entities.Cloth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class CreateClothViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val db = Firebase.firestore
    suspend fun insertCloth(cloth: Cloth) = coroutineScope {
        try {
            val insertMap = mapOf(
                "id" to cloth.id,
                "name" to cloth.name.toString(),
                "color" to cloth.color.toString(),
                "description" to cloth.description.toString(),
                "long" to cloth.long,
                "meters" to cloth.meters,
                "price" to cloth.price,
                "provider" to cloth.provider.toString(),
                "stockActual" to cloth.stockActual,
                "width" to cloth.width
            )
            db.collection("cloths").add(insertMap).await()

        } catch (e: Exception) {
            Log.d("MHTEST", "EXCEPTION EN CREATE CLOTH VIEW MODEL ${e.message}")
        }
    }
}