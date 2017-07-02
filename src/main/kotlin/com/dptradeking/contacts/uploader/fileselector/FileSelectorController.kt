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
class FileSelectorController(val view: FileSelecterView) : Controller() {
    lateinit var subBrokers: List<SubBroker>
    lateinit var headOfficeDepartments: List<Department>
    lateinit var branches: List<Branch>

    fun verifyFiles(): Boolean {
        var detailsAppropriate = false

        try {
            val mainFile: File = File(this.view.getMainFilePath())
            val headOfficeFile: File = File(this.view.getHeadOfficeFilePath())
            val branchesFile: File = File(this.view.getBranchesFilePath())

            subBrokers = getSubBrokers(mainFile)
            headOfficeDepartments = getDepartments(mainFile, headOfficeFile)
            branches = getBranches(mainFile, branchesFile)
        } catch (e: FileNotFoundException) {
            detailsAppropriate = false
        }

        return detailsAppropriate
    }
}
