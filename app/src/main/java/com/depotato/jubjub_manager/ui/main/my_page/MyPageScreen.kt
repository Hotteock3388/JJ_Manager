package com.depotato.jubjub_manager.ui.main.my_page

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.ui.components.JJLogo
import com.depotato.jubjub_manager.ui.modify_equipment.add_equipment.AddEquipmentComposeActivity
import com.depotato.jubjub_manager.ui.text.notoSansFamily
import com.depotato.jubjub_manager.ui.theme.Black
import com.depotato.jubjub_manager.ui.theme.LogOut


@Preview(showBackground = true)
@Composable
fun MyPageScreenPreview() {
    MyPageScreen({}, {})
}

fun openAddEquipmentActivity(context: Context) {
    context.startActivity(Intent(context, AddEquipmentComposeActivity::class.java))
}

@Composable
fun MyPageScreen(
    viewModel: MyPageViewModel = hiltViewModel(),
){
    val context = LocalContext.current

    MyPageScreen(
        onAddEquipmentClick = { openAddEquipmentActivity(context) },
        onLogOutClick = { viewModel.logOut() }
    )
}

@Composable
fun MyPageScreen(
    onAddEquipmentClick: () -> Unit,
    onLogOutClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            JJLogo()
            MyPageTitle(modifier = Modifier.padding(top = 20.dp))
            MyPageMenuItem(
                textResourceId = R.string.menu_add_equipment,
                onClick = onAddEquipmentClick,
                modifier = Modifier.padding(top = 50.dp))
            MyPageMenuItem(
                textResourceId = R.string.menu_log_out,
                textColor = LogOut,
                onClick = onLogOutClick
            )
        }
        Image(
            painter = painterResource(id = R.drawable.group_3),
            modifier = Modifier
                .fillMaxWidth()
                .height(182.dp),
            contentScale = ContentScale.FillBounds,
            contentDescription = "")
    }
}

@Composable
fun MyPageTitle(modifier: Modifier = Modifier){
    Text(
        text = stringResource(id = R.string.my_page),
        modifier = modifier,
        fontFamily = notoSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
}

@Composable
fun MyPageMenuItem(
    modifier: Modifier = Modifier,
    textResourceId: Int,
    textColor: Color = Black,
    onClick: () -> Unit,
){
    TextButton(
        modifier = modifier
            .fillMaxWidth()
            .height(43.dp),
        onClick = onClick
    ){
        Text(
            text = stringResource(textResourceId),
            color = textColor,
            fontFamily = notoSansFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        )
    }
}