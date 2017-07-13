package com.dptradeking.contacts.uploader.confirmpassword

import com.dptradeking.contacts.uploader.model.Branch
import com.dptradeking.contacts.uploader.model.Department
import com.dptradeking.contacts.uploader.model.SubBroker
import com.dptradeking.contacts.uploader.util.isOnline
import javafx.scene.control.PasswordField
import javafx.scene.layout.Priority
import tornadofx.*

/**
 * Creator: Varun Barad
 * Date: 11-07-2017
 * Project: uploader
 */
class ConfirmPasswordView : View("Authenticate") {
    val controller: ConfirmPasswordController by inject()

    val contacts: Triple<List<Department>, List<Branch>, List<SubBroker>> by param()

    private var passwordField: PasswordField by singleAssign()

    var passwordText: String = ""
        get() = passwordField.text

    override val root = form {
        fieldset {
            field("Password") {
                passwordField = passwordfield {
                    requestFocus()
                }
            }
            gridpane {
                useMaxWidth = true
                row {
                    button("Upload") {
                        useMaxWidth = true
                        hgrow = Priority.ALWAYS
                        gridpaneConstraints {
                            marginTop = 10.0
                            marginRight = 25.0
                            hGrow = Priority.ALWAYS
                        }
                        action {
                            runAsync {
                                controller.verifyPassword(passwordText)
                            } ui {
                                if (it) {
                                    runAsync {
                                        isOnline()
                                    } ui {
                                        if (it) {
                                            runAsync {
                                                controller.uploadData(headOfficeDepartments = contacts.first, branches = contacts.second, subBrokers = contacts.third)
                                            } ui {
                                                if (it) {
                                                    close()
                                                    information(header = "Success", content = "Contacts updated successfully")
                                                } else {
                                                    error(header = "Error", content = "Some unexpected error occurred, please contact the developer of this program.")
                                                }
                                            }
                                        } else {
                                            error(header = "No Internet Connection", content = "Please make sure that you have a working Internet connection before attempting to update contact details.")
                                        }
                                    }
                                } else {
                                    error(header = "Incorrect Password", content = "Your entered password is incorrect, please check and enter correct password.")
                                }
                            }
                        }
                    }
                    button("Cancel") {
                        useMaxWidth = true
                        hgrow = Priority.ALWAYS
                        gridpaneConstraints {
                            marginTop = 10.0
                            marginLeft = 25.0
                            hGrow = Priority.ALWAYS
                        }
                        action {
                            close()
                        }
                    }
                }
            }
        }
    }
}
