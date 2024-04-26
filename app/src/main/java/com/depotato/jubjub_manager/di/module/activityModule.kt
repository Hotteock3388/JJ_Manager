package com.depotato.jubjub_manager.di.module

import com.depotato.jubjub_manager.view.equipment_list.EquipmentListViewModel
import com.depotato.jubjub_manager.view.main.MainActivityViewModel
import com.depotato.jubjub_manager.view.modify_equipment.add.AddEquipmentViewModel
import com.depotato.jubjub_manager.view.modify_equipment.edit.EditEquipmentViewModel
import com.depotato.jubjub_manager.view.my_page.MyPageViewModel
import com.depotato.jubjub_manager.view.sign_in.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val activityModule = module {

    viewModel { AddEquipmentViewModel() }
    viewModel { MyPageViewModel(get()) }
    viewModel { SignInViewModel(get(), get()) }
    viewModel { EquipmentListViewModel() }
    viewModel { EditEquipmentViewModel() }
    viewModel { MainActivityViewModel() }

}