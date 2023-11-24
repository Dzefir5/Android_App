package com.example.main_project.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.platform.LocalContext
import com.example.main_project.R
import com.example.main_project.loadFromInternalStorage
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.ByteArrayOutputStream

class RemoteRepository(private val remoteDataBase:FirebaseFirestore,private val remoteStorage:FirebaseStorage) {
    fun insertData(receiptData: Receipt_data,context: Context) {
        remoteDataBase
            .collection("test_01")
            .document("${receiptData.name}").set(receiptData)
        val bytes = loadFromInternalStorage(receiptData.imageBmp, context = context )
        val task = remoteStorage.reference.child("${receiptData.imageBmp}.jpg").putBytes(bytes)
        receiptData.stepsList.forEach(){step->
            val bytes = loadFromInternalStorage(step.image, context = context )
            val task = remoteStorage.reference.child("${step.image}.jpg").putBytes(bytes)
        }
    }
    fun fetchData():MutableList<Receipt_data> {
        var tempList : MutableList<Receipt_data> = mutableListOf()
        remoteDataBase
            .collection("test_01")
            .get()
            .addOnSuccessListener {documents ->
                for(document in documents){
                    val receipt_data = document.toObject<Receipt_data>()
                    tempList.add(receipt_data)
                }
            }
            .addOnFailureListener() {}
        return tempList
    }
   // fun updateData(receiptData: Receipt_data) =remoteDataBase.child("receipts_01").updateChildren(mapOf<String,Receipt_data>(receiptData.receiptId.toString() to receiptData) )

   // fun deleteData(receiptData: Receipt_data) =remoteDataBase.child("receipts_01").child(receiptData.receiptId.toString()).removeValue()

}