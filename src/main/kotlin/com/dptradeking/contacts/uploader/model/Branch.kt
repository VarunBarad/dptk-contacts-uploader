package com.dptradeking.contacts.uploader.model

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import tornadofx.*

/**
 * Creator: Varun Barad
 * Date: 28-06-2017
 * Project: uploader
 */
data class Branch(
        @Expose
        @SerializedName("name")
        val name: String,
        @Expose
        @SerializedName("alias")
        val alias: String,
        @Expose
        @SerializedName("address")
        val address: String,
        @Expose
        @SerializedName("contactNumber")
        val contactNumber: String,
        @Expose
        @SerializedName("executives")
        val executives: List<Executive>
) {
    companion object {
        @JvmStatic
        fun getInstance(jsonBranch: String): Branch? {
            var branch: Branch? by singleAssign()

            try {
                branch = Gson().fromJson(jsonBranch, Branch::class.java)
            } catch (e: JsonSyntaxException) {
                branch = null
                e.printStackTrace()
            }

            return branch
        }
    }

    fun validateName(): Boolean = this.name.isNotEmpty()

    fun validateAlias(): Boolean = this.alias.isNotEmpty()

    fun validateAddress(): Boolean = this.address.isNotEmpty()

    fun validateContactNumber(): Boolean = this.contactNumber.isNotEmpty() && this.contactNumber.matches("^(\\+91)?[1-9][0-9]{9}$".toRegex())

    fun validateExecutives(): Boolean = this.executives.filter({ it.validateDetails() }).isNotEmpty()

    fun validateDetails(): Boolean = this.validateName() &&
            this.validateAlias() &&
            this.validateAddress() &&
            this.validateContactNumber() &&
            this.validateExecutives()

    fun toJsonString(): String = Gson().toJson(this)
}
