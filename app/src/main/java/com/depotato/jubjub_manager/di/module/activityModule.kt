package com.depotato.jubjub_manager.di.module

import com.depotato.jubjub_manager.ui.modify_equipment.edit_equipment.EditEquipmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val activityModule = module {

//    viewModel { AddEquipmentViewModel(get(), get()) }
//    viewModel { MyPageViewModel(get()) }
//    viewModel { SignInViewModel(get(), get()) }
//    viewModel { EquipmentListViewModel(get()) }
    viewModel { EditEquipmentViewModel(get(), get()) }
//    viewModel { MainActivityViewModel() }

}