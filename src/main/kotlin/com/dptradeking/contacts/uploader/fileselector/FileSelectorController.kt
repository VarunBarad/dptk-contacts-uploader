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
    lateinit var subBrokers: List<SubBroker>
    lateinit var headOfficeDepartments: List<Department>
    lateinit var branches: List<Branch>

    fun verifyFiles(
            mainFilePath: String,
            headOfficeFilePath: String,
            branchesFilePath: String
    ): Boolean {
        var detailsAppropriate: Boolean

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
            println(e.message)
        } catch (e: NullPointerException) {
            detailsAppropriate = false
            println(e.message)
        } catch (e: IllegalArgumentException) {
            detailsAppropriate = false
            println(e.message)
        }

        return detailsAppropriate
    }


}
