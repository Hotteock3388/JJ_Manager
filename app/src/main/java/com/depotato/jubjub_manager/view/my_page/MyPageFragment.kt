package com.depotato.jubjub_manager.view.my_page

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.base.BaseFragment
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.databinding.FragmentMyPageBinding
import com.depotato.jubjub_manager.view.add_equipment.AddEquipmentActivity
import com.depotato.jubjub_manager.view.signin.SignInActivity
import org.koin.android.ext.android.inject

class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, MyPageViewModel>(R.layout.fragment_my_page, "") {

    override val viewModel: MyPageViewModel by inject()

    override fun initLiveData() {

        viewModel.logOutComplete.observe(this){
            with(requireActivity()){
                startActivity(Intent(
                    requireContext(), SignInActivity::class.java)
                )
                requireActivity().finish()
            }
        }
    }

    fun openAddEquipmentActivity() {
        startActivity(
            Intent(requireContext(), AddEquipmentActivity::class.java)
        )
    }


}