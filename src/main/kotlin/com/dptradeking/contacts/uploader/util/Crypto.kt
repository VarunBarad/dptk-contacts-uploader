package com.dptradeking.contacts.uploader.util

/**
 * Creator: Varun Barad
 * Date: 07-07-2017
 * Project: uploader
 */

fun encrypt(plainText: String, key: String): String = plainText
        .toCharArray()
        .mapIndexed({ index, c ->
            (c.toInt() + key[index % key.length].toInt()).toChar()
        })
        .joinToString(separator = "")
