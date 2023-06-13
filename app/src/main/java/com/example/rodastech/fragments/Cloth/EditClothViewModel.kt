package com.example.rodastech.fragments.Cloth

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rodastech.entities.Cloth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class EditClothViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val db = Firebase.firestore





    suspend fun updateCloth(cloth: Cloth)= coroutineScope {
        try {
            val clothQuery=db.collection("cloths").whereEqualTo("id",cloth.id.toString()).get().await()
            Log.d("RODASTECH", "ESTOY EN get id num")
            val updateMap= mapOf(
                "name" to cloth.name.toString(),
                "color" to cloth.color.toString(),
                "description" to cloth.description.toString(),
                "long" to  cloth.long,
                "price" to cloth.price,
                "provider" to cloth.provider.toString(),
                "stockActual" to cloth.stockActual,
                "width" to cloth.width,
                "stockMinimo" to cloth.stockMinimo
            )
            for (dbCloth in clothQuery){
                Log.d("RODASTECH", "ESTOY EN for para update ${dbCloth.id}")
                db.collection("cloths").document(dbCloth.id).update(updateMap).await()

            }
        }catch (e:Exception){
            Log.d("RODASTECH", "EXCEPTION EN EDIT CLOTH VIEW MODEL ${e.message}")
        }
    }


    suspend fun updateClothStock(cloth: Cloth)= coroutineScope {
        try {
            val clothQuery=db.collection("cloths").whereEqualTo("id",cloth.id.toString()).get().await()
            val updateMap= mapOf(
                "stockActual" to cloth.stockActual,
            )
            for (dbCloth in clothQuery){
                Log.d("RODASTECH", "ESTOY EN for para update ${dbCloth.id}")
                db.collection("cloths").document(dbCloth.id).update(updateMap).await()
            }
        }catch (e:Exception){
            Log.d("RODASTECH", "EXCEPTION EN EDIT CLOTH VIEW MODEL ${e.message}")
        }
    }


}