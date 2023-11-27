package com.example.main_project.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.example.main_project.R
import com.example.main_project.loadFromInternalStorage
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.ByteArrayOutputStream

class RemoteRepository(private val remoteDataBase:FirebaseFirestore,private val remoteStorage:FirebaseStorage) {
    fun insertData(receiptData: Receipt_data,context: Context) {
        remoteDataBase
            .collection("test_01")
            .document("${receiptData.name}").set(receiptData)
        val bytes = loadFromInternalStorage(receiptData.imageBmp, context = context )
        val task = remoteStorage.reference.child("images").child("${receiptData.imageBmp}.jpg").putBytes(bytes)
        receiptData.stepsList.forEach(){step->
            val bytes = loadFromInternalStorage(step.image, context = context )
            val task = remoteStorage.reference.child("images").child("${step.image}.jpg").putBytes(bytes)
        }
    }

    fun fetchAllData()=remoteDataBase
                .collection("test_01")
                .snapshots()
                .map {
                    it.toObjects<Receipt_data>()
                }
   // fun updateData(receiptData: Receipt_data) =remoteDataBase.child("receipts_01").updateChildren(mapOf<String,Receipt_data>(receiptData.receiptId.toString() to receiptData) )

    fun deleteData(receiptData: Receipt_data) {
        remoteStorage.reference.child("images").child(receiptData.imageBmp+".jpg").delete()
        receiptData.stepsList.forEach(){
            remoteStorage.reference.child("images").child(it.image+".jpg").delete()
        }
        remoteDataBase.collection("test_01").document(receiptData.name).delete()
    }

}