package com.nssus.ihandy.model.yardentry

import com.nssus.ihandy.model.ui.DropdownUIModel

sealed interface YardEntryAction {
    object GoBack : YardEntryAction
    data class TypingCoilNoTextField(val text: String) : YardEntryAction
    data class TypingYYRRCCTTextField(val text: String) : YardEntryAction
    data class TypingSupplierNoTextField(val text: String) : YardEntryAction
    object ClickSendButton : YardEntryAction
    object ClickClearButton : YardEntryAction
    data class SelectDataDropdown(val selectedData: DropdownUIModel) : YardEntryAction
    object ClickNextActionCoilTextField : YardEntryAction
    object SetInitFlagGetCoilResp : YardEntryAction
    object ClickNextActionYYRRCCTTextField : YardEntryAction
    object SetInitFlagGetYYRRCCTResp : YardEntryAction
}