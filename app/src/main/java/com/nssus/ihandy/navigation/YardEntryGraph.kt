package com.nssus.ihandy.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.ui.yardentry.YardEntryRoute
import com.nssus.ihandy.ui.yardentry.coildetlls.YardEntryCoilDetlLsRoute
import com.nssus.ihandy.ui.yardentry.viewmodel.YardEntryViewModel

sealed class YardEntryScreen(val route: String) {
    object MainFillYardEntryScreen : YardEntryScreen("main_fill_yard_entry_screen")
    object CoilDetlLsYardEntryScreen : YardEntryScreen("coil_detl_ls_yard_entry_screen")
}

fun NavGraphBuilder.yardEntryGraph(
    navController: NavController,
    yardEntryVm: YardEntryViewModel
) {
    navigation(
        startDestination = YardEntryScreen.MainFillYardEntryScreen.route,
        route = GraphConstant.YARD_ENTRY
    ) {
        composable(YardEntryScreen.MainFillYardEntryScreen.route) {
            YardEntryRoute(navController, yardEntryVm)
        }
        composable(YardEntryScreen.CoilDetlLsYardEntryScreen.route) {
            YardEntryCoilDetlLsRoute(navController, yardEntryVm)
        }
    }
}