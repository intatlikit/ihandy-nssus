package com.nssus.ihandy.model.yardentry

import androidx.annotation.StringRes
import com.nssus.ihandy.R
import com.nssus.ihandy.model.ui.DropdownUIModel

data class YardEntryUIStateModel(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var navigateType: YardEntryNavigateType = YardEntryNavigateType.NONE,
    var successMsg: String? = null,
    var isError: Boolean = false,
    var errorBody: YardEntryErrorModel? = null,
    //
    var dataLs: List<DropdownUIModel> = emptyList(),
    var isCoilNoTfError: Boolean = false, //
    var coilNo: String = "", //
    var yyrrcct: String = "", //
    var supplierNo: String = "" //
)

enum class YardEntryNavigateType {
    GO_TO_MAIN,
    GO_BACK,
//    DISPLAY_CONFIRM_DIALOG,
//    DISPLAY_DOWNLOAD_SUCCESS_DIALOG,
//    BACK_TO_SELECT_OR_SEARCH_COMP,
    NONE
}

data class YardEntryErrorModel(
    val yardEntryErrorType: YardEntryErrorType = YardEntryErrorType.ERROR_FROM_API,
    var errorMsg: String? = null
)

enum class YardEntryErrorType(@StringRes val errorMsgId: Int = R.string.empty_string) {
    //    DATA_NOT_FOUND(R.string.base_data_not_found),
    ERROR_FROM_API
}