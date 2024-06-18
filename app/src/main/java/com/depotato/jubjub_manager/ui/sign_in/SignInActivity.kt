package com.depotato.jubjub_manager.ui.sign_in

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.depotato.jubjub_manager.base.BaseActivity
import com.depotato.jubjub_manager.ui.main.MainComposeActivity
import org.koin.android.ext.android.inject

class SignInActivity : BaseActivity<SignInViewModel>("SignInActivity") {

    override val viewModel: SignInViewModel by inject()

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
        SignInScreen(
            {}, viewModel
        )
    }
}
