package com.example.rodastech.fragments.Cloth

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rodastech.entities.Client
import com.example.rodastech.entities.Cloth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ListClothViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val db = Firebase.firestore
    private val _cloths = MutableLiveData<MutableList<Cloth>>()
    val cloths : LiveData<MutableList<Cloth>> get() = _cloths

    private val _selectedCloth = MutableLiveData<Cloth>()
    val selectedCloth: MutableLiveData<Cloth> get() = _selectedCloth

    fun llamarGetAllCloths(){
    viewModelScope.launch {
        val cloths=getAllCloths()
            _cloths.value=cloths
        }
    }
    fun setSelectedCloth(cloth: Cloth) {
        _selectedCloth.value = cloth
    }


    suspend fun getAllCloths(): MutableList<Cloth> {
        lateinit var dbClothList: MutableList<Cloth>
        dbClothList = mutableListOf()
        try {
            val data = db.collection("cloths").get().await()
            for (cloth in data) {
                dbClothList.add(cloth.toObject(Cloth::class.java))
            }
        } catch (e: Exception) {
            Log.d("RODASTECH", "EXCEPTION EN LIST CLOTH VIEW MODEL ${e.message}")
        }
        return dbClothList
    }
}

