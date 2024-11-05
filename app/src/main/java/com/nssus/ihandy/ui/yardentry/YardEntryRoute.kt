package com.nssus.ihandy.ui.yardentry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.nssus.ihandy.model.yardentry.YardEntryAction
import com.nssus.ihandy.model.yardentry.YardEntryNavigateType
import com.nssus.ihandy.ui.basecomposable.BaseTitleMsgWithButtonDialog
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.basecomposable.ErrorDialog
import com.nssus.ihandy.ui.basecomposable.WarningDialog
import com.nssus.ihandy.ui.yardentry.viewmodel.YardEntryViewModel
import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.nssus.ihandy.data.util.AppUtil.getCountDownTimer

@Composable
fun YardEntryRoute(
    navController: NavController,
    yardEntryVm: YardEntryViewModel
) {
    val uiYardEntrySt by yardEntryVm.yardEntryUISt

    var countDownTimer: CountDownTimer? by remember { mutableStateOf(null) }

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
//                    BaseTitleMsgWithButtonDialog(
//                        description = uiYardEntrySt.successMsg,
//                        onCloseDialog = { yardEntryVm.action(YardEntryAction.InitNavigateData) },
//                        onLeftButtonClick = { yardEntryVm.action(YardEntryAction.ClickContinueDialogButton) },
//                        onRightButtonClick = { yardEntryVm.action(YardEntryAction.ClearAllValueButton) }
//                    )
                    WarningDialog(
                        message = uiYardEntrySt.successMsg ?: "-",
                        onLeftDialogButtonClick = { yardEntryVm.action(YardEntryAction.ClickContinueDialogButton) },
                        onRightDialogButtonClick = { yardEntryVm.action(YardEntryAction.ClearAllValueButton) }
                    )
//                    ErrorDialog(
//                        message = uiYardEntrySt.successMsg ?: "-",
//                        onDialogButtonClick = { yardEntryVm.action(YardEntryAction.ClickContinueDialogButton) }
//                    )
                }
                YardEntryNavigateType.START_COUNTDOWN_TIMER -> {
                    yardEntryVm.action(YardEntryAction.InitNavigateData) //

                    countDownTimer?.cancel()

                    countDownTimer = getCountDownTimer(
                        countDownTimeInSec = uiYardEntrySt.countdownTime,
                        onCountDownFinish = {
                            println("Countdown Finished")
                            countDownTimer?.cancel()

                            yardEntryVm.action(YardEntryAction.CheckGetDataBeforeClearAllValue)
//                            if (uiYardEntrySt.isClickedCallYYRRCCT.not() ||
//                                (uiYardEntrySt.isClickedCallYYRRCCT && uiYardEntrySt.isClickedCallSupplierNo.not()))
//                                yardEntryVm.action(YardEntryAction.ClearAllValueButton)
                        }
                    ).apply { start() }
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