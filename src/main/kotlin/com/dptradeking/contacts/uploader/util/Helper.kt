package com.dptradeking.contacts.uploader.util

import java.net.InetAddress
import java.net.UnknownHostException
import java.security.MessageDigest


/**
 * Creator: Varun Barad
 * Date: 13-07-2017
 * Project: uploader
 */

fun sha256Hash(input: String): String {
    val md = MessageDigest.getInstance("SHA-256")
    md.update(input.toByteArray(Charsets.UTF_8))
    val byteData = md.digest()

    val sb = StringBuffer()
    for (byte in byteData) {
        sb.append("%02x".format(byte))
    }
    return sb.toString()
}

fun isOnline(): Boolean {
    try {
        InetAddress.getByName("dp-tradeking-contacts.firebaseio.com")
        return true
    } catch (e: UnknownHostException) {
        return false
    }
}
