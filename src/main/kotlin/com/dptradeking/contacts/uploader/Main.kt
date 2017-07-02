package com.dptradeking.contacts.uploader

import com.dptradeking.contacts.uploader.fileselector.FileSelecterView
import javafx.application.Application
import javafx.stage.Stage
import tornadofx.*

/**
 * Creator: Varun Barad
 * Date: 03-06-2017
 * Project: uploader
 */
class DPTKUploader : App(FileSelecterView::class) {
    override fun start(stage: Stage) {
        stage.isResizable = false
        super.start(stage)
    }
}

fun main(args: Array<String>) {
    Application.launch(DPTKUploader::class.java, *args)
}
