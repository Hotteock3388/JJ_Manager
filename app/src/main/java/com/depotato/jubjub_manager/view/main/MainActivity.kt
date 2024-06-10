//package com.depotato.jubjub_manager.view.main
//
//
//import android.view.View
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import com.depotato.jubjub_manager.R
//import com.depotato.jubjub_manager.base.BaseActivity
//import com.depotato.jubjub_manager.databinding.ActivityMainBinding
//import org.koin.android.ext.android.inject
//
//class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>(R.layout.activity_main, "MainActivity") {
//
//    override val viewModel: MainActivityViewModel by inject()
//
//    // 마지막으로 뒤로가기 누른 시각
//    private var backKeyPressedTime: Long = 0
//
//    override fun init() {
//        binding.menuHome.isSelected = true
//    }
//
//    fun onHomeClick(){
//        changeSelectedIcon(binding.menuHome, binding.menuMyPage)
//        setFragment(EquipmentListFragment())
//    }
//
//    fun onMyPageClick(){
//        changeSelectedIcon(binding.menuMyPage, binding.menuHome)
//        setFragment(MyPageFragment())
//    }
//
//    private fun changeSelectedIcon(currentIcon: View, oldIcon: View){
//        currentIcon.isSelected = true
//        oldIcon.isSelected = false
//    }
//
//    private fun setFragment(fr: Fragment){
//        supportFragmentManager
//            .beginTransaction()
//            .replace(binding.fragmentContainerView.id, fr)
//            .commit()
//    }
//
//    //뒤로가기 버튼 눌렀을 때
//    override fun onBackPressed() {
//        //1번 눌렀을 때
//        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
//            backKeyPressedTime = System.currentTimeMillis()
//            Toast.makeText(applicationContext, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
//        }
//        //2초 안에 2번 눌렀을 때 종료
//        else if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
//            super.onBackPressed()
//        }
//    }
//
//}