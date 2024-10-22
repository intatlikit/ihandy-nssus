package com.nssus.ihandy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.ui.home.viewmodel.HomeViewModel
import com.nssus.ihandy.ui.inventorytaking.viewmodel.InvTakingViewModel
import com.nssus.ihandy.ui.yardentry.viewmodel.YardEntryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainGraph(
    navController: NavController
) {
    val homeVm = koinViewModel<HomeViewModel>()

    val yardEntryVm = koinViewModel<YardEntryViewModel>()
    val invTakingVm = koinViewModel<InvTakingViewModel>()

    NavHost(
        navController = navController as NavHostController,
        route = GraphConstant.MAIN,
        startDestination = GraphConstant.HOME
    ) {
        homeGraph(navController, homeVm)

        yardEntryGraph(navController, yardEntryVm)
        invTakingGraph(navController, invTakingVm)
    }
}