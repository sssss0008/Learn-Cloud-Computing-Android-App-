package com.example.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.data.model.ThemeMode

private val DarkColorScheme = darkColorScheme(
    primary = CloudBlueLight,
    onPrimary = DeepNavy,
    primaryContainer = Color(0xFF0369A1),
    onPrimaryContainer = Color(0xFFE0F2FE),
    secondary = ElectricCyan,
    onSecondary = DeepNavy,
    secondaryContainer = Color(0xFF083344),
    onSecondaryContainer = Color(0xFFCFFAFE),
    tertiary = CloudViolet,
    onTertiary = DeepNavy,
    background = DeepNavy,
    onBackground = Slate50,
    surface = DarkNavyCard,
    onSurface = Slate100,
    surfaceVariant = DarkNavyCardElevated,
    onSurfaceVariant = Slate300,
    outline = CyberBorder,
    error = StatusError,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = CloudBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE0F2FE),
    onPrimaryContainer = Color(0xFF075985),
    secondary = Color(0xFF0284C7),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0F2FE),
    onSecondaryContainer = Color(0xFF0369A1),
    tertiary = Color(0xFF6366F1),
    onTertiary = Color.White,
    background = Slate50,
    onBackground = Slate900,
    surface = Color.White,
    onSurface = Slate900,
    surfaceVariant = Slate100,
    onSurfaceVariant = Slate700,
    outline = Slate200,
    error = StatusError,
    onError = Color.White
)

private val AmoledColorScheme = darkColorScheme(
    primary = ElectricCyan,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF0E7490),
    onPrimaryContainer = Color(0xFFCFFAFE),
    secondary = CloudBlueLight,
    onSecondary = Color.Black,
    tertiary = CloudViolet,
    onTertiary = Color.Black,
    background = AmoledBackground,
    onBackground = Color.White,
    surface = AmoledCard,
    onSurface = Color.White,
    surfaceVariant = Color(0xFF1E1E1E),
    onSurfaceVariant = Slate300,
    outline = AmoledBorder,
    error = StatusError,
    onError = Color.White
)

private val HighContrastColorScheme = darkColorScheme(
    primary = Color(0xFF38BDF8),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF0369A1),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF00E5FF),
    onSecondary = Color.Black,
    tertiary = Color(0xFFA5B4FC),
    onTertiary = Color.Black,
    background = Color.Black,
    onBackground = Color.White,
    surface = Color(0xFF121212),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF222222),
    onSurfaceVariant = Color.White,
    outline = Color.White,
    error = Color(0xFFFF5252),
    onError = Color.Black
)

@Composable
fun LearnCloudTheme(
    themeMode: ThemeMode = ThemeMode.DARK,
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeMode) {
        ThemeMode.DARK -> DarkColorScheme
        ThemeMode.LIGHT -> LightColorScheme
        ThemeMode.AMOLED -> AmoledColorScheme
        ThemeMode.HIGH_CONTRAST -> HighContrastColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
