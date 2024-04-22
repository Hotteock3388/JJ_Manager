package com.depotato.jubjub_manager.view.sign_in

import androidx.lifecycle.MutableLiveData
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.data.local.SharedPref
import com.depotato.jubjub_manager.data.remote.retrofit.NetRetrofit
import com.depotato.jubjub_manager.entity.singleton.Constants
import com.depotato.jubjub_manager.function_module.SingleEventLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignInViewModel(private val sharedPref: SharedPref) : BaseViewModel("SignInViewModel") {

    val userId = MutableLiveData<String>("")
    val userPw = MutableLiveData<String>("")

    val saveUserDataComplete = SingleEventLiveData<Unit>()


    fun signIn() {
        if (isInputValid()) {

            addDisposable(
                NetRetrofit.getAuthApi().signIn(
                    userId.value.toString(),
                    userPw.value.toString(),
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({

                        if(it.status == 200){
                            saveUserData()
                        }else{
                            _toastMessage.value = it.message
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

    private fun checkUserAc() {
        if (userId.value.toString() == "1234" && userPw.value.toString() == "1234") {
            saveUserData()
        } else {
            _toastMessage.value = "입력하신 계정 정보가 존재하지 않습니다."
        }
    }

    private fun saveUserData(){
        with(sharedPref){
            saveData(Constants.USER_ID, userId.value!!)
            saveData(Constants.USER_PW, userPw.value!!)
        }

        saveUserDataComplete.value = Unit
    }

    fun checkLoginHistoryExist() {
        with(sharedPref){
            if(isExist(Constants.USER_ID)){
                userId.value = getDataString(Constants.USER_ID)
                userPw.value = getDataString(Constants.USER_PW)
            }
            signIn()
        }

    }
}