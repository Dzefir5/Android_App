package com.example.main_project.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController

@Preview
@Composable
fun StartScreen(/*navController: NavHostController*/){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(brush = Brush.verticalGradient(listOf(Color.Blue, Color.White)))
    ){}
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(40.dp,40.dp,0.dp,0.dp),
            elevation = CardDefaults.cardElevation(10.dp)
        ){
            Card(
                modifier = Modifier
                    .height(64.dp)
                    .width(263.dp)
                    .align(Alignment.CenterHorizontally)
                    .offset(y = 200.dp),
                shape = RoundedCornerShape(21.dp),
                colors = CardDefaults.cardColors(Color.Blue)
            ){}
            Card(
                modifier = Modifier
                    .height(23.dp)
                    .width(304.dp)
                    .align(Alignment.CenterHorizontally)
                    .offset(y = 300.dp),
                shape = RoundedCornerShape(11.dp),
                colors = CardDefaults.cardColors(Color.Blue)
            ){}
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        //.background(brush = Brush.radialGradient(listOf(Color.Blue, Color.White.copy(0f)))),
    ){
        Box(modifier = Modifier
            .size(330.dp)
            .background(brush = Brush.radialGradient(listOf(Color.Blue.copy(0.5f), Color.White.copy(0f))))
            .align(alignment = Alignment.Center)
        ){
            Box(modifier = Modifier
                .size(220.dp)
                .align(alignment = Alignment.Center)
                .clip(CircleShape)
                .background(Color.White)
            )
        }
    }


}