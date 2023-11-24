package com.example.main_project

import MainViewModel
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.main_project.navigation.NavigationGraph
import com.example.main_project.screens.fromBmp
import com.example.main_project.ui.theme.MainTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.lang.Exception


class MainActivity : ComponentActivity() {

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //saveImageToInternalStorage("MyTestFile",Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888))
            val mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
            var remoteDatabase = FirebaseFirestore.getInstance()

            val remoteStorage = Firebase.storage

            val bitmap =BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.image_placeholder_01)
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytes)
            val data = bytes.toByteArray()
            val task = remoteStorage.reference.child("image.jpg").putBytes(data)
           // val byteArray= byteArrayOf(0,1,2)
            //val ByteEnc:String = Base64.getEncoder().encodeToString(byteArray)
            //remoteDatabase.child("002").setValue(Json.encodeToString(Receipt_data()))
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            MainTheme {
                val NavController = rememberNavController()
                NavigationGraph(NavController,mainViewModel, applicationContext)
               // CreationScreen()
            }
            //this.setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }
    }





    private suspend fun  loadAllFromInternalStorage(){
        return withContext(Dispatchers.IO){
            val files=filesDir.listFiles()
            files?.filter { it.canRead()&&it.isFile&&it.name.endsWith(".jpg") }?.map {
                val bytes = it.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.size)
            }?: listOf()
        }
    }


}

fun deleteFromInternalStorage(filename: String,context: Context):Boolean{
    return try {
        context.deleteFile(filename)
    }catch (e:Exception){
        e.printStackTrace()
        false
    }
}
fun  loadFromInternalStorage(path:String,context: Context):ByteArray{
    if(path!="null"){
        context.openFileInput(path+".jpg").use {stream->
            return stream.readBytes()
        }
    }
        return fromBmp(BitmapFactory.decodeResource(context.getResources(),R.drawable.image_placeholder_01))
    }

fun saveImageToInternalStorage(filename:String, bmp: Bitmap,context: Context):Boolean{
    return try{

        context.openFileOutput("$filename.jpg", MODE_PRIVATE).use { stream ->
            if(!bmp.compress(Bitmap.CompressFormat.JPEG,100,stream)){
                throw IOException("ERROR")
                Log.d("MyLog","Failure $filename.jpg")
            }
            Log.d("MyLog","Success $filename.jpg")
        }
        true
    }catch(e: IOException){
        e.printStackTrace()
        Log.d("MyLog","Failure $filename.jpg")
        false
    }
}