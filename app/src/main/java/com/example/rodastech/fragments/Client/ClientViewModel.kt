package com.example.rodastech.fragments.Client

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rodastech.entities.Client
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ClientViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val db = Firebase.firestore
    private val _clients = MutableLiveData<MutableList<Client>>()
    val dbClients : LiveData<MutableList<Client>> get() = _clients



    fun getClientsList() {
        viewModelScope.launch {
            val dbClients = getDbClients()
            _clients.value = dbClients
        }
    }

    suspend fun getDbClients(): MutableList<Client> {
        lateinit var dbClientList: MutableList<Client>
        dbClientList = mutableListOf()
        try {
            val data = db.collection("clients").get().await()
            dbClientList=data.toObjects(Client::class.java)
        } catch (e: Exception) {
            Log.d("MHTEST", "EXCEPTION EN LIST CLOTH VIEW MODEL ${e.message}")
        }
        return dbClientList
    }






}


