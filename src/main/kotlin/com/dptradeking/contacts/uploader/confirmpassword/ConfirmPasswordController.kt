package com.dptradeking.contacts.uploader.confirmpassword

import com.dptradeking.contacts.uploader.model.Branch
import com.dptradeking.contacts.uploader.model.Department
import com.dptradeking.contacts.uploader.model.SubBroker
import com.dptradeking.contacts.uploader.util.FirebaseHandler
import com.dptradeking.contacts.uploader.util.sha256Hash
import tornadofx.*
import java.util.*

/**
 * Creator: Varun Barad
 * Date: 11-07-2017
 * Project: uploader
 */
class ConfirmPasswordController : Controller() {
    private val passwordFilePath = "/password"

    fun uploadData(headOfficeDepartments: List<Department>, branches: List<Branch>, subBrokers: List<SubBroker>): Boolean {
        FirebaseHandler().uploadToFirebase(headOffice = headOfficeDepartments, branches = branches, subBrokers = subBrokers)

        return true
    }

    fun verifyPassword(passwordText: String): Boolean {
        val scanner = Scanner(this.javaClass.getResourceAsStream(passwordFilePath))
        val storedHash = scanner.nextLine()
        scanner.close()

        return (sha256Hash(passwordText) == storedHash)
    }
}
