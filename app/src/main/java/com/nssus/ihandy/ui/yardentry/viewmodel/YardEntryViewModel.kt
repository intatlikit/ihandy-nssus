package com.nssus.ihandy.ui.yardentry.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nssus.ihandy.data.extension.getSelectedItem
import com.nssus.ihandy.data.extension.getSelectedItemValue
import com.nssus.ihandy.data.extension.isErrorTextFieldWith
import com.nssus.ihandy.data.extension.setSelectItemFrom
import com.nssus.ihandy.model.ui.DropdownUIModel
import com.nssus.ihandy.model.yardentry.YardEntryAction
import com.nssus.ihandy.model.yardentry.YardEntryErrorModel
import com.nssus.ihandy.model.yardentry.YardEntryErrorType
import com.nssus.ihandy.model.yardentry.YardEntryNavigateType
import com.nssus.ihandy.model.yardentry.YardEntryUIStateModel
import com.nssus.ihandy.ui.yardentry.constant.YardEntryConstant.MAX_LENGTH_COIL_NO
import com.nssus.ihandy.ui.yardentry.util.YardEntryUtil.getDataLs

class YardEntryViewModel(

) : ViewModel() {
    private val _yardEntryUISt = mutableStateOf(YardEntryUIStateModel())
    val yardEntryUISt: State<YardEntryUIStateModel> = _yardEntryUISt

    fun initData() {
        _yardEntryUISt.value = YardEntryUIStateModel().copy(
            dataLs = getDataLs()
        )
    }

    fun action(viewAction: YardEntryAction) {
        when (viewAction) {
            is YardEntryAction.GoBack -> {
                _yardEntryUISt.value = onYardEntryUIStateSuccess(
                    navigateType = YardEntryNavigateType.GO_BACK
                )
            }
            is YardEntryAction.TypingCoilNoTextField -> { //
//                onYardEntryUIStateLoading()
                _yardEntryUISt.value = onYardEntryUIStateSuccess().copy(
                    coilNo = viewAction.text,
                    isCoilNoTfError = viewAction.text.isErrorTextFieldWith(MAX_LENGTH_COIL_NO)
                // viewAction.text.isNotEmpty() && MAX_LENGTH_COIL_NO !=  viewAction.text.length
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
            }
            is YardEntryAction.ClickClearButton -> { // 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
//                onYardEntryUIStateLoading()
                _yardEntryUISt.value = onYardEntryUIStateSuccess(
//                    navigateType = YardEntryNavigateType.GO_BACK
//                    successMsg = "Something"
                ).copy(
                    dataLs = getDataLs(),
                    isCoilNoTfError = false,
                    coilNo = "",
                    yyrrcct = "",
                    supplierNo = ""
                )
            }
            is YardEntryAction.SelectDataDropdown -> selectDataDropdown(viewAction.selectedData)
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