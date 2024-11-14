package com.nssus.ihandy.ui.yardentry.coildetlls

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.nssus.ihandy.model.yardentry.YardEntryAction
import com.nssus.ihandy.model.yardentry.YardEntryNavigateType
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.yardentry.YardEntryScreen
import com.nssus.ihandy.ui.yardentry.viewmodel.YardEntryViewModel

@Composable
fun YardEntryCoilDetlLsRoute(
    navController: NavController,
    yardEntryVm: YardEntryViewModel
) {
    val uiYardEntrySt by yardEntryVm.yardEntryUISt

    when {
        uiYardEntrySt.isLoading -> CustomLoading()
        uiYardEntrySt.isSuccess -> {
            when (uiYardEntrySt.navigateType) {
                YardEntryNavigateType.BACK_TO_YARD_ENTRY_MAIN -> {
                    navController.popBackStack()
//                    yardEntryVm.initNavigateData()
                    yardEntryVm.action(YardEntryAction.InitNavigateData) //
                }
                else -> Unit
            }
        }
        uiYardEntrySt.isError -> {}
    }

    YardEntryCoilDetlLsScreen(
        uiYardEntrySt = uiYardEntrySt,
        onAction = yardEntryVm::action
    )
}