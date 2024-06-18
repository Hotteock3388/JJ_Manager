package com.depotato.jubjub_manager.ui.main.equipment_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.ui.components.JJLogo
import com.depotato.jubjub_manager.ui.text.ExcludeFontPaddingText
import com.depotato.jubjub_manager.ui.text.notoSansFamily
import com.depotato.jubjub_manager.ui.theme.Blue
import com.depotato.jubjub_manager.ui.theme.HintGray
import com.depotato.jubjub_manager.ui.theme.SearchBar
import org.koin.androidx.compose.koinViewModel


@Preview(showBackground = true)
@Composable
fun EquipmentListScreenPreview() {



    EquipmentListScreen(
        EquipmentListUiState(
            equipments = listOf(
                Equipment(id = 0, name = "기자재 테스트 1", category = "카테고리", maxAmount = 100, currentAmount = 10, imageUrl = ""),
                Equipment(id = 1, name = "기자재 테스트 2", category = "카테고리", maxAmount = 100, currentAmount = 10, imageUrl = ""),
                Equipment(id = 2, name = "기자재 테스트 3", category = "카테고리", maxAmount = 100, currentAmount = 10, imageUrl = ""),
                Equipment(id = 3, name = "기자재 테스트 4", category = "카테고리", maxAmount = 100, currentAmount = 10, imageUrl = ""),
                Equipment(id = 4, name = "기자재 테스트 5", category = "카테고리", maxAmount = 100, currentAmount = 10, imageUrl = "")
            )
        ), {}, {}
    )
}

@Composable
fun EquipmentListScreen(
    viewModel: EquipmentListViewModel = koinViewModel()
) {
    val equipmentListUiState by viewModel.equipmentListUiState.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )

    LaunchedEffect(viewModel) {
        viewModel.getEquipments()
    }

    EquipmentListScreen(
        equipmentListUiState,
        onSearchTextChanged = { text -> viewModel.emitSearchText(text) },
        onEquipmentItemClick = { equipment -> viewModel.emitClickedEquipment(equipment) }
    )

}

@Composable
fun EquipmentListScreen(
    equipmentListUiState: EquipmentListUiState = EquipmentListUiState(),
    onSearchTextChanged: (String) -> Unit = {},
    onEquipmentItemClick: (Equipment) -> Unit = {}
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        JJLogo()
        Spacer(modifier = Modifier.padding(top = 16.dp))
        SearchBar(
            searchText = equipmentListUiState.searchText,
            onValueChanged = onSearchTextChanged
        )
        EquipmentList(
            equipments = equipmentListUiState.equipments,
            searchText = equipmentListUiState.searchText,
            onEquipmentItemClick = onEquipmentItemClick
        )
    }
}


@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    searchText: String,
    onValueChanged: (String) -> Unit
) {
    val modifier = modifier.height(35.dp)

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.CenterVertically)
            .padding(start = 20.dp, end = 20.dp, top = 0.dp, bottom = 0.dp),
        value = searchText,
        textStyle = TextStyle(
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            ),
            fontFamily = notoSansFamily,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        ),
        onValueChange = onValueChanged,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(SearchBar)
            ) {
                if (searchText.isBlank()) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = modifier
                                .width(2.dp)
                                .wrapContentHeight()
                        ) {
                            innerTextField()
                        }
                        // PlaceHolder
                        ExcludeFontPaddingText(
                            modifier = modifier.wrapContentHeight(),
                            text = stringResource(id = R.string.search),
                            color = HintGray,
                            textAlign = TextAlign.Center,
                            fontFamily = notoSansFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    }
                } else {
                    //TextField
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(end = 5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        innerTextField()
                    }
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(end = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Image(
                        modifier = modifier
                            .height(14.dp)
                            .width(14.dp)
                            .padding(),
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "기자재 검색"
                    )
                }
            }
        }
    )
}

@Composable
fun EquipmentList(
    equipments: List<Equipment>,
    searchText: String,
    onEquipmentItemClick: (Equipment) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        contentPadding = PaddingValues(top = 22.dp, bottom = 22.dp)
    ) {
        items(items = equipments.filter {
            it.name.lowercase().trim().contains(searchText.trim()) ||
                    it.category.lowercase().trim().contains(searchText.trim())
        }) {
            EquipmentItem(
                equipment = it,
                onEquipmentItemClick = { onEquipmentItemClick(it) }
            )
        }
    }
}

@Composable
fun EquipmentItem(
    modifier: Modifier = Modifier,
    equipment: Equipment,
    onEquipmentItemClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .shadow(8.dp, spotColor = Blue, shape = RoundedCornerShape(10.dp))
            .clickable { onEquipmentItemClick() },
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
        ) {
            val commonTextStyle = TextStyle(
                fontFamily = notoSansFamily,
                color = Blue,
            )
            Column(
                modifier = modifier.padding(
                    top = 17.5.dp,
                    bottom = 17.5.dp,
                    start = 17.dp
                )
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(equipment.imageUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_add_image),
                    contentDescription = stringResource(R.string.equipment_image_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(65.dp)
                        .height(65.dp)
                )
            }
            Column(
                modifier = modifier.padding(
                    top = 17.5.dp,
                    bottom = 17.5.dp,
                    start = 20.dp
                )
            ) {
                ExcludeFontPaddingText(
                    text = equipment.name,
                    style = commonTextStyle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                ExcludeFontPaddingText(
                    modifier = modifier.padding(top = 1.dp),
                    text = equipment.category,
                    style = commonTextStyle,
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp
                )
                ExcludeFontPaddingText(
                    modifier = modifier.padding(top = 16.dp),
                    text = "수량 : ${equipment.currentAmount}/${equipment.maxAmount}개",
                    style = commonTextStyle,
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp
                )
            }

        }
    }
}