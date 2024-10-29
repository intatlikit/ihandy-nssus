package com.nssus.ihandy.ui.yardentry

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.nssus.ihandy.R
import com.nssus.ihandy.data.extension.getSelectedItem
import com.nssus.ihandy.model.ui.DropdownUIModel
import com.nssus.ihandy.model.yardentry.YardEntryAction
import com.nssus.ihandy.model.yardentry.YardEntryUIStateModel
import com.nssus.ihandy.ui.basecomposable.BaseDropdownDialog
import com.nssus.ihandy.ui.basecomposable.BaseHeader
import com.nssus.ihandy.ui.basecomposable.RowPairButton
import com.nssus.ihandy.ui.basecomposable.TitleAndGrayBgValueText
import com.nssus.ihandy.ui.basecomposable.TopTitleAndBaseTextField
import com.nssus.ihandy.ui.basecomposable.BaseContentCardWithBackButton
import com.nssus.ihandy.ui.basecomposable.PrefixTitleAndGrayBgValueTextWithIcon
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles
import com.nssus.ihandy.ui.yardentry.constant.YardEntryConstant.MAX_LENGTH_COIL_NO
import com.nssus.ihandy.ui.yardentry.constant.YardEntryConstant.MAX_LENGTH_SUPPLIER_NO
import com.nssus.ihandy.ui.yardentry.constant.YardEntryConstant.MAX_LENGTH_YYRRCCT

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun YardEntryScreen(
    uiYardEntrySt: YardEntryUIStateModel,
    onAction: (YardEntryAction) -> Unit
) {
    val firstFocusRequester = FocusRequester()
    val secondFocusRequester = FocusRequester()
    val thirdFocusRequester = FocusRequester()

    val keyboardController = LocalSoftwareKeyboardController.current

    var coilNoTxt by remember { mutableStateOf(TextFieldValue(uiYardEntrySt.coilNo)) }
    var yyrrcctTxt by remember { mutableStateOf(TextFieldValue(uiYardEntrySt.yyrrcct)) }
    var supplierNoTxt by remember { mutableStateOf(TextFieldValue(uiYardEntrySt.supplierNo)) }

    LaunchedEffect(uiYardEntrySt.isGetCoilRespSuccess, uiYardEntrySt.isGetYYRRCCTRespSuccess, uiYardEntrySt.isClearAllTextFieldValue) {
        when {
            uiYardEntrySt.isGetCoilRespSuccess -> {
                secondFocusRequester.requestFocus() // set focus next textfield
                onAction(YardEntryAction.SetInitFlagGetCoilResp) // action to clear flag
            }
            uiYardEntrySt.isGetYYRRCCTRespSuccess -> {
                thirdFocusRequester.requestFocus() // set focus next textfield
                onAction(YardEntryAction.SetInitFlagGetYYRRCCTResp) // action to clear flag
            }
            uiYardEntrySt.isClearAllTextFieldValue -> {
                coilNoTxt = TextFieldValue()
                yyrrcctTxt = TextFieldValue()
                supplierNoTxt = TextFieldValue()

                onAction(YardEntryAction.SetInitFlagClearAllTextField)
            }
        }
    }

    BaseContentCardWithBackButton(onBackIconClick = { onAction(YardEntryAction.GoBack) }) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
            BaseHeader(headerId = R.string.yard_entry_menu_title)
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
                    BaseDropdownDialog(
                        dataList = uiYardEntrySt.dataLs,
                        onDropdownItemSelected = { onAction(YardEntryAction.SelectDataDropdown(it)) },
                        selectedItem = uiYardEntrySt.dataLs.getSelectedItem()
                    )
                    Spacer(modifier = Modifier.height(Dimens.space_textfield_to_textfield))
                    PrefixTitleAndGrayBgValueTextWithIcon(
                        value = stringResource(id = R.string.common_ok_text),
                        iconId = uiYardEntrySt.resultIconId
                    )
                    Spacer(modifier = Modifier.height(Dimens.space_textfield_to_textfield))
                    Row {
                        TopTitleAndBaseTextField(
                            modifier = Modifier
                                .weight(.53f)
                                .focusRequester(firstFocusRequester),
                            titleId = R.string.yard_entry_coil_no_title,
                            tfValue = coilNoTxt, // TextFieldValue(coilNo, TextRange(coilNo.length)),
                            tfMaxLength = MAX_LENGTH_COIL_NO, //
                            tfIsError = uiYardEntrySt.isCoilNoTfError, //
//                            tfErrorMessage = "WARNING", // stringResource(R.string.yard_entry_coil_no_title)
                            onTfValueChanged = {
                                coilNoTxt = it //
//                                isCoilNoTfError = it.text.isNotEmpty() && MAX_LENGTH_COIL_NO != it.text.length //
                                onAction(YardEntryAction.TypingCoilNoTextField(it.text))
                            },
                            onTfNextActionClick = {
                                onAction(YardEntryAction.ClickNextActionCoilTextField)
                            }
                        )
                        Spacer(modifier = Modifier.width(Dimens.space_textfield_to_textfield))
                        TopTitleAndBaseTextField(
                            modifier = Modifier
                                .weight(.47f)
                                .focusRequester(secondFocusRequester),
                            titleId = R.string.yard_entry_yyrrcct_title,
                            tfValue = yyrrcctTxt,
                            tfMaxLength = MAX_LENGTH_YYRRCCT,
                            onTfValueChanged = {
                                yyrrcctTxt = it
                                onAction(YardEntryAction.TypingYYRRCCTTextField(it.text))
                            },
                            onTfNextActionClick = {
                                onAction(YardEntryAction.ClickNextActionYYRRCCTTextField)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(Dimens.space_textfield_to_textfield))
                    TopTitleAndBaseTextField(
                        modifier = Modifier.focusRequester(thirdFocusRequester),
                        titleId = R.string.yard_entry_supplier_no_title,
                        tfValue = supplierNoTxt,
                        tfMaxLength = MAX_LENGTH_SUPPLIER_NO,
                        onTfValueChanged = {
                            supplierNoTxt = it
                            onAction(YardEntryAction.TypingSupplierNoTextField(it.text))
                        },
                        onTfNextActionClick = {
                            keyboardController?.hide() // ถ้าไม่เซต จะไม่ซ่อนคีย์บอร์ด
                        }
                    )

                    TitleAndGrayBgValueText(
                        modifier = Modifier.padding(
                            top = Dimens.padding_inner_content_to_top_title_gray_value,
                            bottom = Dimens.padding_below_bottom_title_gray_value
                        ),
                        titleId = R.string.yard_entry_display_yyrrcct_title,
                        titleTextStyle = FontStyles.txt28,
                        value = "${uiYardEntrySt.coilNo} ${uiYardEntrySt.yyrrcct} ${uiYardEntrySt.supplierNo}",
                    )
                }
            }
        }

        RowPairButton(
            onLeftButtonClick = { onAction(YardEntryAction.ClickSendButton) },
            onRightButtonClick = {
//                coilNoTxt = TextFieldValue()
//                yyrrcctTxt = TextFieldValue()
//                supplierNoTxt = TextFieldValue()

                onAction(YardEntryAction.ClearAllValueButton)
            }
        )
    }
}

@Preview(showBackground = true, locale = "th")
@Composable
fun YardEntryScreenPreview() {
    YardEntryScreen(
        uiYardEntrySt = YardEntryUIStateModel(
            dataLs = listOf(DropdownUIModel("", display = "Product Label", true)),
            isCoilNoTfError = false,
//        resultIconId = null,
            resultIconId = R.drawable.ic_dialog_green_tick,
            coilNo = "",
            yyrrcct = "",
            supplierNo = "",
        ),
        onAction = {}
    )
}