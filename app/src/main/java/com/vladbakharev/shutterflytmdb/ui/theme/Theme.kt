package com.vladbakharev.shutterflytmdb.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = DarkBlue,
    secondary = LightBlue,
    tertiary = LightGreen
)

private val LightColorScheme = lightColorScheme(
    primary = DarkBlue,
    secondary = LightBlue,
    tertiary = LightGreen

    /*  Other default colors to override
     background = Color(0xFFFFFBFE),
     surface = Color(0xFFFFFBFE),
     onPrimary = Color.White,
     onSecondary = Color.White,
     onTertiary = Color.White,
     onBackground = Color(0xFF1C1B1F),
     onSurface = Color(0xFF1C1B1F),*/

)

@Composable
fun ShutterflyTMDBTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = DarkBlue,
            darkIcons = false
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
