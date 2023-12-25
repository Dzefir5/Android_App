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
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import com.example.main_project.navigation.REMOTE_ROUTE
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


//@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ChooseScreen(ViewModel:MainViewModel,navController: NavController) {
    val start_state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    AnimatedVisibility(
        visibleState = start_state,
        enter = slideInHorizontally(animationSpec = tween(100,), initialOffsetX = {it})
                +
                scaleIn(animationSpec = tween(durationMillis =200,delayMillis =200, easing = CubicBezierEasing(.31f,-0.47f,.75f,1.57f) ))
    ) {
        BackGround()
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ){
        Card(modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp, RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp))
            .fillMaxHeight(0.1f)
            .background(color = Color.White),
            shape = RoundedCornerShape(0.dp,0.dp,25.dp,25.dp),
            colors = CardDefaults.cardColors(Color.White)
        )
        {
            Text(modifier = Modifier
                .fillMaxHeight()
                .padding(100.dp, 3.dp),
                text="Choose import",
                fontSize = 35.sp,
                fontFamily = LexendFont,
                fontWeight = FontWeight.ExtraLight,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                letterSpacing = 6.sp,
                textAlign = TextAlign.Center
                //fontStyle = MaterialTheme.typography.bodyMedium
            )
        }
                Card(
                    modifier = Modifier.size(300.dp)
                        .clickable {
                            navController.navigate(EDIT_ROUTE)
                            ViewModel.EditedReceipt= Receipt_data()
                            ViewModel.stepsAmount=0
                            ViewModel.ingredientAmount=0
                            ViewModel.status=Status.CREATING
                        }
                        .align(Alignment.CenterHorizontally),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),

                )
                {
                    Icon(
                        modifier = Modifier.fillMaxSize().scale(1.2f).offset(x = 5.dp),
                        painter = painterResource(id = R.drawable.icon_4),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                Card(
                    modifier = Modifier.size(300.dp)
                        .clickable{
                            navController.navigate(REMOTE_ROUTE)
                        }
                        .align(Alignment.CenterHorizontally),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
                )
                {
                    Icon(
                        modifier = Modifier.fillMaxSize().scale(1.2f).offset(x = 5.dp),
                        painter = painterResource(id = R.drawable.icon_5),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))
        }
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd)
    {
        FloatingActionButton(
            modifier = androidx.compose.ui.Modifier
                .size(120.dp)
                .offset(x = 10.dp, y = 10.dp)
                .myshadow(
                    MaterialTheme.colorScheme.primary,
                    blurRadius = 25.dp,
                    roundY = 200f,
                    roundX = 200f
                )
                .border(
                    width = 2.dp,
                    Color.White.copy(0.2f),
                    CircleShape
                ),
            shape = CircleShape,
            onClick = {
                // some commands
            },
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 5.dp,
                focusedElevation = 5.dp,
                pressedElevation = 2.dp
            )
        ) {
            Icon(
                modifier =Modifier.size(105.dp),
                imageVector = Icons.Rounded.Refresh,
                contentDescription = "add_icon",
                tint = Color.White
            )
        }
    }
}