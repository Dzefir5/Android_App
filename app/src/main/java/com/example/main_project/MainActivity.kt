package com.example.main_project

import MainViewModel
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.main_project.navigation.NavigationGraph
import com.example.main_project.screens.CreationScreen
import com.example.main_project.screens.HomeScreen
import com.example.main_project.screens.StartScreen
import com.example.main_project.ui.theme.MainTheme
import java.io.IOException


class MainActivity : ComponentActivity() {

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            //saveImageToInternalStorage("MyTestFile",Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888))
            val mainViewModel= ViewModelProvider(this).get(MainViewModel::class.java)
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            MainTheme {
                val NavController = rememberNavController()
                NavigationGraph(NavController,mainViewModel)
               // CreationScreen()
            }
            //this.setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }
    }

    private fun saveImageToInternalStorage(filename:String, bmp: Bitmap):Boolean{
         return try{
        openFileOutput("$filename.jpg", MODE_PRIVATE).use { stream ->
            if(!bmp.compress(Bitmap.CompressFormat.JPEG,95,stream)){
                throw IOException("ERROR")
            }
        }
        true
        }catch(e: IOException){
            e.printStackTrace()
            false
        }


    }
}
