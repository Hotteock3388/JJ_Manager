package com.depotato.jubjub_manager.view.my_page

import com.depotato.jubjub_manager.R
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
                    updateToastMessage(R.string.logout_complete)
                    _logOutComplete.value = Unit
                }
                is CommonResult.Failure -> {
                    updateToastMessage(it.errorMessage)
                }
            }
        }
    }

}