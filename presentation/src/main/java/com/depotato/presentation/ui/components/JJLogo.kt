package com.depotato.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.depotato.presentation.R

@Composable
fun JJLogo() {
    Surface(
        modifier = Modifier
            .width(66.dp)
            .padding(top = 30.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_main_logo),
            contentDescription = "Login Title Logo"
        )
    }
}