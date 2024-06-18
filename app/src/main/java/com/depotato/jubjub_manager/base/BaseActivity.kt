package com.depotato.jubjub_manager.base

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


abstract class BaseActivity <VM: BaseViewModel>(
    private val className: String
): ComponentActivity() {

    abstract val viewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFlowCollector()
        initListener()
        init()
        observeToastMessage()
    }

    open fun init(){}
    open fun initFlowCollector(){}
    open fun initListener(){}

    inline fun <reified T> LifecycleOwner.collectWhenStarted(
        flow: Flow<T>, // 제네릭 타입으로 변경
        noinline collect: suspend (T) -> Unit // 타입 변경
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect(collect)
            }
        }
    }

    // 토스트 메시지 띄우기
    fun showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    // 디버깅 로그 띄우기
    fun showLog(msg: String) = Log.d("TestLog_$className", msg)

    // viewModel의 toastMessage를 관찰
    private fun observeToastMessage(){
        collectWhenStarted(viewModel.toastMessage){
            showToast(it)
        }
    }

}