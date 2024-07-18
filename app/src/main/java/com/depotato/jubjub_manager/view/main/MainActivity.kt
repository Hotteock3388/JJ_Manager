package com.depotato.jubjub_manager.view.main


import android.view.View
import androidx.fragment.app.Fragment
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.base.BaseActivity
import com.depotato.jubjub_manager.databinding.ActivityMainBinding
import com.depotato.jubjub_manager.view.equipment_list.EquipmentListFragment
import com.depotato.jubjub_manager.view.my_page.MyPageFragment
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>(R.layout.activity_main, "MainActivity") {

    override val viewModel: MainActivityViewModel by inject()

    override fun init() {
        addBackPressedCallback()
        binding.menuHome.isSelected = true
    }

    fun onHomeClick(){
        changeSelectedIcon(binding.menuHome, binding.menuMyPage)
        setFragment(EquipmentListFragment())
    }

    fun onMyPageClick(){
        changeSelectedIcon(binding.menuMyPage, binding.menuHome)
        setFragment(MyPageFragment())
    }

    private fun changeSelectedIcon(currentIcon: View, oldIcon: View){
        currentIcon.isSelected = true
        oldIcon.isSelected = false
    }

    private fun setFragment(fr: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainerView.id, fr)
            .commit()
    }
}