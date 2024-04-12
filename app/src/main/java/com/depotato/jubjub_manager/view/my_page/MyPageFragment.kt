package com.depotato.jubjub_manager.view.my_page

import android.content.Intent
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.base.BaseFragment
import com.depotato.jubjub_manager.databinding.FragmentMyPageBinding
import com.depotato.jubjub_manager.view.modify_equipment.add.AddEquipmentActivity
import com.depotato.jubjub_manager.view.sign_in.SignInActivity
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