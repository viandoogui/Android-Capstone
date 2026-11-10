package com.group4.group4androidcapstone.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val CapstoneLightColors =
    lightColorScheme(
        primary = Color(0xFFFFBA00), //high emphasis elements like buttons, switches, active tabs
        onPrimary = Color(0xFF0C2B2E), //text on top of primary
        primaryContainer = Color(0xFFD3CECE), //used for lower emphasis containers like nav rail items or highlighted cards
        onPrimaryContainer = Color(0xFF21005D), //text and icons on top of primary container

        secondary = Color(0xFFFFBA00), //less prominent components like filter chips, badges, checkboxes, and secondary action buttons
        onSecondary =Color(0xFF0C2B2E) , //text on top of secondary
        secondaryContainer = Color(0xFFFFBA00), //Container tone for secondary elements
        onSecondaryContainer = Color(0xFF0C2B2E), //text on top of secondary container

        tertiary = Color(0xFF7D5260), //Used for contrasting accents to balance primary and secondary colors used for special highlights, decorative elements, or contrasting callouts.
        onTertiary = Color.White,//text on top of tertiary

        error = Color(0xFFBA1A1A),
        onError = Color.White,

        background = Color(0xFF6D9773),
        onBackground = Color(0xFFFFBA00),

        surface = Color(0xFF0C2B2E),//Used for the surfaces of components, Cards, Sheets, Menus, Dialogs, Bottom Navigation bars.
        onSurface = Color(0xFF1C1B1F),
    )

private val CapstoneDarkColors =
    darkColorScheme(
        primary = Color(0xFFD0BCFF),
        onPrimary = Color(0xFF381E72),
        primaryContainer = Color(0xFF4F378B),
        onPrimaryContainer = Color(0xFFEADDFF),

        secondary = Color(0xFFCCC2DC),
        onSecondary = Color(0xFF332D41),
        secondaryContainer = Color(0xFF4A4458),
        onSecondaryContainer = Color(0xFFE8DEF8),

        tertiary = Color(0xFFEFB8C8),
        onTertiary = Color(0xFF492532),

        error = Color(0xFFFFB4AB),
        onError = Color(0xFF690005),

        background = Color(0xFF1C1B1F),
        onBackground = Color(0xFFE6E1E5),

        surface = Color(0xFF1C1B1F),
        onSurface = Color(0xFFE6E1E5),
    )

val CapstoneShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(6.dp),
    medium = RoundedCornerShape(2.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(28.dp)
)


val CapstoneTypography = Typography(
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
    ),

    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
    ),

    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),

    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    )
)


@Composable
fun CapstoneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) CapstoneDarkColors else CapstoneLightColors
    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = CapstoneTypography,
            shapes = CapstoneShapes,
            content = content
        )
    }
}


