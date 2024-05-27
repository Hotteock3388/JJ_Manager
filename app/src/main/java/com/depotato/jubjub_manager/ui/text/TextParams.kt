package com.depotato.jubjub_manager.ui.text

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.depotato.jubjub_manager.ui.theme.Black
import com.depotato.jubjub_manager.ui.theme.White

data class TextParams(
    val text: String = "",
    val textColor: Color = Black,
    val backgroundColor: Color = White,
    val size: TextUnit,
    val fontFamily: FontFamily,
    val fontWeight: FontWeight,
)