package com.szechwaneseLexicon.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = Color(0xFF006C51),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF89F8D3),
    onPrimaryContainer = Color(0xFF002117),
    secondary = Color(0xFF4C6358),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFCEE9DB),
    onSecondaryContainer = Color(0xFF082016),
    background = Color(0xFFFBFDF9),
    surface = Color(0xFFFBFDF9),
    onSurface = Color(0xFF191C1A)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF6CDBB7),
    onPrimary = Color(0xFF00382A),
    primaryContainer = Color(0xFF00513D),
    onPrimaryContainer = Color(0xFF89F8D3),
    secondary = Color(0xFFB3CCBF),
    onSecondary = Color(0xFF1E352B),
    secondaryContainer = Color(0xFF344B41),
    onSecondaryContainer = Color(0xFFCEE9DB),
    background = Color(0xFF191C1A),
    surface = Color(0xFF191C1A),
    onSurface = Color(0xFFE1E3DF)
)

@Composable
fun SzechwaneseDictionaryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
} 