package com.depotato.jubjub_manager.view.my_page

import androidx.lifecycle.LiveData
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.data.local.SharedPref
import com.depotato.jubjub_manager.entity.singleton.Constants
import com.depotato.jubjub_manager.function_module.SingleEventLiveData

class MyPageViewModel(private val sharedPref: SharedPref): BaseViewModel("MyPageViewModel") {
    
    private val _logOutComplete = SingleEventLiveData<Unit>()
    val logOutComplete = _logOutComplete

    fun logOut(){
        with(sharedPref){
            removeData(Constants.USER_ID)
            removeData(Constants.USER_PW)
        }
        _toastMessage.value = "로그아웃 완료"
        _logOutComplete.value = Unit
    }

}