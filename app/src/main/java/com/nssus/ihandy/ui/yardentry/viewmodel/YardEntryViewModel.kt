package com.nssus.ihandy.ui.yardentry.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nssus.ihandy.R
import com.nssus.ihandy.data.extension.getSelectedItem
import com.nssus.ihandy.data.extension.getSelectedItemValue
import com.nssus.ihandy.data.extension.isEqualsMaxLength
import com.nssus.ihandy.data.extension.isErrorTextFieldWith
import com.nssus.ihandy.data.extension.isNull
import com.nssus.ihandy.data.extension.setMatchedItemFrom
import com.nssus.ihandy.data.extension.setSelectItemFrom
import com.nssus.ihandy.data.extension.setSelectedRemoveFrom
import com.nssus.ihandy.data.usecase.HomeUseCase
import com.nssus.ihandy.model.network.NetworkResult
import com.nssus.ihandy.model.ui.DropdownUIModel
import com.nssus.ihandy.model.yardentry.CoilDetailItem
import com.nssus.ihandy.model.yardentry.YardEntryAction
import com.nssus.ihandy.model.yardentry.YardEntryDialogAction
import com.nssus.ihandy.model.yardentry.YardEntryErrorModel
import com.nssus.ihandy.model.yardentry.YardEntryErrorType
import com.nssus.ihandy.model.yardentry.YardEntryNavigateType
import com.nssus.ihandy.model.yardentry.YardEntryUIStateModel
import com.nssus.ihandy.ui.yardentry.constant.YardEntryConstant.MAX_LENGTH_COIL_NO
import com.nssus.ihandy.ui.yardentry.constant.YardEntryConstant.TIME_TO_GET_COIL_NO
import com.nssus.ihandy.ui.yardentry.constant.YardEntryConstant.TIME_TO_GET_TRUCK_NO
import kotlinx.coroutines.launch

class YardEntryViewModel(
    private val homeUc: HomeUseCase
) : ViewModel() {
    private val _yardEntryUISt = mutableStateOf(YardEntryUIStateModel())
    val yardEntryUISt: State<YardEntryUIStateModel> = _yardEntryUISt

    fun initData() {
        if (_yardEntryUISt.value.isAlreadyInitialFeature) return

        _yardEntryUISt.value = YardEntryUIStateModel(isAlreadyInitialFeature = true) /////////
//            .copy(
//            dataLs = getDataLs()
//        )
    }

    fun action(viewAction: YardEntryAction) {
        when (viewAction) {
            is YardEntryAction.GoBack -> {
                _yardEntryUISt.value = YardEntryUIStateModel(isClearAllTextFieldValue = true)
                _yardEntryUISt.value = onYardEntryUIStateSuccess(navigateType = YardEntryNavigateType.GO_BACK)
            }
            is YardEntryAction.TypingCoilNoTextField -> { //
//                onYardEntryUIStateLoading()
//                 _yardEntryUISt.value = _yardEntryUISt.value.copy( // ใช้ค่าเดิมแล้วก้อปปี้แก้บางค่าพอ ไม่ล้างด้วยsuccess
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    coilNo = viewAction.text,
                    isCoilNoTfError = viewAction.text.isErrorTextFieldWith(MAX_LENGTH_COIL_NO),
                    resultIconId = when {
//                        isEqualMaxLength(viewAction.text, MAX_LENGTH_COIL_NO) -> null
                        viewAction.text.isErrorTextFieldWith(MAX_LENGTH_COIL_NO) -> R.drawable.ic_dialog_red_cross
                        viewAction.text.isEqualsMaxLength(MAX_LENGTH_COIL_NO)-> R.drawable.ic_dialog_green_tick
                        else -> null
                    },
                    isClickedCallCoilNo = false
                )
            }
            is YardEntryAction.ClickNextActionCoilTextField -> callCoilApi()
            is YardEntryAction.SetInitFlagGetCoilResp -> {
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    isGetCoilRespSuccess = false
                )
            }
            is YardEntryAction.ClickNextActionYYRRCCTTextField -> callYYRRCCTApi()
            is YardEntryAction.SetInitFlagGetYYRRCCTResp -> {
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    isGetYYRRCCTRespSuccess = false
                )
            }
            is YardEntryAction.ClickNextActionSupplierNoTextField -> callSupplierNoApi()
            is YardEntryAction.SetInitFlagGetSupplierNoResp -> {
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    isGetSupplierNoRespSuccess = false
                )
            }
            is YardEntryAction.TypingYYRRCCTTextField -> { //
//                onYardEntryUIStateLoading()
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    yyrrcct = viewAction.text,
                    isClickedCallYYRRCCT = false
                )
            }
            is YardEntryAction.TypingSupplierNoTextField -> { //
//                onYardEntryUIStateLoading()
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    supplierNo = viewAction.text,
                    isClickedCallSupplierNo = false
                )
            }
            is YardEntryAction.ClickSendButton -> {
                val value = _yardEntryUISt.value
                println("SSS: ${value.coilNo.isEmpty() || value.isCoilNoTfError}")
                println("SSSS: ${value.coilNo} ${value.yyrrcct} ${value.supplierNo}")
                println("DROPP display: ${value.dataLs.getSelectedItem()?.display}") //
                println("DROPP value: ${value.dataLs.getSelectedItemValue()}") //
                println("isClickedCallCoilNo value: ${value.isClickedCallCoilNo}") //
                println("isClickedCallYYRRCCT value: ${value.isClickedCallYYRRCCT}") //
                println("isClickedCallSupplierNo value: ${value.isClickedCallSupplierNo}") //

//                _yardEntryUISt.value = onYardEntryUIStateSuccess(
//                    navigateType = YardEntryNavigateType.DISPLAY_BUTTON_DIALOG,
//                    successMsg = "Coil No. AAAAAAAA not found" //
//                )

                onYardEntryUIStateLoading()

                _yardEntryUISt.value.coilNoLs.setMatchedItemFrom(_yardEntryUISt.value.coilNo)

                action(YardEntryAction.InitNavigateData)
            }
            is YardEntryAction.ClearAllValueButton -> {
                _yardEntryUISt.value = YardEntryUIStateModel(
                    isClearAllTextFieldValue = true,
                    isAlreadyInitialFeature = true //
                ) // clear with new state model object and set some value
            }
            is YardEntryAction.SetInitFlagClearAllTextField -> {
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    isClearAllTextFieldValue = false
                )
            }
            is YardEntryAction.CheckGetDataBeforeClearAllValue -> {
                val value = _yardEntryUISt.value
                if (value.isClickedCallYYRRCCT.not() ||
                    (value.isClickedCallYYRRCCT && value.isClickedCallSupplierNo.not()))
                    action(YardEntryAction.ClearAllValueButton)
            }
            is YardEntryAction.InitNavigateData -> initNavigateData()
            is YardEntryAction.SelectDataDropdown -> selectDataDropdown(viewAction.selectedData)
            is YardEntryAction.ClickButtonToGoToCoilDetailLsScreen -> {
                if (_yardEntryUISt.value.coilNoLs.isEmpty()) {
                    // add display some error dialog
                    return
                }

                _yardEntryUISt.value = onYardEntryUIStateSuccess(
                    navigateType = YardEntryNavigateType.GO_TO_COIL_DETAIL_LS
                )
            }

            // Coil Detail List Screen
            is YardEntryAction.ClickBackToMainYardEntryScreen -> {
                _yardEntryUISt.value = onYardEntryUIStateSuccess(
                    navigateType = YardEntryNavigateType.BACK_TO_YARD_ENTRY_MAIN
                )
            }
            is YardEntryAction.SelectCoilDetailItem -> {
                onYardEntryUIStateLoading()
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    coilNoLs = _yardEntryUISt.value.coilNoLs.setSelectedRemoveFrom(viewAction.selectedData)
                )
            }
            is YardEntryAction.ClickConfirmRemoveSelectedCoilNoLs -> {
//                _yardEntryUISt.value.coilNoLs.filter { it.isSelectedRemove }.isEmpty()
//                _yardEntryUISt.value.coilNoLs.find { it.isSelectedRemove }.isNull()
                if (_yardEntryUISt.value.coilNoLs.none { it.isSelectedRemove }) {
                    // add display some error dialog
                    return
                }
                onYardEntryUIStateLoading()
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    coilNoLs = _yardEntryUISt.value.coilNoLs.filter { it.isSelectedRemove.not() }
                )
            }
        }
    }

    fun actionFromDialogWith(dialogAction: YardEntryDialogAction) {
        when (dialogAction) {
            is YardEntryDialogAction.ClickLeftDialogButton -> {
                when (_yardEntryUISt.value.errorBody?.yardEntryErrorType) {
                    YardEntryErrorType.EMPTY_SHIPMENT_LOT, YardEntryErrorType.ERROR_FROM_API_SHIPMENT_LOT -> {
                        _yardEntryUISt.value = YardEntryUIStateModel( // create new obj to init all value in state model
                            isClearAllTextFieldValue = true,
                            isAlreadyInitialFeature = true
                        )
                    }
                    YardEntryErrorType.EMPTY_COIL_NUMBER, YardEntryErrorType.ERROR_FROM_API_COIL_NUMBER -> {
                        _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                            isGetCoilRespSuccess = true, // just example
                            coilNo = "" // just example
                        )
                    }
                    else -> { // YardEntryErrorType.ERROR_FROM_API
                        action(YardEntryAction.InitNavigateData)
                    }
                }
            }
            is YardEntryDialogAction.ClearAllValue -> action(YardEntryAction.ClearAllValueButton)
            else -> Unit
        }
    }

    private fun callCoilApi() {
        viewModelScope.launch {
            homeUc.getUserInfo().collect {
                when (it) {
                    is NetworkResult.Success200 -> {
                        // Mock Test to Add Coil Detail into CoilNoList
                        _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                            coilNoLs = if (_yardEntryUISt.value.coilNoLs.find { it.coilNo == _yardEntryUISt.value.coilNo }.isNull()) {
                                _yardEntryUISt.value.coilNoLs + listOf(
                                    CoilDetailItem(
                                        coilNo = _yardEntryUISt.value.coilNo, // from response
                                        status = "YES", // from response
                                        dock = "16W" // from response
                                    )
                                )
                            } else _yardEntryUISt.value.coilNoLs
                        )

                        _yardEntryUISt.value.coilNoLs.setMatchedItemFrom(_yardEntryUISt.value.coilNo)
                        action(YardEntryAction.InitNavigateData)

                        // Mock Success Case
//                        _yardEntryUISt.value = onYardEntryUIStateSuccess(
//                            navigateType = YardEntryNavigateType.START_COUNTDOWN_TIMER
//                        ).copy(
//                            isGetCoilRespSuccess = true,
//                            countdownTime = TIME_TO_GET_TRUCK_NO,
//                            isClickedCallCoilNo = true,
//                            isClickedCallYYRRCCT = false,
//                            isClickedCallSupplierNo = false
//                        )

                        // Mock Fail Case
//                        _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
//                            isGetCoilRespSuccess = false,
//                            coilNo = "",
//                            yyrrcct = "",
//                            supplierNo = "",
//                            isClearAllTextFieldValue = true
//                        )

//                        action(YardEntryAction.ClearAllValueButton)
                    }
                    is NetworkResult.Loading -> onYardEntryUIStateLoading()
                    is NetworkResult.Error -> {
                        _yardEntryUISt.value = onYardEntryUIStateError(
                            yardEntryErrorType = YardEntryErrorType.ERROR_FROM_API_SHIPMENT_LOT, // ERROR_FROM_API_COIL_NUMBER
                            errorMsg = it.errorMessage
                        )
                    }
                    else -> initNavigateData() // Status Code = 204 or other
                }
            }
        }
    }

    private fun callYYRRCCTApi() {
//        _yardEntryUISt.value.isClickedCallYYRRCCT = true

        viewModelScope.launch {
            homeUc.getUserInfo().collect {
                when (it) {
                    is NetworkResult.Success200 -> {
                        _yardEntryUISt.value = onYardEntryUIStateSuccess(
                            navigateType = YardEntryNavigateType.START_COUNTDOWN_TIMER
                        ).copy(
                            isGetYYRRCCTRespSuccess = true,
                            countdownTime = TIME_TO_GET_COIL_NO,
                            isClickedCallYYRRCCT = true, // จะเกิดเคส ยิงแฮนดี้หรือกดnext ก่อนหมดเวลา แต่เอพีไออาจตอบกลับมาแล้วเลยเวลาไปแล้ว ทำให้มันล้างค่า
                            isClickedCallSupplierNo = false
                        )
                    }
                    is NetworkResult.Loading -> onYardEntryUIStateLoading()
                    is NetworkResult.Error -> {
                        _yardEntryUISt.value = onYardEntryUIStateError(
                            errorMsg = it.errorMessage
                        ).copy(
                            isGetYYRRCCTRespSuccess = false
                        )
                    }
                    else -> initNavigateData() // Status Code = 204 or other
                }
            }
        }
    }

    private fun callSupplierNoApi() {
        viewModelScope.launch {
            homeUc.getUserInfo().collect {
                when (it) {
                    is NetworkResult.Success200 -> {
                        _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                            isGetSupplierNoRespSuccess = true,
                            isClickedCallSupplierNo = true // จะเกิดเคส ยิงแฮนดี้หรือกดnext ก่อนหมดเวลา แต่เอพีไออาจตอบกลับมาแล้วเลยเวลาไปแล้ว ทำให้มันล้างค่า
                        )
                    }
                    is NetworkResult.Loading -> onYardEntryUIStateLoading()
                    is NetworkResult.Error -> {
                        _yardEntryUISt.value = onYardEntryUIStateError(
                            errorMsg = it.errorMessage
                        ).copy(
                            isGetSupplierNoRespSuccess = false
                        )
                    }
                    else -> initNavigateData() // Status Code = 204 or other
                }
            }
        }
    }

    private fun selectDataDropdown(selectedData: DropdownUIModel) {
        onYardEntryUIStateLoading()

        _yardEntryUISt.value.dataLs.setSelectItemFrom(selectedData)

        initNavigateData()
    }

    private fun initNavigateData() {
        _yardEntryUISt.value = onYardEntryUIStateSuccess()
    }

    private fun onYardEntryUIStateSuccess(
        navigateType: YardEntryNavigateType = YardEntryNavigateType.NONE,
        successMsg: String? = null
    ): YardEntryUIStateModel = _yardEntryUISt.value.copy(
        isLoading = false,
        isSuccess = true,
        navigateType = navigateType,
        successMsg = successMsg,
        isError = false,
        errorBody = null
    )

    private fun onYardEntryUIStateError(
        yardEntryErrorType: YardEntryErrorType = YardEntryErrorType.ERROR_FROM_API,
        errorMsg: String? = null
    ): YardEntryUIStateModel = _yardEntryUISt.value.copy(
        isLoading = false,
        isSuccess = false,
        navigateType = YardEntryNavigateType.NONE,
        successMsg = null,
        isError = true,
        errorBody = YardEntryErrorModel(
            yardEntryErrorType = yardEntryErrorType,
            errorMsg = errorMsg
        )
    )

    private fun onYardEntryUIStateLoading(isLoading: Boolean = true) {
        _yardEntryUISt.value = _yardEntryUISt.value.copy(isLoading = isLoading)
    }
}