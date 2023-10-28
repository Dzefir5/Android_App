package com.example.main_project.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = PaleCyan50,
    onPrimary = Cyan90,
    primaryContainer = PaleCyan70,
    onPrimaryContainer = Green90,
    inversePrimary = Green40,
    //secondary = DarkGreen80,
    // onSecondary = DarkGreen20,
    secondaryContainer = Color.White,
    // onSecondaryContainer = DarkGreen90,
    tertiary = Violet80,
    onTertiary = Violet20,
    tertiaryContainer = Violet30,
    onTertiaryContainer = Violet90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = Grey10,
    onBackground = Grey90,
    // surface = GreenGrey30,
    // onSurface = GreenGrey80,
    inverseSurface = Grey90,
    inverseOnSurface = Grey10,
    // surfaceVariant = GreenGrey30,
    // onSurfaceVariant = GreenGrey80,
    //outline = GreenGrey80
)

private val LightColorScheme = lightColorScheme(
    primary = PaleCyan50,
    onPrimary = Cyan90,
    primaryContainer =PaleCyan70,
    onPrimaryContainer = Green90,
    inversePrimary = Green40,
    //secondary = DarkGreen80,
    // onSecondary = DarkGreen20,
    secondaryContainer = Color.White,
    // onSecondaryContainer = DarkGreen90,
    tertiary = Violet80,
    onTertiary = Violet20,
    tertiaryContainer = Violet30,
    onTertiaryContainer = Violet90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = Grey10,
    onBackground = Grey90,
    // surface = GreenGrey30,
    // onSurface = GreenGrey80,
    inverseSurface = Grey90,
    inverseOnSurface = Grey10,
    // surfaceVariant = GreenGrey30,
    // onSurfaceVariant = GreenGrey80,
    //outline = GreenGrey80
)

@Composable
fun MainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val dynamicColor: Boolean = Build.VERSION.SDK_INT>=Build.VERSION_CODES.S
        val colorScheme = when{

            //dynamicColor&&darkTheme -> dynamicDarkColorScheme(LocalContext.current)
            //dynamicColor&&!darkTheme -> dynamicLightColorScheme(LocalContext.current)
            darkTheme -> DarkColorScheme
            else -> LightColorScheme

        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}