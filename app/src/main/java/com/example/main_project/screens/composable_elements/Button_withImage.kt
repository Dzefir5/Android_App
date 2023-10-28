package com.example.main_project.screens.composable_elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.main_project.R

@Composable
fun Button_withImage(size : Int, fade : Float,imagesize : Float)
{
    Box(
      modifier = Modifier
          .clip(CircleShape)
          .size(size.dp)
          .background(
              brush = Brush.radialGradient(
                  colorStops = arrayOf(
                      0.6f to Color.White.copy(
                          1.0f
                      ), 0.9f to Color.White.copy(0.0f)
                  )
              )
          )
    ){
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size((size*fade).dp)
                .align(Alignment.Center)
                .background(Color.White)
        ){
            Icon(
                modifier = Modifier
                    .size( (size*imagesize ).dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.dish_icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}