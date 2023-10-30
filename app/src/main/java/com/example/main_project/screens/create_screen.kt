package com.example.main_project.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.main_project.screens.composable_elements.BackGround
import com.example.main_project.screens.composable_elements.myshadow


@Preview
@Composable
fun CreationScreen(){
    BackGround()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.95f)
                .align(Alignment.Center)
                .background(Color.White.copy(0.5f))
        )
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Card(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)
                .shadow(
                    10.dp,
                    RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp),
                    ambientColor = Color.Black
                ),
            shape = RoundedCornerShape(0.dp,0.dp,20.dp,20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ){

        }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
                ) {
                Card(
                    modifier = Modifier.fillMaxWidth(0.7f)
                        .height(55.dp),
                    shape= RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Black)
                ) {
                    Text("01")
                }
                Card(
                    modifier = Modifier.fillMaxWidth(0.7f)
                        .height(55.dp),
                    shape= RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Black)
                ) {

                }
            }
        }
        items(2) {
            Card(
                modifier = Modifier.fillMaxWidth(0.85f)
                    .height(210.dp),
                shape= RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Blue)
            ) {
                //         Text()
            }
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth(0.85f)
                    .align(Alignment.CenterHorizontally),
                shape= RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Blue)
            ) {
                Text("DESCRIPTION")
                //       Text()
            }
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth(0.85f)
                    .align(Alignment.CenterHorizontally),
                shape= RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Blue)
            ) {

                Text("INGREDIENTS")
                /* LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ){

                }*/
            }

        }
    }



    }
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            FloatingActionButton(
                modifier = Modifier
                    .size(120.dp)
                    .offset(x = -10.dp, y = 10.dp)
                    .myshadow(
                        MaterialTheme.colorScheme.primary,
                        blurRadius = 25.dp,
                        roundY = 200f,
                        roundX = 200f
                    )
                    .border(width = 2.dp, Color.White.copy(0.2f), CircleShape),
                shape = CircleShape,
                onClick = { /*TODO*/ },
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 5.dp,
                    focusedElevation = 5.dp,
                    pressedElevation = 2.dp
                )
            ) {
                Icon(
                    modifier = Modifier.size(105.dp),
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "add_icon",
                    tint = Color.White
                )
            }

            FloatingActionButton(
                modifier = Modifier
                    .size(120.dp)
                    .offset(x = 10.dp, y = 10.dp)
                    .myshadow(
                        MaterialTheme.colorScheme.primary,
                        blurRadius = 25.dp,
                        roundY = 200f,
                        roundX = 200f
                    )
                    .border(width = 2.dp, Color.White.copy(0.2f), CircleShape),
                shape = CircleShape,
                onClick = { /*TODO*/ },
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 5.dp,
                    focusedElevation = 5.dp,
                    pressedElevation = 2.dp
                )
            ) {
                Icon(
                    modifier = Modifier.size(105.dp),
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "add_icon",
                    tint = Color.White
                )
            }
        }

    }


}