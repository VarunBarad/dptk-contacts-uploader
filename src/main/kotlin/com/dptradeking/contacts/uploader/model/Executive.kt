package com.dptradeking.contacts.uploader.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

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
    fun validateName(): Boolean = this.name.isNotEmpty()

    fun validateDesignation(): Boolean = this.designation.isNotEmpty()

    fun validateContactNumber(): Boolean = this.contactNumber.isNotEmpty() && this.contactNumber.matches("^(\\+91)?[1-9][0-9]{9}$".toRegex())

    fun validateEmail(): Boolean = this.email.isNotEmpty() && this.email.matches("^[_A-Za-z0-9\\-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$".toRegex())

    fun validateDetails(): Boolean = this.validateName() &&
            this.validateDesignation() &&
            this.validateContactNumber() &&
            this.validateEmail()
}
