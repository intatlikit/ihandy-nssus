package com.nssus.ihandy.ui.yardentry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.nssus.ihandy.model.yardentry.YardEntryAction
import com.nssus.ihandy.model.yardentry.YardEntryNavigateType
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.basecomposable.WarningDialog
import com.nssus.ihandy.ui.yardentry.viewmodel.YardEntryViewModel
import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.nssus.ihandy.R
import com.nssus.ihandy.data.util.AppUtil.getCountDownTimer
import com.nssus.ihandy.model.yardentry.YardEntryDialogAction
import com.nssus.ihandy.ui.basecomposable.FailWithDescriptionDialog

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
                    yardEntryVm.action(YardEntryAction.InitNavigateData)
                }
                YardEntryNavigateType.DISPLAY_BUTTON_DIALOG -> {
                    WarningDialog(
                        message = uiYardEntrySt.successMsg ?: "-",
                        onLeftDialogButtonClick = {},
                        onRightDialogButtonClick = {
//                            yardEntryVm.action(YardEntryAction.ClearAllValueButton)
                            yardEntryVm.actionFromDialogWith(YardEntryDialogAction.ClearAllValue)
                        }
                    )
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
                YardEntryNavigateType.GO_TO_COIL_DETAIL_LS -> {
                    navController.navigate(com.nssus.ihandy.navigation.YardEntryScreen.CoilDetlLsYardEntryScreen.route) // com.nssus.ihandy.navigation.YardEntryScreen ถ้าใช้ชื่ออื่นจะไม่ต้องอิมพอทยาว
                    yardEntryVm.action(YardEntryAction.InitNavigateData)
                }
                else -> Unit
            }
        }
        uiYardEntrySt.isError -> {
            val errorMsg = uiYardEntrySt.errorBody?.errorMsg

            FailWithDescriptionDialog(
                description = if (errorMsg.isNullOrEmpty()) stringResource(id = uiYardEntrySt.errorBody?.yardEntryErrorType?.errorMsgId ?: R.string.dash_string)
                else errorMsg,
                onCloseDialog = { yardEntryVm.actionFromDialogWith(YardEntryDialogAction.ClickLeftDialogButton) }
            )
        }
    }

    YardEntryScreen(
        uiYardEntrySt = uiYardEntrySt,
        onAction = yardEntryVm::action
    )
}