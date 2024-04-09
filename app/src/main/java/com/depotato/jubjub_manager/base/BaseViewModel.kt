package com.depotato.jubjub_manager.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.depotato.jubjub_manager.function_module.SingleEventLiveData

open class BaseViewModel(private val className: String): ViewModel() {

    protected val _toastMessage = SingleEventLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    fun showLog(msg: String) = Log.d("TestLog_$className", msg)

}