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
    var coilNoLs: List<CoilDetailItem> = listOf(
        CoilDetailItem(
            coilNo = "AAAAAAAA12",
            status = "",
            dock = "16W"
        ),
        CoilDetailItem(
            coilNo = "AAAAAAAA13",
            status = "",
            dock = "16W"
        ),
        CoilDetailItem(
            coilNo = "AAAAAAAA14",
            status = "",
            dock = "16W"
        ),
        CoilDetailItem(
            coilNo = "AAAAAAAA15",
            status = "YES",
            dock = "16W"
        ),
        CoilDetailItem(
            coilNo = "AAAAAAAA16",
            status = "YES",
            dock = "16W"
        ),
        CoilDetailItem(
            coilNo = "AAAAAAAA17",
            status = "YES",
            dock = "16W"
        ),
        CoilDetailItem(
            coilNo = "AAAAAAAA18",
            status = "YES",
            dock = "16W"
        ),
        CoilDetailItem(
            coilNo = "AAAAAAAA19",
            status = "YES",
            dock = "16W"
        )
    ) // emptyList()
)

enum class YardEntryNavigateType {
    GO_BACK,
    DISPLAY_BUTTON_DIALOG,
    START_COUNTDOWN_TIMER,
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