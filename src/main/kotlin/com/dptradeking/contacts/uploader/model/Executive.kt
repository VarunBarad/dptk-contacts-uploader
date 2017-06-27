package com.dptradeking.contacts.uploader.model

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import tornadofx.*

/**
 * Creator: Varun Barad
 * Date: 27-06-2017
 * Project: uploader
 */
data class Executive(
        @Expose
        @SerializedName("name")
        val name: String,
        @Expose
        @SerializedName("designation")
        val designation: String,
        @Expose
        @SerializedName("contactNumber")
        val contactNumber: String,
        @Expose
        @SerializedName("email")
        val email: String
) {
    fun getInstance(jsonExecutive: String): Executive? {
        var executive: Executive? by singleAssign()

        try {
            executive = Gson().fromJson(jsonExecutive, Executive::class.java)
        } catch (e: JsonSyntaxException) {
            executive = null
            e.printStackTrace()
        }

        return executive
    }

    fun validateName(): Boolean {
        val name: String = this.name
        val isValid: Boolean

        isValid = name.isNotEmpty()

        return isValid
    }

    fun validateDesignation(): Boolean {
        val designation: String = this.designation
        val isValid: Boolean

        isValid = designation.isNotEmpty()

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

    fun validateDetails(): Boolean {
        return this.validateName() &&
                this.validateDesignation() &&
                this.validateContactNumber() &&
                this.validateEmail()
    }

    fun toJsonString(): String {
        return Gson().toJson(this)
    }
}
