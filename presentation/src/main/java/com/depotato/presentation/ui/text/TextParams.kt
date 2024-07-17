package com.depotato.presentation.ui.text

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.depotato.presentation.ui.theme.Black
import com.depotato.presentation.ui.theme.White

data class TextParams(
    var text: String = "",
    var textColor: Color = Black,
    var backgroundColor: Color = White,
    var size: TextUnit,
    var fontFamily: FontFamily,
    var fontWeight: FontWeight,
)