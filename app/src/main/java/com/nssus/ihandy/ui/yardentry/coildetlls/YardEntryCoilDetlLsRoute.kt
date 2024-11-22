package com.nssus.ihandy.ui.yardentry.coildetlls

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.nssus.ihandy.R
import com.nssus.ihandy.model.yardentry.YardEntryAction
import com.nssus.ihandy.model.yardentry.YardEntryDialogAction
import com.nssus.ihandy.model.yardentry.YardEntryNavigateType
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.basecomposable.WarningDialog
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
                    yardEntryVm.action(YardEntryAction.InitNavigateData)
                }
                YardEntryNavigateType.DISPLAY_CONFIRM_REMOVE_COIL_DIALOG -> {
                    WarningDialog(
                        message = stringResource(id = R.string.yard_entry_coil_detl_ls_confirm_remove_msg),
                        onLeftDialogButtonClick = {
                            yardEntryVm.actionFromDialogWith(YardEntryDialogAction.ClickConfirmRemoveDialogButton)
                        },
                        onRightDialogButtonClick = {
                            yardEntryVm.actionFromDialogWith(YardEntryDialogAction.ClickCancelRemoveDialogButton)
                        }
                    )
                }
                else -> Unit
            }
        }
    }

    YardEntryCoilDetlLsScreen(
        uiYardEntrySt = uiYardEntrySt,
        onAction = yardEntryVm::action
    )
}