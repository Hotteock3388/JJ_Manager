package com.depotato.jubjub_manager.ui.sign_in

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.ui.text.ExcludeFontPaddingText
import com.depotato.jubjub_manager.ui.text.TextParams
import com.depotato.jubjub_manager.ui.text.notoSansFamily
import com.depotato.jubjub_manager.ui.theme.Blue
import com.depotato.jubjub_manager.ui.theme.HintGray
import com.depotato.jubjub_manager.ui.theme.JubJub_ManagerTheme
import com.depotato.jubjub_manager.ui.theme.NoticeGray
import com.depotato.jubjub_manager.ui.theme.White
import org.koin.androidx.compose.koinViewModel



@Preview
@Composable
fun SignInScreenPreView(){ SignInScreen({}, SignInUiState(), {}, {})
}

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = koinViewModel()
){
    val signInUiState by viewModel.signInUiState.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )

    LaunchedEffect(viewModel) {
        viewModel.checkLoginHistoryExist()
    }

    SignInScreen(
        onSignInButtonClick = { viewModel.signIn() },
        signInUiState,
        onUserIdInputBoxChanged = { value -> viewModel.updateUserId(value) },
        onUserPwInputBoxChanged = { value -> viewModel.updateUserPw(value) },
    )
}

@Composable
fun SignInScreen(
    onSignInButtonClick: () -> Unit = {},
    signInUiState : SignInUiState = SignInUiState(),
    onUserIdInputBoxChanged: (String) -> Unit = {},
    onUserPwInputBoxChanged: (String) -> Unit = {},
) {
    JubJub_ManagerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column {
                SignInJubJubLogo(modifier = Modifier.padding(top = 53.dp))

                Column(
                    modifier = Modifier.padding(top = 95.dp, start = 62.5.dp, end = 62.5.dp)
                ) {
                    SignInTitle()
                    Spacer(modifier = Modifier.padding(top = 32.dp))
                    SignInInputBox(
                        label = stringResource(id = R.string.id_label), placeHolder = stringResource(id = R.string.enter_id),
                        value = signInUiState.userId,
                        onValueChanged = onUserIdInputBoxChanged
                    )
                    Spacer(modifier = Modifier.padding(top = 23.dp))
                    SignInInputBox(
                        label = stringResource(id = R.string.pw_label), placeHolder = stringResource(id = R.string.enter_password),
                        value = signInUiState.userPw,
                        valueVisible = false,
                        onValueChanged = onUserPwInputBoxChanged
                    )
                    Spacer(modifier = Modifier.padding(top = 9.dp))
                    PasswordMissNotice()
                    Spacer(modifier = Modifier.padding(top = 250.dp))
                    SignInButton(onSignInButtonClick = onSignInButtonClick)
                }
            }
        }
    }
}

@Composable
fun SignInJubJubLogo(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(37.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_login_title),
            contentDescription = "Login Title Logo"
        )
    }
}

@Composable
fun PasswordMissNotice() {
    ExcludeFontPaddingText(
        text = stringResource(id = R.string.password_miss_notice),
        fontFamily = notoSansFamily,
        fontWeight = FontWeight.Medium,
        color = NoticeGray,
        fontSize = 10.sp
    )
}

@Composable
fun SignInTitle(
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier) {
        ExcludeFontPaddingText(
            text = stringResource(id = R.string.sign_in),
            fontFamily = notoSansFamily,
            fontWeight = FontWeight.Light,
            fontSize = 22.sp,
        )
    }
}

@Composable
fun SignInButton(
    modifier: Modifier = Modifier,
    onSignInButtonClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue,
        ),
        onClick = {
            onSignInButtonClick()
        }) {
        Text(
            text = "로그인",
            color = White
        )
    }
}

@Composable
fun SignInInputBox(
    modifier: Modifier = Modifier,
    value: String,
    valueVisible: Boolean = true,
    label: String,
    placeHolder: String,
    onValueChanged: (String) -> Unit,
) {

    MyInputBox(
        modifier = modifier.padding(top = 6.dp, start = 2.dp, bottom = 9.dp),
        value = value,
        valueVisible = valueVisible,
        onValueChanged = onValueChanged,
        labelParams = TextParams(
            text = label,
            size = 12.sp,
            textColor = Blue,
            fontFamily = notoSansFamily,
            fontWeight = FontWeight.Medium
        ),
        placeHolderParams = TextParams(
            text = placeHolder,
            size = 12.sp,
            textColor = HintGray,
            fontFamily = notoSansFamily,
            fontWeight = FontWeight.Medium
        ),
        textFieldParams = TextParams(
            size = 12.sp,
            fontFamily = notoSansFamily,
            fontWeight = FontWeight.Medium
        )
    )
}

@Composable
fun MyInputBox(
    modifier: Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    labelParams: TextParams,
    placeHolderParams: TextParams,
    textFieldParams: TextParams,
    valueVisible: Boolean = true
) {
//    val state = value.collectAsState(initial = "")

    BasicTextField(
        value = value,
        visualTransformation = if(valueVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                    if (value.isBlank()) {
                        Box(modifier = modifier) {
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
                        Box(modifier = modifier) {
                            innerTextField()
                        }
                    }
                } else {
                    //TextField
                    Box(modifier = modifier) {
                        innerTextField()
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.5.dp)
                        .background(Blue)
                )
            }
        }
    )
}
