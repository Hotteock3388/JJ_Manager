package com.depotato.jubjub_manager.view.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.base.BaseActivity
import com.depotato.jubjub_manager.databinding.ActivitySignInBinding
import com.depotato.jubjub_manager.view.main.MainActivity
import org.koin.android.ext.android.inject

class SignInActivity : BaseActivity<ActivitySignInBinding, SignInViewModel>(R.layout.activity_sign_in, "SignInActivity") {

    override val viewModel: SignInViewModel by inject()

    override fun initLiveData() {
        viewModel.loginSuccess.observe(this){
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            finish()
        }
    }
}