package com.depotato.jubjub_manager.view.sign_in

import android.content.Intent
import android.widget.Toast
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.base.BaseActivity
import com.depotato.jubjub_manager.databinding.ActivitySignInBinding
import com.depotato.jubjub_manager.view.main.MainActivity
import org.koin.android.ext.android.inject

class SignInActivity : BaseActivity<ActivitySignInBinding, SignInViewModel>(R.layout.activity_sign_in, "SignInActivity") {

    override val viewModel: SignInViewModel by inject()

    // 마지막으로 뒤로가기 누른 시각
    private var backKeyPressedTime: Long = 0

    override fun init() {
        viewModel.checkLoginHistoryExist()
    }

    override fun initLiveData() {
        viewModel.saveUserDataComplete.observe(this){
            openMain()
        }
    }

    private fun openMain(){
        startActivity(
            Intent(this, MainActivity::class.java)
        )
        finish()
    }


    //뒤로가기 버튼 눌렀을 때
    override fun onBackPressed() {
        //1번 눌렀을 때
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(applicationContext, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
        //2초 안에 2번 눌렀을 때 종료
        else if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            super.onBackPressed()
        }
    }

}