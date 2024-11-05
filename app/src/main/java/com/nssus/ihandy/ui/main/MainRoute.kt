package com.nssus.ihandy.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.main.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainRoute(
    navController: NavController
) {
    val mainVm = koinViewModel<MainViewModel>()

    val uiMainSt by mainVm.mainUISt

    LaunchedEffect(Unit) {
        mainVm.getUserRole()
    }

    when {
        uiMainSt.isLoading -> CustomLoading()
//        uiMainSt.isSuccess -> {
//            when (uiMainSt.navigateType) {
//                MainNavigateType.GO_TO_HOME -> { //
//                    navController.navigate(GraphConstant.HOME) {
//                        popUpTo(navController.graph.startDestinationId) {
//                            inclusive = true
//                        }
//                        launchSingleTop = true
//                    }
//                    mainVm.initNavigateData()
//                }
//                else -> Unit
//            }
//        }
        uiMainSt.isError -> {

        }
    }

    MainScreen(
        onLogoutIconClick = mainVm::logout
    )
}