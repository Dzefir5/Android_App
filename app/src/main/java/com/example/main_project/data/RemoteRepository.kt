package com.example.main_project.data

import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class RemoteRepository(private val remoteDataBase:DatabaseReference) {
    fun insertData(receiptData: Receipt_data) {
        remoteDataBase
            .child("receipts_01")
            .child(receiptData.receiptId.toString())
            .setValue(mapOf<String,Receipt_data>(receiptData.receiptId.toString() to receiptData))
    }


    fun updateData(receiptData: Receipt_data) =remoteDataBase.child("receipts_01").updateChildren(mapOf<String,Receipt_data>(receiptData.receiptId.toString() to receiptData) )

    fun deleteData(receiptData: Receipt_data) =remoteDataBase.child("receipts_01").child(receiptData.receiptId.toString()).removeValue()

}