package com.example.main_project.screens

import MainViewModel
import Status
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.main_project.R
import com.example.main_project.data.Receipt_data
import com.example.main_project.data.Receipt_ingredient
import com.example.main_project.data.Receipt_step
import com.example.main_project.loadFromInternalStorage
import com.example.main_project.navigation.EDIT_ROUTE
import com.example.main_project.navigation.HOME_ROUTE
import com.example.main_project.saveImageToInternalStorage
import com.example.main_project.screens.composable_elements.BackGround
import com.example.main_project.screens.composable_elements.myshadow
import com.example.main_project.ui.theme.LexendFont
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.UUID

fun fromBmp(bitmap: Bitmap):ByteArray{
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    return outputStream.toByteArray()
}
fun toBmp(byteArray: ByteArray):Bitmap{
    return BitmapFactory.decodeByteArray(byteArray,0, byteArray.size)
}

fun compressBmp(bmp:Bitmap):ByteArray{
    val outputStream = ByteArrayOutputStream()
    bmp.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
    return outputStream.toByteArray()
}
fun scaleBitmap(bmp:Bitmap,maxHeight:Int ,maxWidth :Int ):Bitmap{
    var inputHeight = bmp.height
    var inputWidth=bmp.width
    var scale : Float = maxHeight.toFloat()/inputHeight.toFloat()
    return Bitmap.createScaledBitmap(bmp,(inputWidth*scale).toInt(),(inputHeight*scale).toInt(),true)
}

//@Preview
@Composable
@ExperimentalMaterial3Api
fun CreationScreen(navController: NavController,ViewModel:MainViewModel,context:Context,remote:Boolean){

    var status by remember {
        mutableStateOf(ViewModel.status)
    }
        ViewModel.ingredientAmount=ViewModel.EditedReceipt.ingredientsList.size
        ViewModel.stepsAmount=ViewModel.EditedReceipt.stepsList.size
        var preload :ByteArray = byteArrayOf(0)
        if( ViewModel.EditedReceipt.imageBmp!="null" && !remote){
            preload=loadFromInternalStorage(ViewModel.EditedReceipt.imageBmp,context)
        }
        var MainImageUri:Any? by  remember {
            if(ViewModel.EditedReceipt.imageBmp!="null"){
                mutableStateOf(preload)
            }else{
                mutableStateOf(R.drawable.image_placeholder_01)
            }
        }
        val path:String= UUID.randomUUID().toString()
        val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                MainImageUri=uri
                val byteArray=context.contentResolver.openInputStream(MainImageUri.toString().toUri())!!.use { it.readBytes() }
                var bmp = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
                bmp=scaleBitmap(bmp,720,960)
                saveImageToInternalStorage(path,bmp,context)
                ViewModel.EditedReceipt.imageBmp=path
            } else {
                ////
            }
        }

    var Url = remember{mutableStateOf("")}
    if(remote){
        val memoryRef = ViewModel.remoteStorage.reference
            .child("images")
            .child(ViewModel.EditedReceipt.imageBmp+".jpg")
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
    }


                    //BackGround
    BackGround()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.96f)
                .align(Alignment.Center)
                .background(Color.White.copy(0.5f))
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ){

        Card(                           //Up Bar
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
                            //Main column
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        userScrollEnabled = true,
        verticalArrangement = Arrangement.spacedBy(6.dp)
        //contentPadding = PaddingValues(10.dp)
    ) {
                //Name line
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
                ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(55.dp),
                    shape= RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Row(Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        BasicTextField(modifier = Modifier,
                        value=ViewModel.EditedReceipt.name,
                        onValueChange ={
                            ViewModel.EditedReceipt=ViewModel.EditedReceipt.copy(name = it)
                                       },
                        textStyle = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 30.sp,
                            fontFamily = LexendFont,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            letterSpacing = 1.sp
                        ),
                        enabled = status!=Status.WATCHING,
                        singleLine = true,

                    ){ innerTextField ->
                        TextFieldDefaults.TextFieldDecorationBox(
                            value = ViewModel.EditedReceipt.name,
                            innerTextField = innerTextField,
                            enabled = status!=Status.WATCHING,
                            singleLine = true,
                            placeholder = {Text(modifier = Modifier.fillMaxWidth(), text ="NAME" ,textAlign = TextAlign.Center,
                                fontSize = 30.sp,
                                fontFamily = LexendFont,
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                letterSpacing = 1.sp)},
                            visualTransformation = VisualTransformation.None,
                            interactionSource = MutableInteractionSource(),
                            contentPadding = PaddingValues(horizontal = 5.dp, vertical = 0.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent)
                        )
                    }

                    }

                }
            }
        }
                //Photo place
        item {
            val cardmod = Modifier
                .fillMaxWidth(0.8f)
                .height(210.dp)
                .myshadow(
                    Color.Black.copy(0.5f),
                    offsetX = 2.dp,
                    offsetY = 2.dp,
                    roundY = 50f,
                    roundX = 50f,
                    blurRadius = 3.dp
                )


            //Image
            Card(
                modifier = if(remote)cardmod else cardmod.clickable {
                    pickMedia.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                shape= RoundedCornerShape(20.dp),
                elevation =CardDefaults.elevatedCardElevation(3.dp) ,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                if(!remote){
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(MainImageUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Main_Image",
                        contentScale = ContentScale.Crop,
                    )
                }else{
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = Url.value,
                        contentDescription = "Main_Image",
                        contentScale = ContentScale.Crop,
                    )
                }

            }
        }
                //Description
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally)
                    .myshadow(
                        Color.Black.copy(0.5f),
                        offsetX = 2.dp,
                        offsetY = 2.dp,
                        roundY = 50f,
                        roundX = 50f,
                        blurRadius = 3.dp
                    ),
                shape= RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text(modifier = Modifier
                    .fillMaxWidth(),
                    text="DESCRIPTION",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontFamily = LexendFont,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    letterSpacing = 1.sp
                )
                Divider(modifier = Modifier.fillMaxWidth(),color = MaterialTheme.colorScheme.onSecondaryContainer.copy(0.2f), thickness = 1.dp)
                BasicTextField(modifier = Modifier.padding(10.dp),
                    value=ViewModel.EditedReceipt.description,
                    onValueChange ={ViewModel.EditedReceipt=ViewModel.EditedReceipt.copy(description = it)},
                    textStyle = TextStyle(
                        textAlign = TextAlign.Justify,
                        fontSize = 20.sp,
                        fontFamily = LexendFont,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        letterSpacing = 0.sp
                    ),
                    enabled = status!=Status.WATCHING,
                    singleLine =false,

                    ){ innerTextField ->
                    TextFieldDefaults.TextFieldDecorationBox(
                        value = ViewModel.EditedReceipt.description,
                        innerTextField = innerTextField,
                        enabled = status!=Status.WATCHING,
                        singleLine = false,
                        placeholder = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text =if(status!=Status.WATCHING){ "Print your text "}else{ ""} ,
                                textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontFamily = LexendFont,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            letterSpacing = 0.sp)},
                        visualTransformation = VisualTransformation.None,
                        interactionSource = MutableInteractionSource(),
                        contentPadding = PaddingValues(0.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent)
                    )
                }

                //       Text()
            }
        }
                //Ingredients
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally)
                    .myshadow(
                        Color.Black.copy(0.5f),
                        offsetX = 2.dp,
                        offsetY = 2.dp,
                        roundY = 50f,
                        roundX = 50f,
                        blurRadius = 3.dp
                    ),
                shape= RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "INGREDIENTS",
                    fontSize = 30.sp,
                    fontFamily = LexendFont,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    letterSpacing = 1.sp
                )


                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    //  modifier = Modifier.height(600.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    if (ViewModel.ingredientAmount > 0) {
                    for(num in 0..(ViewModel.ingredientAmount-1)) {
                        key(ViewModel.EditedReceipt.ingredientsList[num],) {
                                IngredientCard(num, ViewModel)
                        }
                    }
                    }
                }
                                //ADD BUTTON
                if(status!=Status.WATCHING) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .width(100.dp)
                                .height(50.dp)
                                .clickable {
                                    ViewModel.EditedReceipt.ingredientsList.add(Receipt_ingredient())
                                    ViewModel.ingredientAmount++
                                },
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Icon(
                                modifier = Modifier.size(200.dp),
                                imageVector = Icons.Rounded.Add,
                                contentDescription = "add_icon",
                                tint = Color.White
                            )
                        }
                    }
                }
               }
                }

                //Steps
        item {
            Card(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally)
                    .myshadow(
                        Color.Black.copy(0.5f),
                        offsetX = 2.dp,
                        offsetY = 2.dp,
                        roundY = 50f,
                        roundX = 50f,
                        blurRadius = 3.dp
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "STEPS",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontFamily = LexendFont,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    letterSpacing = 1.sp
                )
                //Steps Column
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    //  modifier = Modifier.height(600.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    if(ViewModel.stepsAmount>0){
                        for(num in 0..(ViewModel.stepsAmount-1))
                        {
                            key(ViewModel.EditedReceipt.stepsList[num]) {
                                    StepCard(num, ViewModel,context, remote)
                            }
                        }
                    }

                }
                                //Plus Button
                //Spacer(modifier = Modifier.height(20.dp))
                if(status!=Status.WATCHING){
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Card(
                            modifier = Modifier
                                .width(100.dp)
                                .height(50.dp)
                                .clickable {
                                    ViewModel.EditedReceipt.stepsList.add(Receipt_step())
                                    // ViewModel.EditedReceipt.ingredientsList.removeLast()
                                    ViewModel.stepsAmount++
                                },
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Icon(
                                modifier = Modifier.size(200.dp),
                                imageVector = Icons.Rounded.Add,
                                contentDescription = "add_icon",
                                tint = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
    }


                            //Above layer /Floating layer
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
                    .size(110.dp)
                    .offset(x = -10.dp, y = 10.dp)
                    .myshadow(
                        MaterialTheme.colorScheme.primary,
                        blurRadius = 25.dp,
                        roundY = 200f,
                        roundX = 200f
                    )
                    .border(width = 2.dp, Color.White.copy(0.2f), CircleShape),
                shape = CircleShape,
                onClick = {
                            navController.popBackStack()
                            if(ViewModel.status!=Status.WATCHING) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    delay(1000)
                                    ViewModel.EditedReceipt = Receipt_data()
                                    ViewModel.stepsAmount = ViewModel.EditedReceipt.stepsList.size
                                    ViewModel.ingredientAmount = ViewModel.EditedReceipt.ingredientsList.size
                                }
                            }

                          },
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 5.dp,
                    focusedElevation = 5.dp,
                    pressedElevation = 2.dp
                )
            ) {
                Icon(
                    modifier = Modifier
                        .size(75.dp)
                        .rotate(180f),
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = "add_icon",
                    tint = Color.White
                )
            }
            if(!remote){
                FloatingActionButton(
                    modifier = Modifier
                        .size(110.dp)
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

                        if(ViewModel.status!=Status.WATCHING){
                            CoroutineScope(Dispatchers.IO).launch {
                                if(ViewModel.status==Status.EDITING)ViewModel.updateReceipt(ViewModel.EditedReceipt)
                                if(ViewModel.status==Status.CREATING){
                                    ViewModel.addReceipt(ViewModel.EditedReceipt)
                                    //ViewModel.remoteRepository.insertData(ViewModel.EditedReceipt,context)
                                }
                                delay(1000)
                                ViewModel.EditedReceipt = Receipt_data()
                            }
                            navController.navigate(HOME_ROUTE) {
                                popUpTo(HOME_ROUTE) {
                                    inclusive = true
                                }
                            }
                        }else{
                            ViewModel.status = Status.EDITING
                            navController.navigate(EDIT_ROUTE)
                        }



                    },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 5.dp,
                        focusedElevation = 5.dp,
                        pressedElevation = 2.dp
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(if(ViewModel.status!=Status.WATCHING) {75.dp}else{60.dp}),
                        imageVector = if(ViewModel.status!=Status.WATCHING) {Icons.Rounded.Done}else{Icons.Rounded.Edit},
                        contentDescription = "save_icon",
                        tint = Color.White
                    )
                }
            }

        }

    }
}

@ExperimentalMaterial3Api
@Composable
 fun StepCard(number :Int ,ViewModel: MainViewModel,context:Context,remote: Boolean) {
    var step_description by remember {
        mutableStateOf("")
    }
    step_description=ViewModel.EditedReceipt.stepsList[number].description
    val animationState = remember {
        MutableTransitionState(false).apply {
            targetState= true
        }
    }

    var preload :ByteArray
    if( ViewModel.EditedReceipt.stepsList[number].image!="null"&& !remote){
        preload=loadFromInternalStorage(ViewModel.EditedReceipt.stepsList[number].image,context)
    }else{
        preload= byteArrayOf()
    }

    var ImageUri:Any? by  remember {
        if(ViewModel.EditedReceipt.stepsList[number].image!="null"){
            mutableStateOf(preload)
        }else{
            mutableStateOf(R.drawable.image_placeholder_01)
        }

    }
    val path:String= UUID.randomUUID().toString()
    val pickMediaStep = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            ImageUri=uri
            val byteArray=context.contentResolver.openInputStream(ImageUri.toString().toUri())!!.use { it.readBytes() }
            var bmp = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
            bmp= scaleBitmap(bmp,480,640)
            saveImageToInternalStorage(path,bmp,context)
            ViewModel.EditedReceipt.stepsList[number].image= path
            // Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            // Log.d("PhotoPicker", "No media selected")
        }
    }

    var Url = remember{mutableStateOf("")}
    if(remote){
        val memoryRef = ViewModel.remoteStorage.reference
            .child("images")
            .child(ViewModel.EditedReceipt.stepsList[number].image+".jpg")
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
    }


    AnimatedVisibility(
        visibleState = animationState,
        enter = expandVertically(animationSpec = tween(1000), expandFrom = Alignment.Top, initialHeight = {-it}),
        exit = shrinkVertically(animationSpec = tween(1000), shrinkTowards = Alignment.Top, targetHeight = {-it}),
    ) {
        Card(
            modifier = Modifier
                .width(310.dp)
                .border(1.dp, Color.Black.copy(0.5f), RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.elevatedCardElevation(10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    // Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .width(70.dp)
                                .fillMaxHeight()
                                .padding(2.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Card(
                                modifier = Modifier
                                    .size(60.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .offset(y = 5.dp),
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        modifier = Modifier.offset(y = -2.dp),
                                        textAlign = TextAlign.Center,
                                        text = (number + 1).toString(),
                                        fontSize = 40.sp,
                                        fontFamily = LexendFont,
                                        fontWeight = FontWeight.Light,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                                        letterSpacing = 1.sp
                                    )
                                }
                            }
                            if (ViewModel.status != Status.WATCHING) {
                                Card(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .align(Alignment.CenterHorizontally)
                                        .offset(y = 5.dp)
                                        .clickable {
                                            CoroutineScope(Dispatchers.IO).launch {
                                                animationState.targetState = false
                                                while (animationState.isIdle == false) {
                                                }
                                                ViewModel.EditedReceipt.stepsList.removeAt(number)
                                                ViewModel.stepsAmount = ViewModel.EditedReceipt.stepsList.size
                                            }

                                        },
                                    shape = CircleShape,
                                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
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
                        val cardmod = Modifier
                            .width(250.dp)
                            .height(200.dp)
                            .padding(10.dp)
                        Card(
                            modifier = if(remote)cardmod else cardmod
                                .clickable {
                                    pickMediaStep.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                },
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.elevatedCardElevation(3.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            if(!remote){
                                AsyncImage(
                                    modifier = Modifier.fillMaxSize(),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(ImageUri)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Main_Image",
                                    contentScale = ContentScale.Crop,
                                )
                            }else{
                                AsyncImage(
                                    modifier = Modifier.fillMaxSize(),
                                    model = Url.value,
                                    contentDescription = "Main_Image",
                                    contentScale = ContentScale.Crop,
                                )
                            }
                        }
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            // .height(219.dp)
                            //  .offset(y=9.dp)
                            .border(
                                1.dp,
                                Color.Black.copy(0.5f),
                                RoundedCornerShape(20.dp)
                            ),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.elevatedCardElevation(3.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "Description",
                            fontSize = 30.sp,
                            fontFamily = LexendFont,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            letterSpacing = 1.sp
                        )
                        BasicTextField(
                            modifier = Modifier,
                            value = step_description,
                            onValueChange = {
                                step_description = it
                                ViewModel.EditedReceipt.stepsList[number].description =
                                    step_description
                                // Log.d("MyLog","$number"+"_)(_"+"${ViewModel.EditedReceipt.stepsList[number].description}")
                            },
                            textStyle = TextStyle(
                                textAlign = TextAlign.Justify,
                                fontSize = 20.sp,
                                fontFamily = LexendFont,
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                letterSpacing = 0.sp
                            ),
                            enabled = ViewModel.status != Status.WATCHING,
                            singleLine = false,


                            ) { innerTextField ->
                            TextFieldDefaults.TextFieldDecorationBox(
                                value = step_description,
                                innerTextField = innerTextField,
                                enabled = ViewModel.status != Status.WATCHING,
                                singleLine = false,
                                placeholder = {
                                    Text(
                                        modifier = Modifier.fillMaxSize(),
                                        text = "Print your text ",
                                        textAlign = TextAlign.Center,
                                        fontSize = 20.sp,
                                        fontFamily = LexendFont,
                                        fontWeight = FontWeight.Light,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                                        letterSpacing = 0.sp
                                    )
                                },
                                visualTransformation = VisualTransformation.None,
                                interactionSource = MutableInteractionSource(),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun IngredientCard(number:Int ,ViewModel: MainViewModel){

    var ingredient_name by remember {
        mutableStateOf("")
    }
    var ingredient_amount by remember {
        mutableStateOf("")
    }
    ingredient_name = ViewModel.EditedReceipt.ingredientsList[number].name
    ingredient_amount = ViewModel.EditedReceipt.ingredientsList[number].amount

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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
            // horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(45.dp)
                    .align(Alignment.CenterVertically),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Row(
                    Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = ingredient_name,
                        onValueChange = {
                            ingredient_name = it
                            ViewModel.EditedReceipt.ingredientsList[number].name = ingredient_name
                        },
                        textStyle = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontFamily = LexendFont,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            letterSpacing = 0.sp
                        ),
                        enabled = ViewModel.status != Status.WATCHING,
                        singleLine = true,

                        ) { innerTextField ->
                        TextFieldDefaults.TextFieldDecorationBox(
                            value = ingredient_name,
                            innerTextField = innerTextField,
                            enabled = ViewModel.status != Status.WATCHING,
                            singleLine = true,
                            placeholder = {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.CenterVertically),
                                    text = "Ingredient name ",
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    fontFamily = LexendFont,
                                    fontWeight = FontWeight.Light,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    letterSpacing = 0.sp
                                )
                            },
                            visualTransformation = VisualTransformation.None,
                            interactionSource = MutableInteractionSource(),
                            contentPadding = PaddingValues(horizontal = 3.dp, vertical = 0.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxHeight()
                    .offset(y = -2.dp),
                text = ":",
                fontSize = 40.sp,
                textAlign = TextAlign.Center
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth(
                        if (ViewModel.status != Status.WATCHING) {
                            0.7f
                        } else {
                            1f
                        }
                    )
                    .height(45.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Row(
                    Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        modifier = Modifier,
                        value = ingredient_amount,
                        onValueChange = {
                            ingredient_amount = it
                            ViewModel.EditedReceipt.ingredientsList[number].amount =
                                ingredient_amount
                        },
                        textStyle = TextStyle(
                            textAlign = TextAlign.Justify,
                            fontSize = 20.sp,
                            fontFamily = LexendFont,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            letterSpacing = 0.sp
                        ),
                        singleLine = true,
                        enabled = ViewModel.status != Status.WATCHING,

                        ) { innerTextField ->
                        TextFieldDefaults.TextFieldDecorationBox(
                            value = ingredient_amount,
                            innerTextField = innerTextField,
                            enabled = ViewModel.status != Status.WATCHING,
                            singleLine = true,
                            placeholder = {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.CenterVertically),
                                    text = "Amount ",
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    fontFamily = LexendFont,
                                    fontWeight = FontWeight.Light,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    letterSpacing = 0.sp
                                )
                            },
                            visualTransformation = VisualTransformation.None,
                            interactionSource = MutableInteractionSource(),
                            contentPadding = PaddingValues(horizontal = 2.dp, vertical = 0.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
            if (ViewModel.status != Status.WATCHING) {
                Card(modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(45.dp)
                    .clickable {
                        CoroutineScope(Dispatchers.IO).launch {
                            animationState.targetState = false
                            while (animationState.isIdle == false) {
                            }
                            ViewModel.EditedReceipt.ingredientsList.removeAt(number)
                            ViewModel.ingredientAmount =
                                ViewModel.EditedReceipt.ingredientsList.size
                        }

                    }
                    .align(Alignment.CenterVertically),
                    shape = RoundedCornerShape(20.dp),
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
}