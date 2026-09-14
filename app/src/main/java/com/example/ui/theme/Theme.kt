package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

enum class ThemeMode(val label: String) {
    SYSTEM("System Default"),
    LIGHT("Light Mode"),
    DARK("Dark Mode")
}

enum class ColorTheme(val label: String, val description: String) {
    COMBINED("Triad Combined", "All primary colors (Red, Blue, Yellow) combined"),
    RED("Crimson Red", "Warm ruby and cherry tones"),
    BLUE("Cobalt Blue", "Deep ocean sapphire and cobalt tones"),
    YELLOW("Sunlit Gold", "Bright sunflower and warm golden amber"),
    WOODCRAFT("Classic Woodcraft", "Traditional workshop oak and walnut tones")
}

// Woodcraft (Timber Amber)
private val AmberWoodDarkColorScheme = darkColorScheme(
    primary = AmberWoodDarkPrimary,
    onPrimary = AmberWoodDarkOnPrimary,
    primaryContainer = AmberWoodDarkPrimaryContainer,
    onPrimaryContainer = AmberWoodDarkOnPrimaryContainer,
    secondary = AmberWoodDarkSecondary,
    onSecondary = AmberWoodDarkOnSecondary,
    secondaryContainer = AmberWoodDarkSecondaryContainer,
    onSecondaryContainer = AmberWoodDarkOnSecondaryContainer,
    tertiary = AmberWoodDarkTertiary,
    onTertiary = AmberWoodDarkOnTertiary,
    tertiaryContainer = AmberWoodDarkTertiaryContainer,
    onTertiaryContainer = AmberWoodDarkOnTertiaryContainer,
    background = AmberWoodDarkBackground,
    onBackground = AmberWoodDarkOnBackground,
    surface = AmberWoodDarkSurface,
    onSurface = AmberWoodDarkOnSurface,
    surfaceVariant = AmberWoodDarkSurfaceVariant,
    onSurfaceVariant = AmberWoodDarkOnSurfaceVariant,
    outline = AmberWoodDarkOutline
)

private val AmberWoodLightColorScheme = lightColorScheme(
    primary = AmberWoodLightPrimary,
    onPrimary = AmberWoodLightOnPrimary,
    primaryContainer = AmberWoodLightPrimaryContainer,
    onPrimaryContainer = AmberWoodLightOnPrimaryContainer,
    secondary = AmberWoodLightSecondary,
    onSecondary = AmberWoodLightOnSecondary,
    secondaryContainer = AmberWoodLightSecondaryContainer,
    onSecondaryContainer = AmberWoodLightOnSecondaryContainer,
    tertiary = AmberWoodLightTertiary,
    onTertiary = AmberWoodLightOnTertiary,
    tertiaryContainer = AmberWoodLightTertiaryContainer,
    onTertiaryContainer = AmberWoodLightOnTertiaryContainer,
    background = AmberWoodLightBackground,
    onBackground = AmberWoodLightOnBackground,
    surface = AmberWoodLightSurface,
    onSurface = AmberWoodLightOnSurface,
    surfaceVariant = AmberWoodLightSurfaceVariant,
    onSurfaceVariant = AmberWoodLightOnSurfaceVariant,
    outline = AmberWoodLightOutline
)

// Red Schemes
private val RedLightColorScheme = lightColorScheme(
    primary = RedLightPrimary,
    onPrimary = RedLightOnPrimary,
    primaryContainer = RedLightPrimaryContainer,
    onPrimaryContainer = RedLightOnPrimaryContainer,
    secondary = RedLightSecondary,
    onSecondary = RedLightOnSecondary,
    secondaryContainer = RedLightSecondaryContainer,
    onSecondaryContainer = RedLightOnSecondaryContainer,
    tertiary = RedLightTertiary,
    onTertiary = RedLightOnTertiary,
    tertiaryContainer = RedLightTertiaryContainer,
    onTertiaryContainer = RedLightOnTertiaryContainer,
    background = RedLightBackground,
    onBackground = RedLightOnBackground,
    surface = RedLightSurface,
    onSurface = RedLightOnSurface,
    surfaceVariant = RedLightSurfaceVariant,
    onSurfaceVariant = RedLightOnSurfaceVariant,
    outline = RedLightOutline
)

private val RedDarkColorScheme = darkColorScheme(
    primary = RedDarkPrimary,
    onPrimary = RedDarkOnPrimary,
    primaryContainer = RedDarkPrimaryContainer,
    onPrimaryContainer = RedDarkOnPrimaryContainer,
    secondary = RedDarkSecondary,
    onSecondary = RedDarkOnSecondary,
    secondaryContainer = RedDarkSecondaryContainer,
    onSecondaryContainer = RedDarkOnSecondaryContainer,
    tertiary = RedDarkTertiary,
    onTertiary = RedDarkOnTertiary,
    tertiaryContainer = RedDarkTertiaryContainer,
    onTertiaryContainer = RedDarkOnTertiaryContainer,
    background = RedDarkBackground,
    onBackground = RedDarkOnBackground,
    surface = RedDarkSurface,
    onSurface = RedDarkOnSurface,
    surfaceVariant = RedDarkSurfaceVariant,
    onSurfaceVariant = RedDarkOnSurfaceVariant,
    outline = RedDarkOutline
)

// Blue Schemes
private val BlueLightColorScheme = lightColorScheme(
    primary = BlueLightPrimary,
    onPrimary = BlueLightOnPrimary,
    primaryContainer = BlueLightPrimaryContainer,
    onPrimaryContainer = BlueLightOnPrimaryContainer,
    secondary = BlueLightSecondary,
    onSecondary = BlueLightOnSecondary,
    secondaryContainer = BlueLightSecondaryContainer,
    onSecondaryContainer = BlueLightOnSecondaryContainer,
    tertiary = BlueLightTertiary,
    onTertiary = BlueLightOnTertiary,
    tertiaryContainer = BlueLightTertiaryContainer,
    onTertiaryContainer = BlueLightOnTertiaryContainer,
    background = BlueLightBackground,
    onBackground = BlueLightOnBackground,
    surface = BlueLightSurface,
    onSurface = BlueLightOnSurface,
    surfaceVariant = BlueLightSurfaceVariant,
    onSurfaceVariant = BlueLightOnSurfaceVariant,
    outline = BlueLightOutline
)

private val BlueDarkColorScheme = darkColorScheme(
    primary = BlueDarkPrimary,
    onPrimary = BlueDarkOnPrimary,
    primaryContainer = BlueDarkPrimaryContainer,
    onPrimaryContainer = BlueDarkOnPrimaryContainer,
    secondary = BlueDarkSecondary,
    onSecondary = BlueDarkOnSecondary,
    secondaryContainer = BlueDarkSecondaryContainer,
    onSecondaryContainer = BlueDarkOnSecondaryContainer,
    tertiary = BlueDarkTertiary,
    onTertiary = BlueDarkOnTertiary,
    tertiaryContainer = BlueDarkTertiaryContainer,
    onTertiaryContainer = BlueDarkOnTertiaryContainer,
    background = BlueDarkBackground,
    onBackground = BlueDarkOnBackground,
    surface = BlueDarkSurface,
    onSurface = BlueDarkOnSurface,
    surfaceVariant = BlueDarkSurfaceVariant,
    onSurfaceVariant = BlueDarkOnSurfaceVariant,
    outline = BlueDarkOutline
)

// Yellow Schemes
private val YellowLightColorScheme = lightColorScheme(
    primary = YellowLightPrimary,
    onPrimary = YellowLightOnPrimary,
    primaryContainer = YellowLightPrimaryContainer,
    onPrimaryContainer = YellowLightOnPrimaryContainer,
    secondary = YellowLightSecondary,
    onSecondary = YellowLightOnSecondary,
    secondaryContainer = YellowLightSecondaryContainer,
    onSecondaryContainer = YellowLightOnSecondaryContainer,
    tertiary = YellowLightTertiary,
    onTertiary = YellowLightOnTertiary,
    tertiaryContainer = YellowLightTertiaryContainer,
    onTertiaryContainer = YellowLightOnTertiaryContainer,
    background = YellowLightBackground,
    onBackground = YellowLightOnBackground,
    surface = YellowLightSurface,
    onSurface = YellowLightOnSurface,
    surfaceVariant = YellowLightSurfaceVariant,
    onSurfaceVariant = YellowLightOnSurfaceVariant,
    outline = YellowLightOutline
)

private val YellowDarkColorScheme = darkColorScheme(
    primary = YellowDarkPrimary,
    onPrimary = YellowDarkOnPrimary,
    primaryContainer = YellowDarkPrimaryContainer,
    onPrimaryContainer = YellowDarkOnPrimaryContainer,
    secondary = YellowDarkSecondary,
    onSecondary = YellowDarkOnSecondary,
    secondaryContainer = YellowDarkSecondaryContainer,
    onSecondaryContainer = YellowDarkOnSecondaryContainer,
    tertiary = YellowDarkTertiary,
    onTertiary = YellowDarkOnTertiary,
    tertiaryContainer = YellowDarkTertiaryContainer,
    onTertiaryContainer = YellowDarkOnTertiaryContainer,
    background = YellowDarkBackground,
    onBackground = YellowDarkOnBackground,
    surface = YellowDarkSurface,
    onSurface = YellowDarkOnSurface,
    surfaceVariant = YellowDarkSurfaceVariant,
    onSurfaceVariant = YellowDarkOnSurfaceVariant,
    outline = YellowDarkOutline
)

// Combined (Red + Blue + Yellow All Combined) Schemes
private val CombinedLightColorScheme = lightColorScheme(
    primary = CombinedLightPrimary,
    onPrimary = CombinedLightOnPrimary,
    primaryContainer = CombinedLightPrimaryContainer,
    onPrimaryContainer = CombinedLightOnPrimaryContainer,
    secondary = CombinedLightSecondary,
    onSecondary = CombinedLightOnSecondary,
    secondaryContainer = CombinedLightSecondaryContainer,
    onSecondaryContainer = CombinedLightOnSecondaryContainer,
    tertiary = CombinedLightTertiary,
    onTertiary = CombinedLightOnTertiary,
    tertiaryContainer = CombinedLightTertiaryContainer,
    onTertiaryContainer = CombinedLightOnTertiaryContainer,
    background = CombinedLightBackground,
    onBackground = CombinedLightOnBackground,
    surface = CombinedLightSurface,
    onSurface = CombinedLightOnSurface,
    surfaceVariant = CombinedLightSurfaceVariant,
    onSurfaceVariant = CombinedLightOnSurfaceVariant,
    outline = CombinedLightOutline
)

private val CombinedDarkColorScheme = darkColorScheme(
    primary = CombinedDarkPrimary,
    onPrimary = CombinedDarkOnPrimary,
    primaryContainer = CombinedDarkPrimaryContainer,
    onPrimaryContainer = CombinedDarkOnPrimaryContainer,
    secondary = CombinedDarkSecondary,
    onSecondary = CombinedDarkOnSecondary,
    secondaryContainer = CombinedDarkSecondaryContainer,
    onSecondaryContainer = CombinedDarkOnSecondaryContainer,
    tertiary = CombinedDarkTertiary,
    onTertiary = CombinedDarkOnTertiary,
    tertiaryContainer = CombinedDarkTertiaryContainer,
    onTertiaryContainer = CombinedDarkOnTertiaryContainer,
    background = CombinedDarkBackground,
    onBackground = CombinedDarkOnBackground,
    surface = CombinedDarkSurface,
    onSurface = CombinedDarkOnSurface,
    surfaceVariant = CombinedDarkSurfaceVariant,
    onSurfaceVariant = CombinedDarkOnSurfaceVariant,
    outline = CombinedDarkOutline
)

@Composable
fun MyApplicationTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    colorTheme: ColorTheme = ColorTheme.WOODCRAFT,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val isSystemDark = isSystemInDarkTheme()
    val isDark = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemDark
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val colorScheme: ColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> {
            when (colorTheme) {
                ColorTheme.WOODCRAFT -> if (isDark) AmberWoodDarkColorScheme else AmberWoodLightColorScheme
                ColorTheme.RED -> if (isDark) RedDarkColorScheme else RedLightColorScheme
                ColorTheme.BLUE -> if (isDark) BlueDarkColorScheme else BlueLightColorScheme
                ColorTheme.YELLOW -> if (isDark) YellowDarkColorScheme else YellowLightColorScheme
                ColorTheme.COMBINED -> if (isDark) CombinedDarkColorScheme else CombinedLightColorScheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


