package com.example.main_project.screens.composable_elements

import MainViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.main_project.R
import com.example.main_project.data.Receipt_data
import com.example.main_project.navigation.EDIT_ROUTE
import com.example.main_project.ui.theme.LexendFont
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//@Preview
@Composable

fun receiptCard(ViewModel:MainViewModel, receipt:Receipt_data,navController: NavController) {
    val animationState = remember {
        MutableTransitionState(false).apply {
            targetState= true
        }
    }
    AnimatedVisibility(
        visibleState = animationState,
        enter = slideInHorizontally(animationSpec = tween(1000), initialOffsetX = {-it}),
        exit = slideOutHorizontally(animationSpec = tween(1000),targetOffsetX = {+it}),
    ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(290.dp)
            .clickable {
                ViewModel.EditedReceipt = receipt
                ViewModel.status = Status.WATCHING
                ViewModel.stepsAmount = ViewModel.EditedReceipt.stepsList.size
                ViewModel.ingredientAmount = ViewModel.EditedReceipt.ingredientsList.size
                navController.navigate(EDIT_ROUTE)
            },
        shape = RoundedCornerShape(25.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
        ) {
            AsyncImage(
                modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.7f)
                .padding(start = 8.dp, end = 2.dp, top = 8.dp, bottom = 8.dp)
                .clip(shape = RoundedCornerShape(25.dp)),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                model =  //R.drawable.dish_icon
                if(receipt.imageBmp.size>10){
                    receipt.imageBmp
                }else{
                    R.drawable.dish_icon
                }
            )
            /*Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable {
                        ViewModel.EditedReceipt = receipt
                        ViewModel.status = Status.WATCHING
                        ViewModel.stepsAmount = ViewModel.EditedReceipt.stepsList.size
                        ViewModel.ingredientAmount = ViewModel.EditedReceipt.ingredientsList.size
                        navController.navigate(EDIT_ROUTE)
                    }
                    .fillMaxWidth(0.7f)
                    .padding(start = 8.dp, end = 2.dp, top = 8.dp, bottom = 8.dp)
                    .clip(shape = RoundedCornerShape(25.dp)),
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )*/
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 0.dp, end = 0.dp, top = 8.dp, bottom = 4.dp)
            ) {
                Card(
                    modifier = Modifier
                        .size(80.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
                ) {}
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 0.dp, end = 0.dp, top = 2.dp, bottom = 2.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Card(
                        modifier = Modifier
                            .size(45.dp)
                            .clickable {
                                ViewModel.EditedReceipt = receipt
                                ViewModel.status = Status.EDITING
                                navController.navigate(EDIT_ROUTE)
                            },
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = "edit_icon",
                                tint = Color.White
                            )
                        }

                    }
                    Card(
                        modifier = Modifier
                            .size(45.dp)
                            .clickable {
                                CoroutineScope(Dispatchers.IO).launch() {
                                    animationState.targetState = false
                                    while (animationState.isIdle == false) {
                                    }
                                    //delay(2000)
                                    ViewModel.deleteReceipt(receipt)
                                }
                            },
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "delete_icon",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 8.dp),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier,
                    text = receipt.name,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontFamily = LexendFont,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    letterSpacing = 0.sp
                )
            }

        }
    }
}


}