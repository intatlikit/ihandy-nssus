package com.nssus.ihandy.model.yardentry

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nssus.ihandy.R
import com.nssus.ihandy.model.ui.DropdownUIModel
import com.nssus.ihandy.ui.yardentry.util.YardEntryUtil.getDataLs

data class YardEntryUIStateModel(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var navigateType: YardEntryNavigateType = YardEntryNavigateType.NONE,
    var successMsg: String? = null,
    var isError: Boolean = false,
    var errorBody: YardEntryErrorModel? = null,
    //
    var isAlreadyInitialFeature: Boolean = false,
    var dataLs: List<DropdownUIModel> = getDataLs(),
    var isCoilNoTfError: Boolean = false, //
    var isGetCoilRespSuccess: Boolean = false,
    var isGetYYRRCCTRespSuccess: Boolean = false,
    var isGetSupplierNoRespSuccess: Boolean = false,
    @DrawableRes var resultIconId: Int? = null,
    var coilNo: String = "", //
    var yyrrcct: String = "", //
    var supplierNo: String = "", //
    var isClearAllTextFieldValue: Boolean = false,
    // rename urself
    var countdownTime: Long = 0L,
    var isClickedCallCoilNo: Boolean = false,
    var isClickedCallYYRRCCT: Boolean = false,
    var isClickedCallSupplierNo: Boolean = false,
    //
    var coilNoLs: List<CoilDetailItem> = emptyList()
)

enum class YardEntryNavigateType {
    GO_BACK,
    DISPLAY_BUTTON_DIALOG,
    START_COUNTDOWN_TIMER,
    GO_TO_COIL_DETAIL_LS,
    NONE,
    // Coil Detail List Screen
    BACK_TO_YARD_ENTRY_MAIN,
    DISPLAY_CONFIRM_REMOVE_COIL_DIALOG
}

data class YardEntryErrorModel(
    val yardEntryErrorType: YardEntryErrorType = YardEntryErrorType.ERROR_FROM_API,
    var errorMsg: String? = null
)

enum class YardEntryErrorType(@StringRes val errorMsgId: Int = R.string.empty_string) {
    EMPTY_SHIPMENT_LOT(R.string.dash_string), // convert to ur own
    EMPTY_COIL_NUMBER(R.string.dash_string), // convert to ur own
    ERROR_FROM_API_SHIPMENT_LOT,
    ERROR_FROM_API_COIL_NUMBER,
    ERROR_FROM_API
}