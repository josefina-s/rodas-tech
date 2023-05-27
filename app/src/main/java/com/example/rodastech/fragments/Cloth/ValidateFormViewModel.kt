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
    val errorActualStock = MutableLiveData<String?>()
    val errorPrice = MutableLiveData<String?>()
    val errorCuit = MutableLiveData<String?>()

    val errorContactPerson = MutableLiveData<String?>()
    val errorPhone = MutableLiveData<String?>()
    val errorEmail = MutableLiveData<String?>()
    val errorAddress= MutableLiveData<String?>()
    val errorPostalAddress = MutableLiveData<String?>()

    val formularioValido = MutableLiveData<Boolean>()


    fun validarFormulario(name: String, desc: String, provider: String, color: String, width: String, long: String, initialStock: String, price: String) {
        formularioValido.value = isValidName(name)&&isValidDesc(desc)&&isValidProvider(provider)&&isValidColor(color)&&isValidWidth(width)&&isValidLong(long)&&isValidInitialStock(initialStock)&&isValidPrice(price)
    }
    fun validarFormulario(name: String, cuit: String,contactPerson:String, phoneNumber: String, email: String, clientAddress : String, postalCode: String) {
        formularioValido.value = isValidName(name)&&isValidCUIT(cuit)&&isValidContactPerson(contactPerson)&&isValidPhoneNumber(phoneNumber)&&isValidEmail(email)&&isValidAddress(clientAddress)&&isValidPostalCode(postalCode)
    }
    fun validarFormulario(actualStock: String) {
        formularioValido.value = isValidActualStock(actualStock)
    }

    fun isValidActualStock(actualStock: String): Boolean {
        if (actualStock.isNullOrEmpty()){
            errorActualStock.value="Campo obligatorio"
            return false
        }
        return true
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
        val patron = Regex("^\\d{10}$")

        if (phoneNumber.isNullOrEmpty()){
            errorPhone.value="Campo obligatorio"
            return false
        }
        else if(!patron.matches(phoneNumber)){
            errorPhone.value="Formato de teléfono inválido"
            return false
        }
        errorPhone.value=""
        return true
    }
    fun isValidPostalCode(postalCode : String): Boolean {
        val patron = Regex("^\\d{4}(-\\d{3})?$")
//        return patron.matches(codigoPostal)
        if (postalCode.isNullOrEmpty()) {
            errorPostalAddress.value = "Campo obligatorio"
            return false
        } else if(!patron.matches(postalCode)){
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
        } else if(desc.length<4||desc.length>50){
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
    fun isValidPrice(price: String): Boolean {
        if (price.isNullOrEmpty()) {
            errorPrice.value = "Campo obligatorio"
            return false
        } else if(Integer.parseInt(price) <999||Integer.parseInt(price)>50000){
            errorPrice.value = "El precio tiene que ser entre 1000 y 50000 metros"
            return false
        }
        errorPrice.value =""
        return true
    }
    fun isValidInitialStock(initialStock: String): Boolean {
        if (initialStock.isNullOrEmpty()) {
            errorInitialStock.value = "Campo obligatorio"
            return false
        } else if(Integer.parseInt(initialStock) <10||Integer.parseInt(initialStock)>999){
            errorInitialStock.value = "El stock mínimo tiene que ser entre 10 y 1000 metros"
            return false
        }
        errorInitialStock.value =""
        return true
    }

    fun isValidCUIT(cuit: String): Boolean {
        val cuitLimpio = cuit.replace("-", "") // Eliminar guiones si los hubiera
        if (cuit.isNullOrEmpty()) {
            errorCuit.value = "Campo obligatorio"
            return false
        }

        if (cuitLimpio.length != 11 || !cuitLimpio.all { it.isDigit() }) {
            errorCuit.value = "Formato incorrecto"
            return false // Longitud incorrecta o contiene caracteres no numéricos
        }

        val factores = intArrayOf(5, 4, 3, 2, 7, 6, 5, 4, 3, 2) // Factores de multiplicación

        var suma = 0
        for (i in 0 until 10) {
            suma += Character.getNumericValue(cuitLimpio[i]) * factores[i]
        }

        val digitoVerificador = 11 - (suma % 11)
        val ultimoDigito = Character.getNumericValue(cuitLimpio[10])

        if (digitoVerificador == 11) {
            return ultimoDigito == 0
        } else if (digitoVerificador == 10) {
            return ultimoDigito == 9
        }
        if (digitoVerificador == ultimoDigito){
            errorCuit.value = ""
            return true
        }
        else{
            errorCuit.value = "CUIT incorrecto"
            return false
        }
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