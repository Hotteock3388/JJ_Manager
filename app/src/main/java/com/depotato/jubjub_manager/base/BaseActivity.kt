package com.depotato.jubjub_manager.base

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.depotato.jubjub_manager.BR


abstract class BaseActivity <B: ViewDataBinding>(
    @LayoutRes
    private val layoutResId: Int,
    private val className: String
): AppCompatActivity() {

    // 마지막으로 뒤로가기 누른 시각
    private var backKeyPressedTime: Long = 0

    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)

        // data binding에 ac 변수 등록
        with(binding) {
            try {
                setVariable(BR.ac, this@BaseActivity)
            } catch (e: Exception){
                e.printStackTrace()
            }
            lifecycleOwner = this@BaseActivity
        }

        init()
        initLiveData()
        initListener()
    }

    open fun init(){}
    open fun initLiveData(){}
    open fun initListener(){}

    // 토스트 메시지 띄우기
    fun showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    // 디버깅 로그 띄우기
    fun showLog(msg: String) = Log.d("TestLog_$className", msg)

    // viewModel의 toastMessage를 관찰
    fun observeToastMessage(viewModel: BaseViewModel){
        viewModel.toastMessage.observe(this){
            showToast(it)
        }
    }

    //뒤로가기 버튼 눌렀을 때
    override fun onBackPressed() {
        //1번 눌렀을 때
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(applicationContext, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
        //2초 안에 2번 눌렀을 때 종료
        else if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish()
        }
    }
}