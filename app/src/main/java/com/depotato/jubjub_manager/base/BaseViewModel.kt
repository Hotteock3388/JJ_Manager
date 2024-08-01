package com.depotato.jubjub_manager.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.depotato.jubjub_manager.function_module.SingleEventLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel(private val className: String): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _toastMessage = SingleEventLiveData<Any>()
    val toastMessage: LiveData<Any> = _toastMessage

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun updateToastMessage(msg: String){
        _toastMessage.value = msg
    }
    fun updateToastMessage(msg: Int){
        _toastMessage.value = msg
    }

    fun addDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    fun showLog(msg: String) = Log.d("TestLog_$className", msg)
}