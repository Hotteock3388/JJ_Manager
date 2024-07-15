package com.depotato.presentation.ui.sign_in

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.depotato.presentation.base.BaseActivity
import com.depotato.presentation.ui.main.MainComposeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity<SignInViewModel>("SignInActivity") {

//    override val viewModel: SignInViewModel by inject()

    override val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBackPressedCallback()

        setContent {
            SignInScreen(
                viewModel = viewModel
            )
        }
    }

    override fun initFlowCollector() {
        collectWhenStarted(viewModel.signInComplete) {
            openMain()
        }
    }

    private fun openMain() {
        startActivity(
            Intent(this, MainComposeActivity::class.java)
        )
        finish()
    }

    @Preview
    @Composable
    fun SignInScreenPreview() {
        SignInScreen(viewModel)
    }
}