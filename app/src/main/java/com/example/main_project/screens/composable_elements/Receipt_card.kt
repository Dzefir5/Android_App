package com.example.main_project.screens.composable_elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.main_project.R

@Preview
@Composable

fun receiptCard(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(290.dp),
        shape = RoundedCornerShape(25.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.75f)
        ) {

            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.7f)
                    .padding(start = 8.dp, end = 2.dp, top = 8.dp, bottom = 8.dp)
                    .clip(shape = RoundedCornerShape(25.dp)),
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(start = 0.dp, end = 0.dp, top = 8.dp, bottom = 4.dp)
            ) {
                Card(modifier = Modifier
                    .size(80.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
                ) {}
                Row(modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 0.dp, end = 0.dp, top = 2.dp, bottom = 2.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ){
                    Card(modifier = Modifier
                        .size(45.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {}
                    Card(modifier = Modifier
                        .size(45.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {}
                }
            }
        }
        Card(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 8.dp),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ){

        }
    }


}