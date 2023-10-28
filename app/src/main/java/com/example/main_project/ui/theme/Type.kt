package com.example.main_project.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.main_project.R


val LexendFont = FontFamily(
    Font(R.font.lexendthin, FontWeight.Thin),
    Font(R.font.lexendextralight, FontWeight.ExtraLight),
    Font(R.font.lexendlight, FontWeight.Light),
    Font(R.font.lexendregular, FontWeight.Normal),
    Font(R.font.lexendmedium, FontWeight.Medium),
    Font(R.font.lexendsemibold, FontWeight.SemiBold),
    Font(R.font.lexendextrabold, FontWeight.ExtraBold),
    Font(R.font.lexendbold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = LexendFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)