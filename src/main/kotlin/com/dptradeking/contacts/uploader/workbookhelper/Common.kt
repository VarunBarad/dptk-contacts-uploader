package com.dptradeking.contacts.uploader.workbookhelper

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFSheet

/**
 * Creator: Varun Barad
 * Date: 29-06-2017
 * Project: uploader
 */

fun getTitlesFromWorksheet(sheet: XSSFSheet): Map<String, Int> {
    val titles: MutableMap<String, Int> = mutableMapOf()

    val titlesRow: Row? = sheet.getRow(0)

    if (titlesRow != null) {
        titlesRow
                .cellIterator()
                .withIndex()
                .forEach { titles.put(it.value.stringCellValue, it.index) }
    } else {
        throw IllegalArgumentException("You need to have a titles row in your ${sheet.sheetName} sheet.")
    }

    return titles
}
