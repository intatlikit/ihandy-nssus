package com.nssus.ihandy.di

import com.nssus.ihandy.ui.home.viewmodel.HomeViewModel
import com.nssus.ihandy.ui.inventorytaking.viewmodel.InvTakingViewModel
import com.nssus.ihandy.ui.login.viewmodel.LoginViewModel
import com.nssus.ihandy.ui.main.viewmodel.MainViewModel
import com.nssus.ihandy.ui.yardentry.viewmodel.YardEntryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // ViewModel
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel() } // get()
    viewModel { HomeViewModel() }
    viewModel { YardEntryViewModel() }
    viewModel { InvTakingViewModel() }
}