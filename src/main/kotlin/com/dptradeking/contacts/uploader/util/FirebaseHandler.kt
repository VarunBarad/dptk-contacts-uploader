package com.dptradeking.contacts.uploader.util

import com.dptradeking.contacts.uploader.model.Branch
import com.dptradeking.contacts.uploader.model.Department
import com.dptradeking.contacts.uploader.model.SubBroker
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseCredentials
import com.google.firebase.database.FirebaseDatabase

/**
 * Creator: Varun Barad
 * Date: 08-07-2017
 * Project: uploader
 */

class FirebaseHandler {
    private val databaseName = "dp-tradeking-contacts"

    fun uploadToFirebase(headOffice: List<Department>, branches: List<Branch>, subBrokers: List<SubBroker>) {
        val firebaseDB: FirebaseDatabase = connectToFirebase()

        firebaseDB.reference.child("headOffice").setValue(headOffice)
        firebaseDB.reference.child("branches").setValue(branches)
        firebaseDB.reference.child("subBrokers").setValue(subBrokers)
    }

    private fun connectToFirebase(): FirebaseDatabase {
        if (FirebaseApp.getApps().size < 1) {
            val keyFilePath = "/firebase-adminsdk-key.json"
            val firebaseOptions = FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(this.javaClass.getResourceAsStream(keyFilePath)))
                    .setDatabaseUrl("https://$databaseName.firebaseio.com")
                    .setDatabaseAuthVariableOverride(mapOf(Pair(first = "uid", second = "uploader")))
                    .build()

            FirebaseApp.initializeApp(firebaseOptions)
        }

        return FirebaseDatabase.getInstance()
    }
}
