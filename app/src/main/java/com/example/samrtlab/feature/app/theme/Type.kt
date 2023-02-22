package com.example.samrtlab.feature.app.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.samrtlab.R

val Family = FontFamily(
    Font(R.font.lato_black, weight = FontWeight.Black),
    Font(R.font.lato_black_italic, weight = FontWeight.Black, style = FontStyle.Italic),
    Font(R.font.lato_bold, weight = FontWeight.Bold),
    Font(R.font.lato_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.lato_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.lato_light, weight = FontWeight.Light),
    Font(R.font.lato_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.lato_regular, weight = FontWeight.Normal),
    Font(R.font.lato_thin, weight = FontWeight.Thin),
    Font(R.font.lato_thin_italic, weight = FontWeight.Thin, style = FontStyle.Italic),
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = Family,
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)