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
    companion object {
        @JvmStatic
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
    }

    fun validateName(): Boolean = this.name.isNotEmpty()

    fun validateAddress(): Boolean = this.address.isNotEmpty()

    fun validateContactNumber(): Boolean = this.contactNumber.isNotEmpty() && this.contactNumber.matches("^(\\+91)?[1-9][0-9]{9}$".toRegex())

    fun validateEmail(): Boolean = this.email.isNotEmpty() && this.email.matches("^[_A-Za-z0-9\\-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$".toRegex())

    fun validateRegistrationNumber(): Boolean {
        val isValid: Boolean

        //ToDo: Check registration number regex
        isValid = this.registrationNumber.isNotEmpty()

        return isValid
    }

    fun validateIncorporationDate(): Boolean {
        val incorporationDate: String = this.incorporationDate
        var isValid: Boolean by singleAssign()

        if (incorporationDate.isNotEmpty()) {
            try {
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                val date = formatter.parse(incorporationDate)
                val currentDate = Date()
                isValid = !currentDate.before(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                isValid = false
            }
        } else {
            isValid = false
        }

        return isValid
    }

    fun validateDetails(): Boolean = this.validateName() &&
            this.validateAddress() &&
            this.validateContactNumber() &&
            this.validateEmail() &&
            this.validateRegistrationNumber() &&
            this.validateIncorporationDate()

    fun toJsonString(): String = Gson().toJson(this)
}
