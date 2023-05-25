package com.example.rodastech.fragments.Cloth

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rodastech.entities.Cloth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class CreateClothViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val db = Firebase.firestore
//    val name = MutableLiveData<String>()
    val cloth = MutableLiveData<Cloth>()
    val errorNombre = MutableLiveData<String?>()
    val errorDesc = MutableLiveData<String?>()
    val errorProv = MutableLiveData<String?>()
    val errorColor = MutableLiveData<String?>()
    val errorLong = MutableLiveData<String?>()
    val errorWidth = MutableLiveData<String?>()
    val errorInitialStock = MutableLiveData<String?>()
    val formularioValido = MutableLiveData<Boolean>()


    fun validarFormulario() {
        formularioValido.value = isValidName()&&isValidDesc()&&isValidProvider()&&isValidColor()&&isValidLong()&&isValidWidth()&&isValidInitialStock()
    }

    fun isValidName(): Boolean {
        if (cloth.value?.name.toString().isNullOrEmpty()) {
            errorNombre.value = "Nombre es requerido"
            return false
        } else if(cloth.value?.name.toString().length<3||cloth.value?.name.toString().length>20){
            errorNombre.value = "La longitud tiene que ser entre 3 y 20 caracteres"
            return false
        }
        errorNombre.value =""
        return true
    }
    fun isValidProvider(): Boolean {
        if (cloth.value?.provider.toString().isNullOrEmpty()) {
            errorProv.value = "Proveedor es requerido"
            return false
        } else if(cloth.value?.provider.toString().length<3||cloth.value?.provider.toString().length>20){
            errorProv.value = "La longitud tiene que ser entre 3 y 20 caracteres"
            return false
        }
        errorProv.value =""
        return true
    }
    fun isValidDesc(): Boolean {

        if (cloth.value?.description.toString().isNullOrEmpty()) {
            errorDesc.value = "Descripción es requerido"
            return false
        } else if(cloth.value?.description.toString().length<4||cloth.value?.description.toString().length>20){
            errorDesc.value = "La longitud tiene que ser entre 4 y 20 caracteres"
            return false
        }
        errorDesc.value =""
        return true
    }
    fun isValidColor(): Boolean {
        if (cloth.value?.color.toString().isNullOrEmpty()) {
            errorColor.value = "Color es requerido"
            return false
        } else if(cloth.value?.color.toString().length<3||cloth.value?.color.toString().length>20){
            errorColor.value = "La longitud tiene que ser entre 3 y 20 caracteres"
            return false
        }
        errorColor.value =""
        return true
    }
    fun isValidLong(): Boolean {
        if (cloth.value?.long.toString().isNullOrEmpty()) {
            errorLong.value = "Longitud es requerido"
            return false
        } else if(cloth.value?.long!! <5||cloth.value?.long!!>20){
            errorLong.value = "El largo tiene que ser entre 5 y 20 metros"
            return false
        }
        errorLong.value =""
        return true
    }
    fun isValidWidth(): Boolean {
        if (cloth.value?.width.toString().isNullOrEmpty()) {
            errorWidth.value = "Longitud es requerido"
            return false
        } else if(cloth.value?.width!! <5||cloth.value?.width!!>20){
            errorWidth.value = "El ancho tiene que ser entre 5 y 20 metros"
            return false
        }
        errorWidth.value =""
        return true
    }
    fun isValidInitialStock(): Boolean {
        if (cloth.value?.stockMinimo.toString().isNullOrEmpty()) {
            errorInitialStock.value = "Stock inicial es requerido"
            return false
        } else if(cloth.value?.stockMinimo!! <5||cloth.value?.stockMinimo!!>500){
            errorInitialStock.value = "El stock inicial tiene que ser entre 5 y 500 metros"
            return false
        }
        errorInitialStock.value =""
        return true
    }


//    suspend fun insertCloth() = coroutineScope {
//        try {
//            val insertMap = mapOf(
//                "id" to cloth.value?.id ,
//                "name" to cloth.value?.name.toString(),
//                "color" to cloth.value?.color.toString(),
//                "description" to cloth.value?.description.toString(),
//                "long" to cloth.value?.long,
////                "meters" to cloth.value?.meters,
//                "price" to cloth.value?.price,
//                "provider" to cloth.value?.provider.toString(),
//                "stockActual" to cloth.value?.stockActual,
//                "width" to cloth.value?.width
//            )
//            db.collection("cloths").add(insertMap).await()
//
//        } catch (e: Exception) {
//            Log.d("MHTEST", "EXCEPTION EN CREATE CLOTH VIEW MODEL ${e.message}")
//        }
//    }


    suspend fun insertCloth(cloth: Cloth) = coroutineScope {
        try {
            val insertMap = mapOf(
                "id" to cloth.id,
                "name" to cloth.name.toString(),
                "color" to cloth.color.toString(),
                "description" to cloth.description.toString(),
                "long" to cloth.long,
//                "meters" to cloth.meters,
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