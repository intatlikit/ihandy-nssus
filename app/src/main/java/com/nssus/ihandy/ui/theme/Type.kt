package com.nssus.ihandy.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

//val helvathaicaFonts = FontFamily(
//    Font(R.font.db_helvathaica),
//    Font(R.font.db_helvathaica_bd, FontWeight.Bold),
//    Font(R.font.db_helvathaica_bd_it, FontWeight.Bold, FontStyle.Italic),
//    Font(R.font.db_helvathaica_blk),
//    Font(R.font.db_helvathaica_blk_it, style = FontStyle.Italic),
//    Font(R.font.db_helvathaica_it, style = FontStyle.Italic),
//    Font(R.font.db_helvathaica_med),
//    Font(R.font.db_helvathaica_med_it, style = FontStyle.Italic),
//)

class CustomFontStyle(
    val txt14: TextStyle,
    val txt16: TextStyle,
    val txt20: TextStyle,
    val txt22: TextStyle,
    val txt24: TextStyle,
    val txt26: TextStyle,
    val txt28: TextStyle,
    val txt30: TextStyle,
    val txt32: TextStyle,
    val txt34: TextStyle,
    val txt36: TextStyle,
    val txt38: TextStyle,
    val txt40: TextStyle,
    val txt42: TextStyle,
    val txt44: TextStyle
)

val defaultTypography = Typography(
    titleSmall = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 18.sp,
    ),
    titleMedium = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 24.sp,
    ),
    titleLarge = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 32.sp,
    ),
    bodySmall = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 16.sp,
    ),
    bodyMedium = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 18.sp,
    ),
    bodyLarge = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 20.sp,
    )
)

val sw480Typography = Typography(
    titleSmall = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 20.sp,
    ),
    titleMedium = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 26.sp,
    ),
    titleLarge = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 38.sp,
    ),
    bodySmall = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 18.sp,
    ),
    bodyMedium = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 20.sp,
    ),
    bodyLarge = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 22.sp,
    )
)

val defaultFontStyle = CustomFontStyle(
    txt14 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 14.sp,
        color = Color.Black
    ),
    txt16 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 16.sp,
        color = Color.Black
    ),
    txt20 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 20.sp,
        color = Color.Black
    ),
    txt22 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 22.sp,
        color = Color.Black
    ),
    txt24 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 24.sp,
        color = Color.Black
    ),
    txt26 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 26.sp,
        color = Color.Black
    ),
    txt28 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 28.sp,
        color = Color.Black
    ),
    txt30 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 30.sp,
        color = Color.Black
    ),
    txt32 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 32.sp,
        color = Color.Black
    ),
    txt34 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 34.sp,
        color = Color.Black
    ),
    txt36 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 36.sp,
        color = Color.Black
    ),
    txt38 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 38.sp,
        color = Color.Black
    ),
    txt40 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 40.sp,
        color = Color.Black
    ),
    txt42 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 42.sp,
        color = Color.Black
    ),
    txt44 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 44.sp,
        color = Color.Black
    )
)

// ยังใช้ขนาดเดียวกับจอเล็ก
val sw480FontStyle = CustomFontStyle(
    txt14 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 14.sp,
        color = Color.Black
    ),
    txt16 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 16.sp,
        color = Color.Black
    ),
    txt20 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 20.sp,
        color = Color.Black
    ),
    txt22 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 22.sp,
        color = Color.Black
    ),
    txt24 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 24.sp,
        color = Color.Black
    ),
    txt26 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 26.sp,
        color = Color.Black
    ),
    txt28 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 28.sp,
        color = Color.Black
    ),
    txt30 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 30.sp,
        color = Color.Black
    ),
    txt32 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 32.sp,
        color = Color.Black
    ),
    txt34 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 34.sp,
        color = Color.Black
    ),
    txt36 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 36.sp,
        color = Color.Black
    ),
    txt38 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 38.sp,
        color = Color.Black
    ),
    txt40 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 40.sp,
        color = Color.Black
    ),
    txt42 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 42.sp,
        color = Color.Black
    ),
    txt44 = TextStyle(
//        fontFamily = helvathaicaFonts,
        fontSize = 44.sp,
        color = Color.Black
    )
)