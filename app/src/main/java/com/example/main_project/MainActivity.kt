package com.example.main_project

import MainViewModel
import android.content.pm.ActivityInfo
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.main_project.navigation.NavigationGraph
import com.example.main_project.screens.HomeScreen
import com.example.main_project.screens.StartScreen
import com.example.main_project.ui.theme.MainTheme


class MainActivity : ComponentActivity() {
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            val mainViewModel= ViewModelProvider(this).get(MainViewModel::class.java)
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            MainTheme {
                val NavController = rememberNavController()
                NavigationGraph(NavController,mainViewModel)
            }
            //this.setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)


        }
    }

}
