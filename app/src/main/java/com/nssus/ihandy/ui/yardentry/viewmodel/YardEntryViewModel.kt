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
import com.nssus.ihandy.data.extension.setSelectItemFrom
import com.nssus.ihandy.data.usecase.HomeUseCase
import com.nssus.ihandy.model.network.NetworkResult
import com.nssus.ihandy.model.ui.DropdownUIModel
import com.nssus.ihandy.model.yardentry.YardEntryAction
import com.nssus.ihandy.model.yardentry.YardEntryErrorModel
import com.nssus.ihandy.model.yardentry.YardEntryErrorType
import com.nssus.ihandy.model.yardentry.YardEntryNavigateType
import com.nssus.ihandy.model.yardentry.YardEntryUIStateModel
import com.nssus.ihandy.ui.yardentry.constant.YardEntryConstant.MAX_LENGTH_COIL_NO
import kotlinx.coroutines.launch

class YardEntryViewModel(
    private val homeUc: HomeUseCase
) : ViewModel() {
    private val _yardEntryUISt = mutableStateOf(YardEntryUIStateModel())
    val yardEntryUISt: State<YardEntryUIStateModel> = _yardEntryUISt

    fun initData() {
        _yardEntryUISt.value = YardEntryUIStateModel() /////////
//            .copy(
//            dataLs = getDataLs()
//        )
    }

    fun action(viewAction: YardEntryAction) {
        when (viewAction) {
            is YardEntryAction.GoBack -> {
                _yardEntryUISt.value = YardEntryUIStateModel() ///// เคลียค่า เพื่อกันเข้ามาหน้าแรกอีกรอบละที่กรอกยังอยุ
                _yardEntryUISt.value = onYardEntryUIStateSuccess(
                    navigateType = YardEntryNavigateType.GO_BACK
                )
            }
            is YardEntryAction.TypingCoilNoTextField -> { //
//                onYardEntryUIStateLoading()
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    coilNo = viewAction.text,
                    isCoilNoTfError = viewAction.text.isErrorTextFieldWith(MAX_LENGTH_COIL_NO),
                    resultIconId = when {
//                        isEqualMaxLength(viewAction.text, MAX_LENGTH_COIL_NO) -> null
                        viewAction.text.isErrorTextFieldWith(MAX_LENGTH_COIL_NO) -> R.drawable.ic_dialog_red_cross
                        viewAction.text.isEqualsMaxLength(MAX_LENGTH_COIL_NO)-> R.drawable.ic_dialog_green_tick
                        else -> null
                    }
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
            is YardEntryAction.TypingYYRRCCTTextField -> { //
//                onYardEntryUIStateLoading()
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    yyrrcct = viewAction.text
                )
            }
            is YardEntryAction.TypingSupplierNoTextField -> { //
//                onYardEntryUIStateLoading()
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    supplierNo = viewAction.text
                )
            }
            is YardEntryAction.ClickSendButton -> {
                val value = _yardEntryUISt.value
                println("SSS: ${value.coilNo.isEmpty() || value.isCoilNoTfError}")
                println("SSSS: ${value.coilNo} ${value.yyrrcct} ${value.supplierNo}")
                println("DROPP display: ${value.dataLs.getSelectedItem()?.display}") //
                println("DROPP value: ${value.dataLs.getSelectedItemValue()}") //

                _yardEntryUISt.value = onYardEntryUIStateSuccess(
                    navigateType = YardEntryNavigateType.DISPLAY_BUTTON_DIALOG,
                    successMsg = "Coil No. AAAAAAAA not found" //
                )
            }
            is YardEntryAction.ClearAllValueButton -> {
                _yardEntryUISt.value = YardEntryUIStateModel().copy( // clear and then .copy( set some values to update (optional))
                    isClearAllTextFieldValue = true
                )
            }
            is YardEntryAction.SetInitFlagClearAllTextField -> {
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    isClearAllTextFieldValue = false
                )
            }
            is YardEntryAction.ClickContinueDialogButton -> callCoilApi()
            is YardEntryAction.InitNavigateData -> initNavigateData()
            is YardEntryAction.SelectDataDropdown -> selectDataDropdown(viewAction.selectedData)
        }
    }

    private fun callCoilApi() {
        viewModelScope.launch {
            homeUc.getUserInfo().collect {
                when (it) {
                    is NetworkResult.Success -> {
                        // Mock Success Case
                        _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                            isGetCoilRespSuccess = true
                        )

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
                            errorMsg = it.errorMessage
                        ).copy(
                            isGetCoilRespSuccess = false
                        )
                    }
                }
            }
        }
    }

    private fun callYYRRCCTApi() {
        viewModelScope.launch {
            homeUc.getUserInfo().collect {
                when (it) {
                    is NetworkResult.Success -> {
                        _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                            isGetYYRRCCTRespSuccess = true
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
                }
            }
        }
    }

    private fun selectDataDropdown(selectedData: DropdownUIModel) {
        onYardEntryUIStateLoading()

        _yardEntryUISt.value.dataLs.setSelectItemFrom(selectedData)

        initNavigateData()
    }

    fun initNavigateData() {
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