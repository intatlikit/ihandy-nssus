package com.nssus.ihandy.ui.yardentry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.nssus.ihandy.model.yardentry.YardEntryAction
import com.nssus.ihandy.model.yardentry.YardEntryNavigateType
import com.nssus.ihandy.ui.basecomposable.BaseTitleMsgWithButtonDialog
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.yardentry.viewmodel.YardEntryViewModel

@Composable
fun YardEntryRoute(
    navController: NavController,
    yardEntryVm: YardEntryViewModel
) {
    val uiYardEntrySt by yardEntryVm.yardEntryUISt

    LaunchedEffect(Unit) {
        yardEntryVm.initData()
    }

    when {
        uiYardEntrySt.isLoading -> CustomLoading()
        uiYardEntrySt.isSuccess -> {
            when (uiYardEntrySt.navigateType) {
                YardEntryNavigateType.GO_BACK -> {
                    navController.popBackStack()
//                    yardEntryVm.initNavigateData()
                    yardEntryVm.action(YardEntryAction.InitNavigateData) //
                }
                YardEntryNavigateType.DISPLAY_BUTTON_DIALOG -> {
                    BaseTitleMsgWithButtonDialog(
                        description = uiYardEntrySt.successMsg,
                        onCloseDialog = { yardEntryVm.action(YardEntryAction.InitNavigateData) },
                        onLeftButtonClick = { yardEntryVm.action(YardEntryAction.ClickContinueDialogButton) },
                        onRightButtonClick = { yardEntryVm.action(YardEntryAction.ClearAllValueButton) }
                    )
                }
                else -> Unit
            }
        }
        uiYardEntrySt.isError -> {}
    }

    YardEntryScreen(
        uiYardEntrySt = uiYardEntrySt,
        onAction = yardEntryVm::action
    )
}