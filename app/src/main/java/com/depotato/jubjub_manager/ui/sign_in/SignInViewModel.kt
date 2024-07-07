package com.depotato.jubjub_manager.ui.sign_in

import androidx.lifecycle.viewModelScope
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.domain.auth.login_hisotry.CheckLoginHistoryUseCase
import com.depotato.jubjub_manager.domain.auth.sign_in.SignInResult
import com.depotato.jubjub_manager.domain.auth.sign_in.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class SignInUiState(
    val userId: String = "",
    val userPw: String = ""
)

@HiltViewModel
class SignInViewModel @Inject constructor (
    private val checkLoginHistoryUseCase: CheckLoginHistoryUseCase,
    private val signInUseCase: SignInUseCase
) : BaseViewModel("SignInViewModel") {

    private val _signInUiState = MutableStateFlow(SignInUiState())
    val signInUiState = _signInUiState.asStateFlow()

    private var _signInComplete = MutableSharedFlow<Unit>()
    val signInComplete = _signInComplete.asSharedFlow()

    fun updateUserId(value: String) = _signInUiState.update { it.copy(userId = value) }
    fun updateUserPw(value: String) = _signInUiState.update { it.copy(userPw = value) }

    fun signIn() {
        if(isInputValid()){
            viewModelScope.launch {
                try {
                    signInUseCase.invoke(
                        signInUiState.value.userId,
                        signInUiState.value.userPw
                    ).collect {
                        when(it){
                            is SignInResult.Success -> {
                                _signInComplete.emit(Unit)
                            }
                            is SignInResult.Failure -> {
                                emitToastMessage(it.errorMessage)
                            }
                        }
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                    emitToastMessage(e.localizedMessage)
                }
            }
        }
    }

    private fun isInputValid(): Boolean {
        return if (signInUiState.value.userId.isBlank()) {
            invalidInput(R.string.invalid_input_id_blank)
        } else if (signInUiState.value.userPw.isBlank()) {
            invalidInput(R.string.invalid_input_pw_blank)
        } else true
    }

    private fun invalidInput(msg: Int): Boolean {
        emitToastMessage(msg)
        return false
    }

    fun checkLoginHistoryExist() {
        with(checkLoginHistoryUseCase()){
            if(isExist){
                updateUserId(userId)
                updateUserPw(userPw)
                signIn()
            }
        }
    }
}