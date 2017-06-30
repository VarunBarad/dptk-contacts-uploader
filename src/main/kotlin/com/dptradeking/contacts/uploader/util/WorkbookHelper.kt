package com.dptradeking.contacts.uploader.util

import com.dptradeking.contacts.uploader.model.Executive
import com.dptradeking.contacts.uploader.model.SubBroker
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File

/**
 * Creator: Varun Barad
 * Date: 29-06-2017
 * Project: uploader
 */

fun getTitlesFromWorksheet(sheet: Sheet): Map<String, Int> {
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

fun getExecutives(executivesFile: File): Map<String, List<Executive>> {
    val executivesMap: MutableMap<String, List<Executive>> = mutableMapOf()

    val workbook = XSSFWorkbook(executivesFile.inputStream())

    workbook
            .asIterable()
            .forEach { executivesMap.put(it.sheetName, sheetToExecutives(it)) }

    workbook.close()

    return executivesMap
}

fun sheetToExecutives(sheet: Sheet): List<Executive> {
    val titles = getTitlesFromWorksheet(sheet)

    // Check whether all the columns required are present or not
    if (!titles.containsKey("name")) {
        throw NullPointerException("The name of the executive must come under a column titled \"name\" in a sheet named \"${sheet.sheetName}\".\nIn case if you don't want to have that information, keep an empty cell under the column containing that title.")
    }
    if (!titles.containsKey("designation")) {
        throw NullPointerException("The designation of the executive must come under a column titled \"designation\" in a sheet named \"${sheet.sheetName}\".\nIn case if you don't want to have that information, keep an empty cell under the column containing that title.")
    }
    if (!titles.containsKey("contactNumber")) {
        throw NullPointerException("The contact-number of the executive must come under a column titled \"contactNumber\" in a sheet named \"${sheet.sheetName}\".\nIn case if you don't want to have that information, keep an empty cell under the column containing that title.")
    }
    if (!titles.containsKey("email")) {
        throw NullPointerException("The email of the executive must come under a column titled \"email\" in a sheet named \"${sheet.sheetName}\".\nIn case if you don't want to have that information, keep an empty cell under the column containing that title.")
    }

    val executives: MutableList<Executive> = mutableListOf()

    val rowIterator = sheet.rowIterator()
    rowIterator.next() // Skip the titles row

    rowIterator.forEachRemaining {
        val executive = Executive(
                it.getCell(titles["name"]!!).stringCellValue,
                it.getCell(titles["designation"]!!).stringCellValue,
                it.getCell(titles["contactNumber"]!!).stringCellValue,
                it.getCell(titles["email"]!!).stringCellValue
        )

        if (executive.validateDetails()) {
            executives.add(executive)
        } else {
            throw NullPointerException("Invalid details for the executive in row ${it.rowNum + 1} in a sheet named ${sheet.sheetName}.")
        }
    }

    return executives
}

fun getSubBrokers(mainFile: File): List<SubBroker> {
    val subBrokers: MutableList<SubBroker>

    val workbook: XSSFWorkbook = XSSFWorkbook(mainFile.inputStream())
    val subBrokersSheet: Sheet? = workbook.getSheet("Sub-Brokers")

    if (subBrokersSheet != null) {
        subBrokers = mutableListOf()
        val titles = getTitlesFromWorksheet(subBrokersSheet)

        // Check whether all required columns are present or not
        if (!titles.containsKey("name")) {
            throw NullPointerException("The name of the sub-broker must come under a column titled \"name\" in the sheet named \"Sub-Brokers\".\nIn case if you don't want to have that information, keep an empty cell under the column containing that title.")
        }
        if (!titles.containsKey("address")) {
            throw NullPointerException("The address of the sub-broker must come under a column titled \"address\" in the sheet named \"Sub-Brokers\".\nIn case if you don't want to have that information, keep an empty cell under the column containing that title.")
        }
        if (!titles.containsKey("contactNumber")) {
            throw NullPointerException("The contact-number of the sub-broker must come under a column titled \"contactNumber\" in the sheet named \"Sub-Brokers\".\nIn case if you don't want to have that information, keep an empty cell under the column containing that title.")
        }
        if (!titles.containsKey("email")) {
            throw NullPointerException("The email of the sub-broker must come under a column titled \"email\" in the sheet named \"Sub-Brokers\".\nIn case if you don't want to have that information, keep an empty cell under the column containing that title.")
        }
        if (!titles.containsKey("registrationNumber")) {
            throw NullPointerException("The registration-number of the sub-broker must come under a column titled \"registrationNumber\" in the sheet named \"Sub-Brokers\".\nIn case if you don't want to have that information, keep an empty cell under the column containing that title.")
        }
        if (!titles.containsKey("incorporationDate")) {
            throw NullPointerException("The incorporation-date of the sub-broker must come under a column titled \"incorporationDate\" in the sheet named \"Sub-Brokers\".\nIn case if you don't want to have that information, keep an empty cell under the column containing that title.")
        }

        val rowIterator = subBrokersSheet.rowIterator()
        rowIterator.next() // Skip the titles row

        rowIterator.forEachRemaining { row ->
            val s: SubBroker = SubBroker(
                    row.getCell(titles["name"]!!).stringCellValue,
                    row.getCell(titles["address"]!!).stringCellValue,
                    row.getCell(titles["contactNumber"]!!).stringCellValue,
                    row.getCell(titles["email"]!!).stringCellValue,
                    row.getCell(titles["registrationNumber"]!!).stringCellValue,
                    row.getCell(titles["incorporationDate"]!!).stringCellValue
            )

            if (s.validateDetails()) {
                subBrokers.add(s)
            } else {
                throw NullPointerException("The details of sub-brokers needs to go in a worksheet named \"Sub-Brokers\".\nIn the case when you don't have any sub-brokers keep an empty sheet by the same name.")
            }
        }

        workbook.close()
    } else {
        workbook.close()
        throw IllegalArgumentException("The details of sub-brokers needs to go in a worksheet named \"Sub-Brokers\".\n" +
                "In the case when you don't have any sub-brokers keep an empty sheet by the same name.")
    }

    return subBrokers
}
