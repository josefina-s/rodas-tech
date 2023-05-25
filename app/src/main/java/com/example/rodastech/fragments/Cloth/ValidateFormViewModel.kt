package com.example.rodastech.fragments.Cloth

import android.telephony.PhoneNumberUtils
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rodastech.entities.Client
import com.example.rodastech.entities.Cloth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class ValidateFormViewModel : ViewModel(){


    val db = Firebase.firestore
    val cloth = MutableLiveData<Cloth>()
    val client = MutableLiveData<Client>()

    val errorNombre = MutableLiveData<String?>()
    val errorDesc = MutableLiveData<String?>()
    val errorProv = MutableLiveData<String?>()
    val errorColor = MutableLiveData<String?>()
    val errorLong = MutableLiveData<String?>()
    val errorWidth = MutableLiveData<String?>()
    val errorInitialStock = MutableLiveData<String?>()

    val errorContactPerson = MutableLiveData<String?>()
    val errorPhone = MutableLiveData<String?>()
    val errorEmail = MutableLiveData<String?>()
    val errorAddress= MutableLiveData<String?>()
    val errorPostalAddress = MutableLiveData<String?>()

    val formularioValido = MutableLiveData<Boolean>()


    fun validarFormulario(name: String, desc: String, provider: String, color: String, width: String, long: String, initialStock: String) {
        formularioValido.value = isValidName(name)&&isValidDesc(desc)&&isValidProvider(provider)&&isValidColor(color)&&isValidWidth(width)&&isValidLong(long)&&isValidInitialStock(initialStock)
    }
    fun validarFormulario(name: String, contactPerson:String, phoneNumber: String, email: String, clientAddress : String, postalCode: String) {
        formularioValido.value = isValidName(name)&&isValidName(contactPerson)&&isValidPhoneNumber(phoneNumber)&&isValidEmail(email)&&isValidAddress(clientAddress)&&isValidPostalCode(postalCode)
    }


    fun isValidName(name :String ): Boolean {
        if (name.isNullOrEmpty()) {
            errorNombre.value = "Campo obligatorio"
            return false
        } else if(name.length<3||name.length>20){
            errorNombre.value = "La longitud tiene que ser entre 3 y 20 caracteres"
            return false
        }
        errorNombre.value =""
        return true
    }
    fun isValidContactPerson(name :String ): Boolean {
        if (name.isNullOrEmpty()) {
            errorContactPerson.value = "Campo obligatorio"
            return false
        } else if(name.length<3||name.length>20){
            errorContactPerson.value = "La longitud tiene que ser entre 3 y 20 caracteres"
            return false
        }
        errorContactPerson.value =""
        return true
    }
    fun isValidAddress(address :String ): Boolean {
        if (address.isNullOrEmpty()) {
            errorAddress.value = "Campo obligatorio"
            return false
        } else if(address.length<3||address.length>20){
            errorAddress.value = "La longitud tiene que ser entre 3 y 20 caracteres"
            return false
        }
        errorAddress.value =""
        return true
    }
    fun isValidEmail(email :String): Boolean{
        if (email.isNullOrEmpty()){
            errorEmail.value="Campo obligatorio"
            return false
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            errorEmail.value="Dirección de email inválida"
            return false
        }
        errorEmail.value=""
        return true
    }
    fun isValidPhoneNumber(phoneNumber :String): Boolean{
        if (phoneNumber.isNullOrEmpty()){
            errorPhone.value="Campo obligatorio"
            return false
        }
        else if(PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber))
//        else if(PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber))
        {
            errorPhone.value="Formato de teléfono inválido"
            return false
        }
        errorPhone.value=""
        return true
    }
    fun isValidPostalCode(postalCode : String): Boolean {
        if (postalCode.isNullOrEmpty()) {
            errorPostalAddress.value = "Campo obligatorio"
            return false
        } else if(Integer.parseInt(postalCode) !=4){
            errorPostalAddress.value = "El código postal debe ser de cuatro caracteres"
            return false
        }
        errorPostalAddress.value =""
        return true
    }

    fun isValidProvider(provider: String): Boolean {
        if (provider.isNullOrEmpty()) {
            errorProv.value = "Campo obligatorio"
            return false
        } else if(provider.length<3||provider.length>20){
            errorProv.value = "La longitud tiene que ser entre 3 y 20 caracteres"
            return false
        }
        errorProv.value =""
        return true
    }

    fun isValidDesc(desc :String): Boolean {

        if (desc.isNullOrEmpty()) {
            errorDesc.value = "Campo obligatorio"
            return false
        } else if(desc.length<4||desc.length>20){
            errorDesc.value = "La longitud tiene que ser entre 4 y 20 caracteres"
            return false
        }
        errorDesc.value =""
        return true
    }
    fun isValidColor(color: String): Boolean {
        if (color.isNullOrEmpty()) {
            errorColor.value = "Campo obligatorio"
            return false
        } else if(color.length<3||color.length>20){
            errorColor.value = "La longitud tiene que ser entre 3 y 20 caracteres"
            return false
        }
        errorColor.value =""
        return true
    }
    fun isValidLong(long: String): Boolean {
        if (long.isNullOrEmpty()) {
            errorLong.value = "Campo obligatorio"
            return false
        } else if(Integer.parseInt(long) <5||Integer.parseInt(long)>20){
            errorLong.value = "El largo tiene que ser entre 5 y 20 metros"
            return false
        }
        errorLong.value =""
        return true
    }
    fun isValidWidth(width: String): Boolean {
        if (width.isNullOrEmpty()) {
            errorWidth.value = "Campo obligatorio"
            return false
        } else if(Integer.parseInt(width) <5||Integer.parseInt(width)>20){
            errorWidth.value = "El ancho tiene que ser entre 5 y 20 metros"
            return false
        }
        errorWidth.value =""
        return true
    }
    fun isValidInitialStock(initialStock: String): Boolean {
        if (initialStock.isNullOrEmpty()) {
            errorInitialStock.value = "Campo obligatorio"
            return false
        } else if(Integer.parseInt(initialStock) <5||Integer.parseInt(initialStock)>500){
            errorInitialStock.value = "El stock inicial tiene que ser entre 5 y 500 metros"
            return false
        }
        errorInitialStock.value =""
        return true
    }


    suspend fun insertCloth() = coroutineScope {
        try {
            val insertMap = mapOf(
                "id" to cloth.value?.id ,
                "name" to cloth.value?.name.toString(),
                "color" to cloth.value?.color.toString(),
                "description" to cloth.value?.description.toString(),
                "long" to cloth.value?.long,
                "price" to cloth.value?.price,
                "provider" to cloth.value?.provider.toString(),
                "stockActual" to cloth.value?.stockActual,
                "width" to cloth.value?.width
            )
            db.collection("cloths").add(insertMap).await()

        } catch (e: Exception) {
            Log.d("MHTEST", "EXCEPTION EN CREATE CLOTH VIEW MODEL ${e.message}")
        }
    }

}