package com.depotato.jubjub_manager.ui.modify_equipment

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.ui.text.ExcludeFontPaddingText
import com.depotato.jubjub_manager.ui.text.TextParams
import com.depotato.jubjub_manager.ui.text.notoSansFamily
import com.depotato.jubjub_manager.ui.theme.Black
import com.depotato.jubjub_manager.ui.theme.Blue
import com.depotato.jubjub_manager.ui.theme.DeleteImageBG
import com.depotato.jubjub_manager.ui.theme.HintGray
import com.depotato.jubjub_manager.ui.theme.JubJub_ManagerTheme
import com.depotato.jubjub_manager.ui.theme.SpinnerBG
import com.depotato.jubjub_manager.ui.theme.SpinnerStroke
import com.depotato.jubjub_manager.ui.theme.White
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.androidx.compose.koinViewModel

class ModifyEqScreen {

    private val equipmentInfoLabelParams = TextParams(
        textColor = Blue,
        size = 16.sp,
        fontFamily = notoSansFamily,
        fontWeight = FontWeight.Bold,
    )

    private val equipmentInfoTextFieldParams = TextParams(
        size = 13.sp,
        fontFamily = notoSansFamily,
        fontWeight = FontWeight.Medium,
    )

    @Preview(showBackground = true)
    @Composable
    fun AddEquipmentScreenPreview() {
        JubJub_ManagerTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(bottom = 49.dp, end = 31.dp)
                ) {
                    Column(modifier = Modifier.padding(start = 37.dp)) {
                        ModifyEqScreenTitle()
                        EquipmentImagePicker()
                        EquipmentName()
                        EquipmentMaxAmount()
                        EquipmentCurrentAmount()
                        CategoryDropdownMenu()
                    }
                    UploadButton()
                }
            }
        }
    }

    @Composable
    fun ModifyEquipmentScreen(
        viewModel: ModifyEquipmentViewModel = koinViewModel(),
        openGallery: () -> Unit = {},
        buttonOnClick: () -> Unit = {}
    ){
        JubJub_ManagerTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(bottom = 49.dp, end = 31.dp)
                ) {
                    Column(modifier = Modifier.padding(start = 37.dp)) {
                        ModifyEqScreenTitle()
                        EquipmentImagePicker(
                            imageUri = viewModel.equipmentImageUri,
                            imageURL = viewModel.equipmentImageUrl,
                            openGallery = openGallery,
                            deleteImage = { viewModel.deleteImage() }
                        )
                        EquipmentName(
                            value = viewModel.equipmentName,
                            onValueChanged = { value -> viewModel.updateName(value) }
                        )
                        EquipmentMaxAmount(
                            value = viewModel.equipmentMaxAmount,
                            onValueChanged = { value ->
                                if (value.isDigitsOnly()) viewModel.updateMaxAmount(value)
                            }
                        )
                        EquipmentCurrentAmount(
                            value = viewModel.equipmentCurrentAmount,
                            onValueChanged = { value ->
                                if (value.isDigitsOnly()) viewModel.updateCurrentAmount(value)
                            }
                        )

                        CategoryDropdownMenu(
                            categories = viewModel.categories,
                            selectedOptionText = viewModel.equipmentCategory,
                            onCategorySelected = { selectedItem ->
                                viewModel.updateEquipmentCategory(selectedItem)
                            }
                        )
                    }
                    UploadButton(onClick = buttonOnClick)
                }
            }
        }
    }
    @Composable
    fun UploadButton(
        modifier: Modifier = Modifier,
        onClick: () -> Unit = {}
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Blue
            ),
            modifier = modifier
                .width(83.dp)
                .height(39.dp),
            onClick = onClick
        ) {
            Text(
                text = "추가",
                color = White
            )
        }
    }

    @Composable
    fun EquipmentName(
        value: StateFlow<String> = MutableStateFlow(""),
        onValueChanged: (String) -> Unit = {}
    ) {
        EquipmentInfoTextField(
            modifier = Modifier.padding(top = 9.dp),
            value = value,
            onValueChanged = onValueChanged,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            labelParams = equipmentInfoLabelParams.copy().apply { text = "제품명" },
            textFieldParams = equipmentInfoTextFieldParams.copy().apply { text = "제품명을 입력하세요." }
        )
    }

    @Composable
    fun EquipmentMaxAmount(
        value: StateFlow<String> = MutableStateFlow(""),
        onValueChanged: (String) -> Unit = {}
    ) {
        EquipmentInfoTextField(
            value = value,
            onValueChanged = onValueChanged,
            labelParams = equipmentInfoLabelParams.copy().apply { text = "전체 수량" },
            textFieldParams = equipmentInfoTextFieldParams.copy()
                .apply { text = "기자재 전체 보유 수량을 입력하세요." }
        )
    }

    @Composable
    fun EquipmentCurrentAmount(
        value: StateFlow<String> = MutableStateFlow(""),
        onValueChanged: (String) -> Unit = {}
    ) {
        EquipmentInfoTextField(
            value = value,
            onValueChanged = onValueChanged,
            labelParams = equipmentInfoLabelParams.copy().apply { text = "잔여 수량" },
            textFieldParams = equipmentInfoTextFieldParams.copy()
                .apply { text = "대여 가능한 잔여 기자재 수량을 입력하세요." }
        )
    }

    @Composable
    fun ModifyEqScreenTitle(
        modifier: Modifier = Modifier,
        text: String = "추가하기"
    ) {
        ExcludeFontPaddingText(
            text = text,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            fontFamily = notoSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            style = TextStyle(textAlign = TextAlign.Center)
        )
    }

    @Composable
    fun EquipmentImagePicker(
        modifier: Modifier = Modifier,
        imageUri: StateFlow<Uri> = MutableStateFlow(Uri.EMPTY),
        imageURL: StateFlow<String> = MutableStateFlow(""),
        openGallery: () -> Unit = {},
        deleteImage: () -> Unit = {}
    ) {
        val _imageUri = imageUri.collectAsState()
        val _imageURL = imageURL.collectAsState()

        Box(modifier = modifier.padding(top = 18.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        if (_imageUri.value == Uri.EMPTY && _imageURL.value == "")
                            R.drawable.ic_add_image
                        else if (_imageUri.value != Uri.EMPTY){
                            _imageUri.value
                        }
                        else if (_imageURL.value != ""){
                            _imageURL.value
                        } else {
                            R.drawable.ic_add_image
                        }
                    )
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_add_image),
                contentDescription = stringResource(R.string.equipment_image_description),
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .padding(top = 20.dp)
                    .width(86.dp)
                    .height(86.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        if(_imageUri.value == Uri.EMPTY && _imageURL.value == ""){
                            openGallery()
                        }
                    }
            )
            if (_imageUri.value != Uri.EMPTY || _imageURL.value != "") {
                Box(
                    modifier = modifier
                        .padding(start = 66.dp)
                        .width(40.dp)
                        .height(40.dp)
                        .padding(5.dp)
                        .clip(CircleShape)
                        .background(DeleteImageBG)
                        .clickable {
                            deleteImage()
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    )
                }
            }

        }
    }

    @Composable
    fun EquipmentInfoTextField(
        modifier: Modifier = Modifier,
        value: StateFlow<String>,
        onValueChanged: (String) -> Unit,
        labelParams: TextParams,
        textFieldParams: TextParams,
        keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        placeHolderParams: TextParams = textFieldParams.copy().apply { textColor = HintGray },
        valueVisible: Boolean = true
    ) {
        val state = value.collectAsState(initial = "")

        BasicTextField(
            value = state.value,
            modifier = modifier.padding(top = 26.dp),
            keyboardOptions = keyboardOptions,
            singleLine = true,
            visualTransformation = if (valueVisible) VisualTransformation.None else PasswordVisualTransformation(),
            textStyle = LocalTextStyle.current.merge(
                TextStyle(
                    color = textFieldParams.textColor,
                    fontSize = textFieldParams.size,
                    fontFamily = textFieldParams.fontFamily,
                    fontWeight = textFieldParams.fontWeight,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            ),
            onValueChange = onValueChanged,
            decorationBox = { innerTextField ->
                Column {
                    //Label
                    ExcludeFontPaddingText(
                        text = labelParams.text,
                        color = labelParams.textColor,
                        fontFamily = labelParams.fontFamily,
                        fontWeight = labelParams.fontWeight,
                        fontSize = labelParams.size
                    )
                    if (placeHolderParams.text.isNotBlank()) {
                        if (state.value.isBlank()) {
                            Box(modifier = Modifier.padding(9.dp)) {
                                // PlaceHolder
                                ExcludeFontPaddingText(
                                    text = placeHolderParams.text,
                                    color = placeHolderParams.textColor,
                                    fontFamily = placeHolderParams.fontFamily,
                                    fontWeight = placeHolderParams.fontWeight,
                                    fontSize = placeHolderParams.size
                                )
                                innerTextField()
                            }
                        } else {
                            //TextField
                            Box(modifier = Modifier.padding(9.dp)) {
                                innerTextField()
                            }
                        }
                    } else {
                        //TextField
                        Box(modifier = Modifier.padding(9.dp)) {
                            innerTextField()
                        }
                    }
                }
            }
        )
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun CategoryDropdownMenu(
        categories: StateFlow<List<String>> = MutableStateFlow(listOf("카테고리를 선택하세요.")).asStateFlow(),
        selectedOptionText: StateFlow<String> = MutableStateFlow(categories.value[0]),
        onCategorySelected: (String) -> Unit = { },
        labelParams: TextParams = TextParams(
            text = "카테고리",
            textColor = Blue,
            size = 16.sp,
            fontFamily = notoSansFamily,
            fontWeight = FontWeight.Bold,
        ),
    ) {
        var expanded by remember { mutableStateOf(false) }
        var selectedOptionText by remember {
            mutableStateOf(selectedOptionText.value)
        }

        Column(
            modifier = Modifier.padding(top = 26.dp)
        ) {
            // Label
            ExcludeFontPaddingText(
                text = labelParams.text,
                color = labelParams.textColor,
                fontFamily = labelParams.fontFamily,
                fontWeight = labelParams.fontWeight,
                fontSize = labelParams.size
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                modifier = Modifier
                    .padding(9.dp)
                    .width(200.dp)
                    .height(35.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(SpinnerBG),
                onExpandedChange = { expanded = it },
            ) {

                ExcludeFontPaddingText(
                    text = selectedOptionText,
                    modifier = Modifier
                        .height(35.dp)
                        .padding(start = 12.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    fontFamily = notoSansFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 9.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .background(SpinnerStroke)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dropdown_closed),
                        modifier = Modifier.padding(start = 11.dp, end = 11.dp),
                        contentDescription = ""
                    )
                }

                ExposedDropdownMenu(
                    expanded = expanded,
                    modifier = Modifier
                        .clip(RoundedCornerShape(7.dp)),
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    categories.value.forEach { selectionOption ->
                        DropdownMenuItem(
                            modifier = Modifier
                                .height(22.dp)
                                .clip(RoundedCornerShape(7.dp)),
                            onClick = {
                                onCategorySelected(selectionOption)
                                selectedOptionText = selectionOption
                                expanded = false
                            }
                        ) {
                            ExcludeFontPaddingText(
                                text = selectionOption,
                                modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically),
                                color = Black,
                                fontSize = 9.sp,
                                fontFamily = notoSansFamily,
                                fontWeight = FontWeight.Normal,
                            )
                        }
                    }
                }
            }
        }
    }
}

