package com.dptradeking.contacts.uploader.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Creator: Varun Barad
 * Date: 27-06-2017
 * Project: uploader
 */
data class Department(
        @Expose
        @SerializedName("name")
        val name: String,
        @Expose
        @SerializedName("alias")
        val alias: String,
        @Expose
        @SerializedName("executives")
        val executives: List<Executive>
) {
    fun validateName(): Boolean = this.name.isNotEmpty()

    fun validateAlias(): Boolean = this.alias.isNotEmpty()

    fun validateExecutives(): Boolean = this.executives.filter({ it.validateDetails() }).isNotEmpty()

    fun validateDetails(): Boolean = this.validateName() &&
            this.validateAlias() &&
            this.validateExecutives()
}
