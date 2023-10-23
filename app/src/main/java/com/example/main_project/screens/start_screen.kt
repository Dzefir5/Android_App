package com.example.main_project.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.with
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
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
        .background(brush = Brush.verticalGradient(colorStops = arrayOf(0.0f to Color.Green,0.7f to Color.White)))
    ){}
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        AnimatedVisibility(
            visibleState = start_state,
            enter = slideInVertically(
                animationSpec = tween(durationMillis = 600, delayMillis = 100), initialOffsetY = {it}
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(40.dp, 40.dp, 0.dp, 0.dp),
                colors = CardDefaults.cardColors(Color.White)
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
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(21.dp),
                        colors = CardDefaults.cardColors(Color.Green)
                    ) {}
                }

                AnimatedVisibility(modifier = Modifier
                    .height(23.dp)
                    .width(304.dp)
                    .align(Alignment.CenterHorizontally)
                    .offset(y = 300.dp),
                    visibleState = state_01,
                    enter = scaleIn(animationSpec = tween(durationMillis = 300, delayMillis = 450))
                ){
                    Card(
                        modifier = Modifier
                            .height(23.dp)
                            .width(304.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(11.dp),
                        colors = CardDefaults.cardColors(Color.Green)
                    ) {}
                }

            }
        }
    }


    AnimatedVisibility(
        visibleState = start_state,
        enter = slideInVertically(animationSpec = tween(600), initialOffsetY = {it})
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
                            Color.Green.copy(0.5f),
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
                    .background(Color.White)
                )
            }
        }
    }



}