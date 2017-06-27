package com.dptradeking.contacts.uploader.model

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import tornadofx.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Creator: Varun Barad
 * Date: 27-06-2017
 * Project: uploader
 */
data class SubBroker(
        @Expose
        @SerializedName("name")
        val name: String,
        @Expose
        @SerializedName("address")
        val address: String,
        @Expose
        @SerializedName("contactNumber")
        val contactNumber: String,
        @Expose
        @SerializedName("email")
        val email: String,
        @Expose
        @SerializedName("registrationNumber")
        val registrationNumber: String,
        @Expose
        @SerializedName("incorporationDate")
        val incorporationDate: String
) {
    fun getInstance(jsonSubBroker: String): SubBroker? {
        var subBroker: SubBroker? by singleAssign()

        try {
            subBroker = Gson().fromJson(jsonSubBroker, SubBroker::class.java)
        } catch (e: JsonSyntaxException) {
            subBroker = null
            e.printStackTrace()
        }

        return subBroker
    }

    fun validateName(): Boolean {
        val name: String = this.name
        val isValid: Boolean

        isValid = name.isNotEmpty()

        return isValid
    }

    fun validateAddress(): Boolean {
        val address: String = this.address
        val isValid: Boolean

        isValid = address.isNotEmpty()

        return isValid
    }

    fun validateContactNumber(): Boolean {
        val contactNumber: String = this.contactNumber
        val isValid: Boolean

        isValid = contactNumber.isNotEmpty() && contactNumber.matches("^(\\+91)?[1-9][0-9]{9}$".toRegex())

        return isValid
    }

    fun validateEmail(): Boolean {
        val email: String = this.email
        val isValid: Boolean

        isValid = email.isNotEmpty() && email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$".toRegex())

        return isValid
    }

    fun validateRegistrationNumber(): Boolean {
        val registrationNumber: String = this.registrationNumber
        val isValid: Boolean

        //ToDo: Check registration number regex
        isValid = registrationNumber.isNotEmpty()

        return isValid
    }

    fun validateIncorporationDate(): Boolean {
        val incorporationDate: String = this.incorporationDate
        var isValid: Boolean by singleAssign()

        if (incorporationDate.isNotEmpty()) {
            isValid = false
        } else {
            try {
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                val date = formatter.parse(incorporationDate)
                val currentDate = Date()
                isValid = !currentDate.before(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                isValid = false
            }

        }

        return isValid
    }

    fun validateDetails(): Boolean {
        return this.validateName() &&
                this.validateAddress() &&
                this.validateContactNumber() &&
                this.validateEmail() &&
                this.validateRegistrationNumber() &&
                this.validateIncorporationDate()
    }

    fun toJsonString(): String {
        return Gson().toJson(this)
    }
}
