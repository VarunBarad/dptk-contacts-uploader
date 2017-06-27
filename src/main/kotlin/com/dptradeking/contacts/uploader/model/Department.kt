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
    fun getInstance(jsonDepartment: String): Department? {
        var department: Department? by singleAssign()

        try {
            department = Gson().fromJson(jsonDepartment, Department::class.java)
        } catch (e: JsonSyntaxException) {
            department = null
            e.printStackTrace()
        }

        return department
    }

    fun validateName(): Boolean = this.name.isNotEmpty()

    fun validateAlias(): Boolean = this.alias.isNotEmpty()

    fun validateExecutives(): Boolean = this.executives.filter({ it.validateDetails() }).isNotEmpty()

    fun validateDetails(): Boolean = this.validateName() &&
            this.validateAlias() &&
            this.validateExecutives()

    fun toJsonString(): String = Gson().toJson(this)
}
