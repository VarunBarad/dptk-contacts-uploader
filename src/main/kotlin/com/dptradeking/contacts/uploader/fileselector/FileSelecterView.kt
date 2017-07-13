package com.dptradeking.contacts.uploader.fileselector

import com.dptradeking.contacts.uploader.confirmpassword.ConfirmPasswordView
import javafx.application.Platform
import javafx.scene.control.TextField
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File

/**
 * Creator: Varun Barad
 * Date: 02-07-2017
 * Project: uploader
 */
class FileSelecterView : View("DP-TradeKING Contacts") {
    val controller: FileSelectorController by inject()

    private val extensionFilter: Array<FileChooser.ExtensionFilter> = arrayOf(FileChooser.ExtensionFilter("Excel Workbook", "*.xlsx"))

    private var mainFileField: TextField by singleAssign()
    private var headOfficeFileField: TextField by singleAssign()
    private var branchesFileField: TextField by singleAssign()

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

    override val root = form {
        fieldset("Choose files") {
            field("Main File") {
                mainFileField = textfield {
                    requestFocus()
                }
                button("Browse") {
                    action {
                        val files: List<File> = chooseFile(title = "Select Main workbook", mode = FileChooserMode.Single, filters = extensionFilter)
                        if (files.isNotEmpty()) {
                            mainFilePath = files[0].absolutePath
                        }
                    }
                }
            }
            field("Head-Office File") {
                headOfficeFileField = textfield()
                button("Browse") {
                    action {
                        val files: List<File> = chooseFile(title = "Select Head-Office workbook", mode = FileChooserMode.Single, filters = extensionFilter)
                        if (files.isNotEmpty()) {
                            headOfficeFilePath = files[0].absolutePath
                        }
                    }
                }
            }
            field("Branches File") {
                branchesFileField = textfield()
                button("Browse") {
                    action {
                        val files: List<File> = chooseFile(title = "Select Branches workbook", mode = FileChooserMode.Single, filters = extensionFilter)
                        if (files.isNotEmpty()) {
                            branchesFilePath = files[0].absolutePath
                        }
                    }
                }
            }
            borderpane {
                right {
                    button("Verify & Upload") {
                        action {
                            runAsync {
                                controller.verifyFiles(mainFilePath, headOfficeFilePath, branchesFilePath)
                            } ui {
                                if (it.first) {
                                    openInternalWindow(view = find<ConfirmPasswordView>(mapOf(ConfirmPasswordView::contacts to it.third)))
                                } else {
                                    error(header = "Error in supplied data", content = it.second)
                                }
                            }
                        }
                    }
                }
                left {
                    button("Exit") {
                        action {
                            Platform.exit()
                        }
                    }
                }
            }
        }
    }
}
