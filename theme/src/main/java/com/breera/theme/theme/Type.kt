package com.breera.theme.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.breera.theme.R
import ir.kaaveh.sdpcompose.ssp

// Set of Material typography styles to start with
val Weather_Typography: Typography
    @Composable
    get() = Typography(
        headlineLarge = TextStyle(
            fontSize = 28.ssp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontWeight = FontWeight.SemiBold
        ),

        headlineMedium = TextStyle(
            fontSize = 22.ssp,
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontWeight = FontWeight.W700,
            color = Color.Black
        ),

        headlineSmall = TextStyle(
            fontSize = 18.ssp,
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        ),

        bodyLarge = TextStyle(
            fontSize = 40.ssp,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        ),

        bodySmall = TextStyle(
            color = MaterialTheme.colorScheme.inverseOnSurface,
            fontSize = 13.ssp,
            fontWeight = FontWeight.SemiBold
        ),

        titleLarge = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 50.ssp,
            color = Color.Black,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            )
        ),

        labelLarge = TextStyle(
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            ),
            textAlign = TextAlign.Center,
            fontSize = 20.ssp,
            color = Color.Black,
        ),

        labelMedium = TextStyle(
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            ),
            fontWeight = FontWeight.SemiBold,
            fontSize = 10.ssp,
            color =  MaterialTheme.colorScheme.onPrimaryContainer,
        ),

        labelSmall = TextStyle(
            fontSize = 11.ssp,
            fontWeight = FontWeight.W700,
            color = Color.Black
        )
    )