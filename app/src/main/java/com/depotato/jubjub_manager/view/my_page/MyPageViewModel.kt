package com.depotato.jubjub_manager.view.my_page

import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.domain.auth.login_data.DeleteAuthDataUseCase
import com.depotato.jubjub_manager.domain.equipment.CommonResult
import com.depotato.jubjub_manager.function_module.SingleEventLiveData

class MyPageViewModel(
    private val deleteAuthDataUseCase: DeleteAuthDataUseCase
): BaseViewModel("MyPageViewModel") {
    
    private val _logOutComplete = SingleEventLiveData<Unit>()
    val logOutComplete = _logOutComplete

    fun logOut(){
        deleteAuthDataUseCase().let {
            when(it){
                is CommonResult.Success -> {
                    _toastMessage.value = "로그아웃 완료"
                    _logOutComplete.value = Unit
                }
                is CommonResult.Failure -> {
                    _toastMessage.value = it.errorMessage
                }
            }
        }
    }

}