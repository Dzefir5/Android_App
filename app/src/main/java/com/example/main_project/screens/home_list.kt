package com.example.main_project.screens

import MainViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.main_project.data.Receipt_data
import com.example.main_project.data.Receipt_step
import com.example.main_project.navigation.EDIT_ROUTE
import com.example.main_project.screens.composable_elements.BackGround
import com.example.main_project.screens.composable_elements.myshadow
import com.example.main_project.screens.composable_elements.receiptCard

//@Preview
@Composable


fun HomeScreen(navController: NavHostController,ViewModel:MainViewModel){
 /*  viewModel.addNote(Receipt_data(
       receiptId = 1,
       name = "FOOD",
       description = "ITS FOOD",
       stepsList= listOf( Receipt_step(1,"STEP1"),Receipt_step(2,"STEP2"))
   ))*/
    val state = remember{
        MutableTransitionState(false).apply{
            targetState = true
        }
    }

    BackGround()
    Column(

    ){
        Card(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally),
            shape = RoundedCornerShape(0.dp,0.dp,20.dp,20.dp)
        ){
            Row(
                modifier = Modifier.fillMaxSize() ,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Card(
                    modifier = Modifier
                        .height(40.dp)
                        .width(310.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                   // TextField(value = , onValueChange = )
                    Text("")
                }
                Spacer(modifier = Modifier.width(5.dp))
                Button(
                    modifier = Modifier
                        .size(40.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ){

                }
            }

        }
        val list=ViewModel.getAllData.collectAsState(initial = emptyList<Receipt_data>())
        LazyColumn(){
            itemsIndexed(list.value, key = {index: Int, item: Receipt_data -> item.receiptId }){_,receipt->
                receiptCard(receipt=receipt,ViewModel=ViewModel, navController = navController)
            }
            item(){
                Spacer(modifier = Modifier.fillMaxWidth().height(60.dp))
            }
        }
        var a = Receipt_data()
       /* Column {
            Spacer(modifier = Modifier.height(3.dp))
            receiptCard(a)
            Spacer(modifier = Modifier.height(3.dp))
            receiptCard(a)
            Spacer(modifier = Modifier.height(3.dp))
            receiptCard(a)
        }*/

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .background(Color.White.copy(0.0f))
        ){

        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.7f to Color.White.copy(0.0f),
                        1.0f to MaterialTheme.colorScheme.primaryContainer
                    )
                )
            ),
            verticalArrangement = Arrangement.Bottom
    ) {
            Card(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .myshadow(Color.Black.copy(0.2f), offsetY = -4.dp, offsetX = -5.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                shape = RoundedCornerShape(0.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ){

            }
    }
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
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
            onClick = {
                navController.navigate(EDIT_ROUTE)
                ViewModel.EditedReceipt= Receipt_data()
                ViewModel.stepsAmount=0
                ViewModel.ingredientAmount=0
                ViewModel.status=Status.CREATING

                      },
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