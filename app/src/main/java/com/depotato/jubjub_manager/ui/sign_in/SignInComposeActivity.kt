package com.depotato.jubjub_manager.ui.sign_in

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.ui.main.MainComposeActivity
import com.depotato.jubjub_manager.ui.text.ExcludeFontPaddingText
import com.depotato.jubjub_manager.ui.text.TextParams
import com.depotato.jubjub_manager.ui.text.notoSansFamily
import com.depotato.jubjub_manager.ui.theme.Blue
import com.depotato.jubjub_manager.ui.theme.HintGray
import com.depotato.jubjub_manager.ui.theme.JubJub_ManagerTheme
import com.depotato.jubjub_manager.ui.theme.NoticeGray
import com.depotato.jubjub_manager.ui.theme.White
import com.depotato.jubjub_manager.view.sign_in.SignInViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SignInComposeActivity : ComponentActivity() {

    val viewModel: SignInViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignInScreen(
                onSignInButtonClick = { viewModel.signIn() },
                viewModel = viewModel
            )
        }
        initFlowCollector()
    }


    private fun initFlowCollector() {
        collectWhenStarted(viewModel.signInComplete) {
            openMain()
        }
        collectWhenStarted(viewModel.toastMessage) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun openMain() {
        startActivity(
            Intent(this, MainComposeActivity::class.java)
        )
        finish()
    }

    inline fun <reified T> LifecycleOwner.collectWhenStarted(
        flow: Flow<T>, // 제네릭 타입으로 변경
        noinline collect: suspend (T) -> Unit // 타입 변경
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect(collect)
            }
        }
    }

    @Preview
    @Composable
    fun SignInScreenPreview() {
        SignInScreen(
            {}, viewModel
        )
    }
}

@Composable
fun SignInScreen(
    onSignInButtonClick: () -> Unit,
    viewModel: SignInViewModel
) {
    JubJub_ManagerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column {
                SignInJubJubLogo(modifier = Modifier.padding(top = 53.dp))

                Column(
                    modifier = Modifier.padding(
                        top = 95.dp,
                        start = 62.5.dp,
                        end = 62.5.dp
                    )
                ) {
                    SignInTitle()
                    Spacer(modifier = Modifier.padding(top = 32.dp))

                    SignInInputBox(
                        label = stringResource(id = R.string.id_label), placeHolder = stringResource(id = R.string.enter_id),
                        value = viewModel.userId,
                        onValueChanged = { value -> viewModel.updateUserId(value) }
                    )
                    Spacer(modifier = Modifier.padding(top = 23.dp))
                    SignInInputBox(
                        label = stringResource(id = R.string.pw_label), placeHolder = stringResource(id = R.string.enter_password),
                        value = viewModel.userPw,
                        valueVisible = false,
                        onValueChanged = { value -> viewModel.updateUserPw(value) }
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
    value: StateFlow<String>,
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
    value: StateFlow<String>,
    onValueChanged: (String) -> Unit,
    labelParams: TextParams,
    placeHolderParams: TextParams,
    textFieldParams: TextParams,
    valueVisible: Boolean = true,
) {
    val state = value.collectAsState(initial = "")

    BasicTextField(
        value = state.value,
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
                    if (state.value.isBlank()) {
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
