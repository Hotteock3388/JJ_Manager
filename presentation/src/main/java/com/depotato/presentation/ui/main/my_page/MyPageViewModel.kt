package com.depotato.presentation.ui.main.my_page

import androidx.lifecycle.viewModelScope
import com.depotato.domain.auth.log_out.LogoutUseCase
import com.depotato.domain.equipment.CommonResult
import com.depotato.presentation.R
import com.depotato.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor (
    private val logoutUseCase: LogoutUseCase
): BaseViewModel("MyPageViewModel") {
    
    private val _logOutComplete = MutableSharedFlow<Unit>()
    val logOutComplete = _logOutComplete.asSharedFlow()

    fun logOut(){
        when(logoutUseCase()){
            is CommonResult.Success -> {
                emitToastMessage(R.string.menu_logout)
                viewModelScope.launch {
                    _logOutComplete.emit(Unit)
                }
            }

            is CommonResult.Failure -> {
                emitToastMessage(R.string.unknown_error_occurred)
            }

        }
    }
}