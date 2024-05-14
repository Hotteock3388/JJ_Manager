package com.depotato.jubjub_manager.view.sign_in

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.domain.auth.login_hisotry.CheckLoginHistoryUseCase
import com.depotato.jubjub_manager.domain.auth.sign_in.SignInResult
import com.depotato.jubjub_manager.domain.auth.sign_in.SignInUseCase
import com.depotato.jubjub_manager.function_module.SingleEventLiveData
import kotlinx.coroutines.launch

class SignInViewModel(
    private val checkLoginHistoryUseCase: CheckLoginHistoryUseCase,
    private val signInUseCase: SignInUseCase
) : BaseViewModel("SignInViewModel") {

    val userId = MutableLiveData<String>("")
    val userPw = MutableLiveData<String>("")

    private val _signInComplete = SingleEventLiveData<Unit>()
    val signInComplete: SingleEventLiveData<Unit> = _signInComplete

    fun signIn() {
        if(isInputValid()){
            viewModelScope.launch {
                try {
                    signInUseCase.invoke(
                        userId.value.toString(),
                        userPw.value.toString()
                    ).collect {
                        when(it){
                            is SignInResult.Success -> {
                                _signInComplete.value = Unit
                            }
                            is SignInResult.Failure -> {
                                _toastMessage.value = it.errorMessage
                            }
                        }
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                    _toastMessage.value = e.message
                }
            }
        }
    }

    private fun isInputValid(): Boolean {
        return if (userId.value!!.isBlank()) {
            invalidInput("아이디를 입력해주세요.")
        } else if (userPw.value!!.isBlank()) {
            invalidInput("비밀번호를 입력해주세요.")
        } else true
    }

    private fun invalidInput(msg: String): Boolean {
        _toastMessage.value = msg
        return false
    }

    fun checkLoginHistoryExist() {
        with(checkLoginHistoryUseCase()){
            if(isExist){
                this@SignInViewModel.userId.value = userId
                this@SignInViewModel.userPw.value = userPw
                signIn()
            }
        }
    }
}