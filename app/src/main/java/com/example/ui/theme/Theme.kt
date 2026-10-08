package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = MaroonLight,
    onPrimary = TextWhite,
    primaryContainer = MaroonDark,
    onPrimaryContainer = TextWhite,
    secondary = MetallicSilver,
    onSecondary = GraphiteBackground,
    secondaryContainer = DarkGray,
    onSecondaryContainer = TextWhite,
    tertiary = MaroonAccent,
    onTertiary = TextWhite,
    background = GraphiteBackground,
    onBackground = TextWhite,
    surface = GraphiteSurface,
    onSurface = TextWhite,
    surfaceVariant = GraphiteSurfaceVariant,
    onSurfaceVariant = TextMuted,
    outline = SurfaceBorder,
    error = StatusError,
    onError = TextWhite
)

private val LightColorScheme = lightColorScheme(
    primary = MaroonPrimary,
    onPrimary = TextWhite,
    primaryContainer = MaroonLight,
    onPrimaryContainer = TextWhite,
    secondary = DarkGray,
    onSecondary = TextWhite,
    secondaryContainer = MetallicSilver,
    onSecondaryContainer = GraphiteBackground,
    tertiary = MaroonDark,
    onTertiary = TextWhite,
    background = Color(0xFFF4F6F8),
    onBackground = GraphiteBackground,
    surface = Color(0xFFFFFFFF),
    onSurface = GraphiteBackground,
    surfaceVariant = Color(0xFFE5E9EE),
    onSurfaceVariant = Color(0xFF4A5568),
    outline = Color(0xFFCBD5E0),
    error = StatusError,
    onError = TextWhite
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Default to Dark Industrial Maroon theme
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
