package com.depotato.jubjub_manager.view.sign_in

import android.content.Intent
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.base.BaseActivity
import com.depotato.jubjub_manager.databinding.ActivitySignInBinding
import com.depotato.jubjub_manager.view.main.MainActivity
import org.koin.android.ext.android.inject

class SignInActivity : BaseActivity<ActivitySignInBinding, SignInViewModel>(R.layout.activity_sign_in, "SignInActivity") {

    override val viewModel: SignInViewModel by inject()

    override fun init() {
        addBackPressedCallback()
        viewModel.checkLoginHistoryExist()
    }

    override fun initLiveData() {
        viewModel.signInComplete.observe(this){
            openMain()
        }
    }

    private fun openMain(){
        startActivity(
            Intent(this, MainActivity::class.java)
        )
        finish()
    }

}