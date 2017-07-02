package com.dptradeking.contacts.uploader.fileselector

import javafx.scene.control.Button
import javafx.scene.control.TextField
import tornadofx.*

/**
 * Creator: Varun Barad
 * Date: 02-07-2017
 * Project: uploader
 */
class FileSelecterView : View("DP-TradeKING Contacts Uploader") {
    val controller: FileSelectorController by inject()

    var mainFileField: TextField by singleAssign()
    var headOfficeFileField: TextField by singleAssign()
    var branchesFileField: TextField by singleAssign()

    var buttonBrowseMainFile: Button by singleAssign()
    var buttonBrowseHeadOfficeFile: Button by singleAssign()
    var buttonBrowseBranchesFile: Button by singleAssign()
    var buttonCancel: Button by singleAssign()
    var buttonUpload: Button by singleAssign()

    override val root = vbox {
        hbox {
            label("Main File")
            mainFileField = textfield()
            buttonBrowseMainFile = button("Browse") {

            }
        }
        hbox {
            label("Head-Office File")
            headOfficeFileField = textfield()
            buttonBrowseHeadOfficeFile = button("Browse") {

            }
        }
        hbox {
            label("Branches File")
            branchesFileField = textfield()
            buttonBrowseBranchesFile = button("Browse") {

            }
        }
        hbox {
            buttonCancel = button("Cancel & Exit") {

            }
            buttonUpload = button("Verify & Upload") {
                action {
                    runAsync {
                        controller.verifyFiles()
                    } ui {

                    }
                }
            }
        }
    }

    fun getMainFilePath(): String = this.mainFileField.text

    fun getHeadOfficeFilePath(): String = this.headOfficeFileField.text

    fun getBranchesFilePath(): String = this.branchesFileField.text
}
