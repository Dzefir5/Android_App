package com.example.main_project.screens.composable_elements

import MainViewModel
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.main_project.R
import com.example.main_project.data.Receipt_data
import com.example.main_project.loadFromInternalStorage
import com.example.main_project.navigation.EDIT_ROUTE
import com.example.main_project.navigation.REMOTE_EDIT_ROUTE
import com.example.main_project.ui.theme.LexendFont
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File

//@Preview
@Composable

fun receiptCard(ViewModel:MainViewModel, receipt:Receipt_data,navController: NavController,context: Context,remote:Boolean) {
    var painter :Bitmap
    var Url = remember{mutableStateOf("")}
    val memoryRef = ViewModel.remoteStorage.reference
        .child("images")
        .child(receipt.imageBmp+".jpg")
    if( receipt.imageBmp!="null") {
        if (remote) {
            painter =BitmapFactory.decodeResource(context.getResources(),R.drawable.image_placeholder_01)
            memoryRef.downloadUrl.addOnSuccessListener {
                Url.value=it.toString()
                Log.d("Mylog","${Url}")
            }.addOnFailureListener(){
                 ViewModel.remoteStorage.reference
                .child("01.png")
                .downloadUrl.addOnSuccessListener(){
                    Url.value=it.toString()
                }
            }

        } else {

            var bytes=loadFromInternalStorage(receipt.imageBmp, context)
            painter = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)


        }
    } else{
            ViewModel.remoteStorage.reference
                .child("01.png")
                .downloadUrl.addOnSuccessListener(){
                    Url.value=it.toString()
                }
        painter =BitmapFactory.decodeResource(context.getResources(),R.drawable.image_placeholder_01)
    }
    // анимацию процесса загрузки
    val animationState = remember {
        MutableTransitionState(true).apply {
            targetState= true
        }
    }






    AnimatedVisibility(
        visibleState = animationState,
        enter = expandVertically(animationSpec = tween(1000), expandFrom = Alignment.Top, initialHeight = {-it}),
        exit = shrinkVertically(animationSpec = tween(1000), shrinkTowards = Alignment.Top, targetHeight = {-it}),

        ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(290.dp)
            /*.myshadow(
                Color.Black.copy(0.7f),
                offsetX =3.dp , offsetY =2.dp,
                blurRadius = 1.dp, roundX = 40f, roundY = 40f
            )*/
            .shadow(5.dp, spotColor = Color.Black.copy(0.5f) , shape = RoundedCornerShape(25.dp))
            .clickable {
                ViewModel.EditedReceipt = receipt
                ViewModel.status = Status.WATCHING
                ViewModel.stepsAmount = ViewModel.EditedReceipt.stepsList.size
                ViewModel.ingredientAmount = ViewModel.EditedReceipt.ingredientsList.size
                if (remote) {
                    navController.navigate(REMOTE_EDIT_ROUTE)
                } else {
                    navController.navigate(EDIT_ROUTE)
                }

            },
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.7f)
                    .padding(start = 8.dp, end = 2.dp, top = 8.dp, bottom = 8.dp) ,
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
            ){
                if(remote){
                    AsyncImage(modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(25.dp)),
                        contentScale = ContentScale.Crop,
                        model =Url.value,
                        contentDescription = ""
                    )
                }else{
                    Image(modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(25.dp)),
                        contentScale = ContentScale.Crop,
                        bitmap = painter.asImageBitmap(),
                        contentDescription = ""
                    )
                }

            }

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
                if(!remote){
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
                                        ViewModel.deleteReceipt(receipt, context)
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
                }else{
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 0.dp, end = 0.dp, top = 2.dp, bottom = 2.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Card(
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    ViewModel.saveToLocalDatabase(receipt)
                                },
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    modifier = Modifier.size(40.dp),
                                    imageVector = Icons.Rounded.Add,
                                    contentDescription = "add_remote_icon",
                                    tint = Color.White
                                )
                            }
                        }
                        Card(
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    CoroutineScope(Dispatchers.IO).launch() {
                                        animationState.targetState = false
                                        while (animationState.isIdle == false) {
                                        }
                                        ViewModel.remoteRepository.deleteData(receipt)
                                    }
                                },
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    modifier = Modifier.size(40.dp),
                                    imageVector = Icons.Rounded.Delete,
                                    contentDescription = "delete_remote_icon",
                                    tint = Color.White
                                )
                            }
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