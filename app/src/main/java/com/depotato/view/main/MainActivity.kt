package com.depotato.view.main


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.databinding.ActivityMainBinding
import com.depotato.view.manage_equipment.ManageEquipmentFragment
import com.depotato.view.my_page.MyPageFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.ac = this
        binding.lifecycleOwner = this


        binding.menuHome.isSelected = true
    }

    fun onHomeClick(){
        changeSelectedIcon(binding.menuHome, binding.menuMyPage)
        setFragment(ManageEquipmentFragment())
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