package com.depotato.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import com.depotato.presentation.R
import com.depotato.presentation.base.BaseActivity
import com.depotato.presentation.ui.main.equipment_list.EquipmentListScreen
import com.depotato.presentation.ui.main.equipment_list.EquipmentListViewModel
import com.depotato.presentation.ui.main.my_page.MyPageScreen
import com.depotato.presentation.ui.main.my_page.MyPageViewModel
import com.depotato.presentation.ui.modify_equipment.edit_equipment.EditEquipmentComposeActivity
import com.depotato.presentation.ui.sign_in.SignInActivity
import com.depotato.presentation.ui.theme.JubJub_ManagerTheme
import com.depotato.presentation.ui.theme.White
import dagger.hilt.android.AndroidEntryPoint

enum class CurrentNavigationItem {
    HOME, MY_PAGE
}

@AndroidEntryPoint
class MainComposeActivity() : BaseActivity<MainActivityViewModel>("MainComposeActivity") {

    // 마지막으로 뒤로가기 누른 시각

    override val viewModel: MainActivityViewModel by viewModels()

    private val equipmentListViewModel : EquipmentListViewModel by viewModels()
    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBackPressedCallback()

        setContent {
            MainScreen()
        }

        collectWhenStarted(equipmentListViewModel.clickedEquipment){
            Intent(this, EditEquipmentComposeActivity::class.java).apply {
                putExtra("equipment", it)
                startActivity(this)
            }
        }

        collectWhenStarted(myPageViewModel.logOutComplete){
            logOut()
        }

    }
    private fun logOut(){
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }

    override fun onResume() {
        super.onResume()
        equipmentListViewModel.getEquipments()
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
        Row {
            Button(
                modifier = Modifier
                    .weight(1.0F)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = White,
                ),
                onClick = onHomeClick
            ) {
                if (currentPage == CurrentNavigationItem.HOME) {
                    Image(
                        painter = painterResource(R.drawable.ic_home_selected),
                        contentDescription = ""
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.ic_home_unselected),
                        contentDescription = ""
                    )
                }
            }
            Button(
                modifier = Modifier
                    .weight(1.0F)
                    .fillMaxWidth()
                    .fillMaxHeight(),
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