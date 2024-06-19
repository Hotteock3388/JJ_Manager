package com.depotato.jubjub_manager.di.module

import com.depotato.jubjub_manager.ui.main.equipment_list.EquipmentListViewModel
import com.depotato.jubjub_manager.ui.main.MainActivityViewModel
import com.depotato.jubjub_manager.ui.modify_equipment.add_equipment.AddEquipmentViewModel
import com.depotato.jubjub_manager.ui.modify_equipment.edit_equipment.EditEquipmentViewModel
import com.depotato.jubjub_manager.ui.main.my_page.MyPageViewModel
import com.depotato.jubjub_manager.ui.sign_in.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val activityModule = module {

    viewModel { AddEquipmentViewModel(get(), get()) }
    viewModel { MyPageViewModel(get()) }
    viewModel { SignInViewModel(get(), get()) }
    viewModel { EquipmentListViewModel(get()) }
    viewModel { EditEquipmentViewModel(get(), get()) }
    viewModel { MainActivityViewModel() }

}