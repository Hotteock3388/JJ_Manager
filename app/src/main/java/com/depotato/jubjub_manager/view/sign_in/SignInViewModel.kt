package com.depotato.jubjub_manager.view.sign_in

import androidx.lifecycle.MutableLiveData
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.domain.auth.CheckLoginHistoryUseCase
import com.depotato.jubjub_manager.domain.auth.SignInUseCase
import com.depotato.jubjub_manager.entity.dataclass.response.SignInResult
import com.depotato.jubjub_manager.function_module.SingleEventLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignInViewModel(
    private val checkLoginHistoryUseCase: CheckLoginHistoryUseCase,
    private val signInUseCase: SignInUseCase
) : BaseViewModel("SignInViewModel") {

    val userId = MutableLiveData<String>("")
    val userPw = MutableLiveData<String>("")

    private val _signInComplete = SingleEventLiveData<Unit>()
    val signInComplete: SingleEventLiveData<Unit> = _signInComplete

    fun signIn() {
        if (isInputValid()) {
            addDisposable(
                signInUseCase(
                    userId.value.toString(),
                    userPw.value.toString()
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        when(it){
                            is SignInResult.Success -> {
                                _signInComplete.value = Unit
                            }
                            is SignInResult.Failure -> {
                                _toastMessage.value = it.errorMessage
                            }
                        }
                    }, {
                        it.printStackTrace()
                    })
            )
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