package com.example.main_project.screens

import MainViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.main_project.R
import com.example.main_project.navigation.HOME_ROUTE
import com.example.main_project.navigation.REMOTE_EDIT_ROUTE
import com.example.main_project.navigation.REMOTE_ROUTE
import com.example.main_project.screens.composable_elements.myshadow
import com.example.main_project.ui.theme.LexendFont

@Composable
fun StartScreen(navController: NavHostController,viewModel:MainViewModel){
    val isDarkThemed = false
    val start_state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    val state_01 = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colorStops = arrayOf(
                    0.0f to MaterialTheme.colorScheme.primary,
                    0.8f to Color.White
                )
            )
        )
    ){}
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        AnimatedVisibility(
            visibleState = start_state,
            enter = slideInVertically(
                animationSpec = tween(durationMillis = 600, delayMillis = 100,CubicBezierEasing(.4f,-0.05f,.65f,1.0f)), initialOffsetY = {it}
            )
        ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .shadow(8.dp,RoundedCornerShape(40.dp, 40.dp, 0.dp, 0.dp)),
                    shape = RoundedCornerShape(40.dp, 40.dp, 0.dp, 0.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    AnimatedVisibility(modifier = Modifier
                        .height(64.dp)
                        .width(263.dp)
                        .align(Alignment.CenterHorizontally)
                        .offset(y = 200.dp),
                        visibleState = state_01,
                        enter = scaleIn(animationSpec = tween(durationMillis = 300, delayMillis = 450))
                    ){
                        Card(
                            modifier = Modifier
                                .height(64.dp)
                                .width(263.dp)
                                .myshadow(Color.Black.copy(0.7f),offsetX =3.dp , offsetY =3.dp, blurRadius = 3.dp, roundX = 60f, roundY = 60f )
                                .clickable{
                                    navController.navigate(HOME_ROUTE)
                                },
                            shape = RoundedCornerShape(21.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Row(modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ){
                                Text(modifier = Modifier
                                    .fillMaxHeight(),
                                    text="OPEN",
                                    fontSize = 40.sp,
                                    fontFamily = LexendFont,
                                    fontWeight = FontWeight.ExtraLight,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    letterSpacing = 10.sp
                                    //fontStyle = MaterialTheme.typography.bodyMedium
                                )
                            }

                        }
                    }

                    AnimatedVisibility(modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .offset(y = 300.dp),
                        visibleState = state_01,
                        enter = scaleIn(animationSpec = tween(durationMillis = 300, delayMillis = 450))
                    ){
                        Card(
                            modifier = Modifier
                                .height(23.dp)
                                .width(304.dp)
                                .myshadow(Color.Black.copy(0.7f),
                                    offsetX =3.dp , offsetY =2.dp, blurRadius = 1.dp, roundX = 40f, roundY = 40f )
                                .align(Alignment.CenterHorizontally),
                            shape = RoundedCornerShape(11.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Row(modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ){
                                Text(modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(2.dp),
                                    text="000.PMI-ENTERPRISE",
                                    fontSize =15.sp,
                                    fontFamily = LexendFont,
                                    fontWeight = FontWeight.ExtraLight,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    letterSpacing = 5.sp
                                    //fontStyle = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }

                }
            

        }
    }


    AnimatedVisibility(
        visibleState = start_state,
        enter = slideInVertically(animationSpec = tween(800,), initialOffsetY = {it})
        +
                scaleIn(animationSpec = tween(durationMillis =600,delayMillis =200, easing = CubicBezierEasing(.31f,-0.47f,.75f,1.57f) ))
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            //.background(brush = Brush.radialGradient(listOf(Color.Blue, Color.White.copy(0f)))),
        ){
            Box(modifier = Modifier
                .size(330.dp)
                .background(
                    brush = Brush.radialGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary.copy(0.5f),
                            Color.White.copy(0f)
                        )
                    )
                )
                .align(alignment = Alignment.Center)
            ){
                Box(modifier = Modifier
                    .size(220.dp)
                    .align(alignment = Alignment.Center)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                ){
                    Icon(
                        modifier = Modifier
                            .size(300.dp)
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.dishicon_01),
                        contentDescription = "  ",
                        tint = MaterialTheme.colorScheme.primary
                    )

                }
            }
        }
    }



}