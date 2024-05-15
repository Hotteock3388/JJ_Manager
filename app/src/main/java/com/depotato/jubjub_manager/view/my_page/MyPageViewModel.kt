package com.depotato.jubjub_manager.view.my_page

import androidx.lifecycle.viewModelScope
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.data.local.SharedPref
import com.depotato.jubjub_manager.entity.singleton.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MyPageViewModel(private val sharedPref: SharedPref): BaseViewModel("MyPageViewModel") {
    
    private val _logOutComplete = MutableSharedFlow<Unit>()
    val logOutComplete = _logOutComplete.asSharedFlow()

    fun logOut(){
        with(sharedPref){
            removeData(Constants.USER_ID)
            removeData(Constants.USER_PW)
        }

        viewModelScope.launch {
            emitToastMessage(Constants.LOGOUT_COMPLETE)
            delay(2000)
            _logOutComplete.emit(Unit)
        }
    }

}