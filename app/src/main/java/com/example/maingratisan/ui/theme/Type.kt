package com.example.maingratisan.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    )
    /* labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val Typography.cardTitle: TextStyle
    @Composable
    get() {
        return TextStyle(
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp,
            shadow = Shadow(
                color = DarkGradient,
                offset = Offset(5.0f, 5.0f),
                blurRadius = 3f
            )
        )
    }

val Typography.cardSmall: TextStyle
    @Composable
    get() {
        return TextStyle(
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            letterSpacing = 0.5.sp,
            shadow = Shadow(
                color = DarkGradient,
                offset = Offset(5.0f, 5.0f),
                blurRadius = 3f
            )
        )
    }