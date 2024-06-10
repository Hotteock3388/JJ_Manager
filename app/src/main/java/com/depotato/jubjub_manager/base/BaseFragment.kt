//package com.depotato.jubjub_manager.base
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.annotation.LayoutRes
//import androidx.databinding.DataBindingUtil
//import androidx.databinding.ViewDataBinding
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.LifecycleOwner
//import androidx.lifecycle.lifecycleScope
//import androidx.lifecycle.repeatOnLifecycle
//import com.depotato.jubjub_manager.BR
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.launch
//
//abstract class BaseFragment<B : ViewDataBinding, VM: BaseViewModel>(
//    @LayoutRes
//    private val layoutResId: Int,
//    private val className: String
//) : Fragment() {
//
//    lateinit var binding: B
//
//    abstract val viewModel: VM
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        with(binding) {
//            try{
//                setVariable(BR.fr, this@BaseFragment)
//                setVariable(BR.vm, viewModel)
//            } catch (e: Exception){
//                e.printStackTrace()
//            }
//            lifecycleOwner = viewLifecycleOwner
//        }
//
//        init()
//        initListener()
//        initFlowCollector()
//        observeToastMessage()
//    }
//
//    inline fun <reified T> LifecycleOwner.collectWhenStarted(
//        flow: Flow<T>, // 제네릭 타입으로 변경
//        noinline collect: suspend (T) -> Unit // 타입 변경
//    ) {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                flow.collect(collect)
//            }
//        }
//    }
//
//    open fun init() {}
//    open fun initFlowCollector() {}
//    open fun initListener() {}
//
//    fun showToast(msg: String) = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
//    fun showLog(msg: String) = Log.d("TestLog_$className", msg)
//
//    // viewModel의 toastMessage를 관찰
//    private fun observeToastMessage(){
//        collectWhenStarted(viewModel.toastMessage){
//            showToast(it)
//        }
//    }
//}
