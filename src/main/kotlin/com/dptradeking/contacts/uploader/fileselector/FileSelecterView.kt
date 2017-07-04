package com.dptradeking.contacts.uploader.fileselector

import javafx.application.Platform
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File

/**
 * Creator: Varun Barad
 * Date: 02-07-2017
 * Project: uploader
 */
class FileSelecterView : View("DP-TradeKING Contacts Uploader") {
    val controller: FileSelectorController by inject()

    val extensionFilter: Array<FileChooser.ExtensionFilter> = arrayOf(FileChooser.ExtensionFilter("Excel Workbook", "*.xlsx"))

    private var mainFileField: TextField by singleAssign()
    private var headOfficeFileField: TextField by singleAssign()
    private var branchesFileField: TextField by singleAssign()

    private var buttonBrowseMainFile: Button by singleAssign()
    private var buttonBrowseHeadOfficeFile: Button by singleAssign()
    private var buttonBrowseBranchesFile: Button by singleAssign()
    private var buttonCancel: Button by singleAssign()
    private var buttonUpload: Button by singleAssign()

    var mainFilePath: String
        get() = mainFileField.text
        set(value) {
            mainFileField.text = value
        }

    var headOfficeFilePath: String
        get() = headOfficeFileField.text
        set(value) {
            headOfficeFileField.text = value
        }

    var branchesFilePath: String
        get() = branchesFileField.text
        set(value) {
            branchesFileField.text = value
        }

    override val root = vbox {
        hbox {
            label("Main File")
            mainFileField = textfield()
            buttonBrowseMainFile = button("Browse") {
                action {
                    val files: List<File> = chooseFile(title = "Select Main workbook", mode = FileChooserMode.Single, filters = extensionFilter)
                    if (files.isNotEmpty()) {
                        mainFilePath = files[0].absolutePath
                    }
                }
            }
        }
        hbox {
            label("Head-Office File")
            headOfficeFileField = textfield()
            buttonBrowseHeadOfficeFile = button("Browse") {
                action {
                    val files: List<File> = chooseFile(title = "Select Head-Office workbook", mode = FileChooserMode.Single, filters = extensionFilter)
                    if (files.isNotEmpty()) {
                        headOfficeFilePath = files[0].absolutePath
                    }
                }
            }
        }
        hbox {
            label("Branches File")
            branchesFileField = textfield()
            buttonBrowseBranchesFile = button("Browse") {
                action {
                    val files: List<File> = chooseFile(title = "Select Branches workbook", mode = FileChooserMode.Single, filters = extensionFilter)
                    if (files.isNotEmpty()) {
                        branchesFilePath = files[0].absolutePath
                    }
                }
            }
        }
        hbox {
            buttonCancel = button("Cancel & Exit") {
                action {
                    Platform.exit()
                }
            }
            buttonUpload = button("Verify & Upload") {
                action {
                    runAsync {
                        controller.verifyFiles(mainFilePath, headOfficeFilePath, branchesFilePath)
                    } ui {
                        if (it) {
                            println("Data in files is correct")
                        } else {
                            println("Data is not as required")
                        }
                    }
                }
            }
        }
    }
}
