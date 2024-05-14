package com.depotato.jubjub_manager.base

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depotato.jubjub_manager.BR
import kotlinx.coroutines.launch


abstract class BaseActivity <B: ViewDataBinding, VM: BaseViewModel>(
    @LayoutRes
    private val layoutResId: Int,
    private val className: String
): AppCompatActivity() {

    lateinit var binding: B

    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)

        // data binding에 ac 변수 등록
        with(binding) {
            try {
                setVariable(BR.ac, this@BaseActivity)
                setVariable(BR.vm, viewModel)
            } catch (e: Exception){
                e.printStackTrace()
            }
            lifecycleOwner = this@BaseActivity
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                initFlowCollector()
            }
        }
        initListener()
        init()
        observeToastMessage()
    }

    open fun init(){}
    open suspend fun initFlowCollector(){}
    open fun initListener(){}

    // 토스트 메시지 띄우기
    fun showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    // 디버깅 로그 띄우기
    fun showLog(msg: String) = Log.d("TestLog_$className", msg)

    // viewModel의 toastMessage를 관찰
    private fun observeToastMessage(){
        viewModel.toastMessage.observe(this){
            showToast(it)
        }
    }
}