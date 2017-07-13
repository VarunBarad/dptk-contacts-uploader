package com.dptradeking.contacts.uploader.fileselector

import com.dptradeking.contacts.uploader.model.Branch
import com.dptradeking.contacts.uploader.model.Department
import com.dptradeking.contacts.uploader.model.SubBroker
import com.dptradeking.contacts.uploader.util.getBranches
import com.dptradeking.contacts.uploader.util.getDepartments
import com.dptradeking.contacts.uploader.util.getSubBrokers
import tornadofx.*
import java.io.File
import java.io.FileNotFoundException

/**
 * Creator: Varun Barad
 * Date: 02-07-2017
 * Project: uploader
 */
class FileSelectorController : Controller() {
    fun verifyFiles(
            mainFilePath: String,
            headOfficeFilePath: String,
            branchesFilePath: String
    ): Triple<Boolean, String, Triple<List<Department>, List<Branch>, List<SubBroker>>> {
        var detailsAppropriate: Boolean
        var message: String = ""

        var subBrokers: List<SubBroker> = emptyList()
        var headOfficeDepartments: List<Department> = emptyList()
        var branches: List<Branch> = emptyList()

        try {
            val mainFile: File = File(mainFilePath)
            val headOfficeFile: File = File(headOfficeFilePath)
            val branchesFile: File = File(branchesFilePath)

            subBrokers = getSubBrokers(mainFile)
            headOfficeDepartments = getDepartments(mainFile, headOfficeFile)
            branches = getBranches(mainFile, branchesFile)

            detailsAppropriate = true
        } catch (e: FileNotFoundException) {
            detailsAppropriate = false
            message = e.message!!
        } catch (e: NullPointerException) {
            detailsAppropriate = false
            message = e.message!!
        } catch (e: IllegalArgumentException) {
            detailsAppropriate = false
            message = e.message!!
        }

        return Triple(detailsAppropriate, message, Triple(headOfficeDepartments, branches, subBrokers))
    }
}
