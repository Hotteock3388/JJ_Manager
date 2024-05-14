package com.depotato.jubjub_manager.view.sign_in

import androidx.lifecycle.viewModelScope
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.domain.auth.login_hisotry.CheckLoginHistoryUseCase
import com.depotato.jubjub_manager.domain.auth.sign_in.SignInResult
import com.depotato.jubjub_manager.domain.auth.sign_in.SignInUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val checkLoginHistoryUseCase: CheckLoginHistoryUseCase,
    private val signInUseCase: SignInUseCase
) : BaseViewModel("SignInViewModel") {

    val _userId = MutableStateFlow<String>("")
    private val userId = _userId.asStateFlow()

    val _userPw = MutableStateFlow<String>("")
    private val userPw = _userPw.asStateFlow()

    private var _signInComplete = MutableSharedFlow<Unit>()
    val signInComplete = _signInComplete.asSharedFlow()

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
                                _signInComplete.emit(Unit)
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
                this@SignInViewModel._userId.value = userId
                this@SignInViewModel._userPw.value = userPw
                signIn()
            }
        }
    }
}