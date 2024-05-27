package com.depotato.jubjub_manager.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.ui.theme.JubJub_ManagerTheme
import com.depotato.jubjub_manager.ui.theme.White

enum class CurrentNavigationItem {
    HOME, MY_PAGE
}

class MainComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreView() {
    MainScreen()
}

@Composable
fun MainScreen() {
    var CURRENT_PAGE by rememberSaveable() {
        mutableStateOf(CurrentNavigationItem.HOME)
    }

    JubJub_ManagerTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Box(modifier = Modifier.weight(1.0F)){
                    when(CURRENT_PAGE){
                        CurrentNavigationItem.HOME -> EquipmentListScreen()
                        CurrentNavigationItem.MY_PAGE -> MyPageScreen()
                    }
                }
                BottomNavigation(
                    currentPage = CURRENT_PAGE,
                    onHomeClick = { CURRENT_PAGE = CurrentNavigationItem.HOME },
                    onMyPageClick = { CURRENT_PAGE = CurrentNavigationItem.MY_PAGE }
                )
            }
        }
    }
}

@Composable
fun BottomNavigation(
    currentPage: CurrentNavigationItem,
    onHomeClick: () -> Unit,
    onMyPageClick: () -> Unit) {
    Surface(
        modifier = Modifier.height(60.dp),
        shadowElevation = 12.0.dp
    ) {
        Row(

        ) {
            Button(
                modifier = Modifier.weight(1.0F).fillMaxWidth().fillMaxHeight(),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = White,
                ),
                onClick = onHomeClick
            ) {
                if (currentPage == CurrentNavigationItem.HOME) {
                    Image(
                        painter = painterResource(R.drawable.ic_home_selected),
                        contentDescription = "기자재 목록"
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.ic_home_unselected),
                        contentDescription = "기자재 목록"
                    )
                }
            }
            Button(
                modifier = Modifier.weight(1.0F).fillMaxWidth().fillMaxHeight(),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = White,
                ),
                onClick = onMyPageClick
            ) {
                if (currentPage == CurrentNavigationItem.MY_PAGE) {
                    Image(
                        painter = painterResource(R.drawable.ic_my_page_selected),
                        contentDescription = "기자재 목록"
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.ic_my_page_unselected),
                        contentDescription = "마이 페이지"
                    )
                }
            }
        }
    }
}

@Composable
fun EquipmentListScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "EquipmentListScreen")
    }
}

@Composable
fun MyPageScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "MyPageScreen")
    }
}