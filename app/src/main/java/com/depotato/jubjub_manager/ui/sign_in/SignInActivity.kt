package com.depotato.jubjub_manager.ui.sign_in

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depotato.jubjub_manager.ui.main.MainComposeActivity
import kotlinx.coroutines.flow.Flow
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
        viewModel.checkLoginHistoryExist()
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
