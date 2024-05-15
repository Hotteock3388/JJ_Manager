package com.depotato.jubjub_manager.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel(private val className: String): ViewModel() {

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun emitToastMessage(msg: String) = viewModelScope.launch {
        _toastMessage.emit(msg)
    }

    fun showLog(msg: String) = Log.d("TestLog_$className", msg)
}