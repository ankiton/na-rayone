package ru.narayone.sharedui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Primary,
    secondary = Secondary,
    background = Background,
    surface = Surface,
    onPrimary = OnPrimary,
    onSecondary = OnSecondary,
    onBackground = OnBackground,
    onSurface = OnSurface
)

@Composable
fun NaRayoneTheme(content: @Composable () -> Unit) {
    val colors = DarkColorPalette
    
    MaterialTheme(
        colorScheme = colors, 
        typography = Typography, 
        shapes = Shapes, 
        content = content
    )
} 