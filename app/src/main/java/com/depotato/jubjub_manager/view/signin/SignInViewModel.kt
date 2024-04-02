package com.depotato.jubjub_manager.view.signin

import androidx.lifecycle.MutableLiveData
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.function_module.SingleEventLiveData

class SignInViewModel: BaseViewModel("SignInViewModel") {

    val userId = MutableLiveData<String>("")
    val userPw = MutableLiveData<String>("")

    val loginSuccess = SingleEventLiveData<Unit>()


    fun signIn(){
        if(isInputValid()) {
            checkUserAc()
        }
    }

    private fun isInputValid(): Boolean {
        return if(userId.value!!.isBlank()){
            invalidInput("아이디를 입력해주세요.")
        }else if(userPw.value!!.isBlank()){
            invalidInput("비밀번호를 입력해주세요.")
        }else true
    }

    private fun invalidInput(msg: String): Boolean {
        _toastMessage.value = msg
        return false
    }

    private fun checkUserAc() {
        if(userId.value.toString() == "1234" && userPw.value.toString() == "1234"){
            loginSuccess.value = Unit
        }else{
            _toastMessage.value = "입력하신 계정 정보가 존재하지 않습니다."
        }
    }
}