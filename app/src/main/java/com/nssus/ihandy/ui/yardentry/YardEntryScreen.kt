package com.nssus.ihandy.ui.yardentry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nssus.ihandy.R
import com.nssus.ihandy.data.extension.getMatchedItem
import com.nssus.ihandy.data.extension.getSelectedItem
import com.nssus.ihandy.data.extension.isNotNull
import com.nssus.ihandy.data.extension.padStart
import com.nssus.ihandy.data.extension.padStartCustom
import com.nssus.ihandy.data.extension.simpleVerticalScrollbar
import com.nssus.ihandy.model.ui.DropdownUIModel
import com.nssus.ihandy.model.yardentry.CoilDetailItem
import com.nssus.ihandy.model.yardentry.YardEntryAction
import com.nssus.ihandy.model.yardentry.YardEntryUIStateModel
import com.nssus.ihandy.ui.basecomposable.BaseButton
import com.nssus.ihandy.ui.basecomposable.BaseDropdownDialog
import com.nssus.ihandy.ui.basecomposable.BaseHeader
import com.nssus.ihandy.ui.basecomposable.RowPairButton
import com.nssus.ihandy.ui.basecomposable.TitleAndGrayBgValueText
import com.nssus.ihandy.ui.basecomposable.TopTitleAndBaseTextField
import com.nssus.ihandy.ui.basecomposable.BaseContentCardWithBackButton
import com.nssus.ihandy.ui.basecomposable.PrefixTitleAndGrayBgValueTextWithIcon
import com.nssus.ihandy.ui.basecomposable.Table2ColumnDetailRow
import com.nssus.ihandy.ui.basecomposable.Table2ColumnHeaderRow
import com.nssus.ihandy.ui.basecomposable.Table3ColumnDetailRow
import com.nssus.ihandy.ui.basecomposable.Table3ColumnHeaderRow
import com.nssus.ihandy.ui.theme.BaseGreen
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles
import com.nssus.ihandy.ui.theme.SilverGray
import com.nssus.ihandy.ui.theme.StatusRed
import com.nssus.ihandy.ui.theme.White87
import com.nssus.ihandy.ui.yardentry.basecomposable.BaseDropdownDialogWithButton
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
    val focusManager = LocalFocusManager.current

    var coilNoTxt by remember { mutableStateOf(TextFieldValue(uiYardEntrySt.coilNo)) }
    var yyrrcctTxt by remember { mutableStateOf(TextFieldValue(uiYardEntrySt.yyrrcct)) }
    var supplierNoTxt by remember { mutableStateOf(TextFieldValue(uiYardEntrySt.supplierNo)) }

    val listState = rememberLazyListState()

    val focusedCoilNoItem = uiYardEntrySt.coilNoLs.getMatchedItem()

    LaunchedEffect(
        uiYardEntrySt.isGetCoilRespSuccess,
        uiYardEntrySt.isGetYYRRCCTRespSuccess,
        uiYardEntrySt.isGetSupplierNoRespSuccess,
        uiYardEntrySt.isClearAllTextFieldValue,
        focusedCoilNoItem // Scroll to the item when the composable is first created or when focusIndex changes
    ) {
        when {
            uiYardEntrySt.isGetCoilRespSuccess -> {
                secondFocusRequester.requestFocus() // set focus next textfield
                onAction(YardEntryAction.SetInitFlagGetCoilResp) // action to clear flag
            }
            uiYardEntrySt.isGetYYRRCCTRespSuccess -> {
                thirdFocusRequester.requestFocus() // set focus next textfield
                onAction(YardEntryAction.SetInitFlagGetYYRRCCTResp) // action to clear flag
            }
            uiYardEntrySt.isGetSupplierNoRespSuccess -> {
                focusManager.clearFocus() //
                onAction(YardEntryAction.SetInitFlagGetSupplierNoResp) // action to clear flag
            }
            uiYardEntrySt.isClearAllTextFieldValue -> {
                coilNoTxt = TextFieldValue()
                yyrrcctTxt = TextFieldValue()
                supplierNoTxt = TextFieldValue()

                onAction(YardEntryAction.SetInitFlagClearAllTextField)
            }
            focusedCoilNoItem.isNotNull() -> listState.animateScrollToItem(uiYardEntrySt.coilNoLs.indexOf(focusedCoilNoItem))
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

            Row(verticalAlignment = Alignment.Bottom) {
                TopTitleAndBaseTextField(
                    modifier = Modifier
                        .weight(.51f)
                        .focusRequester(thirdFocusRequester),
                    titleId = R.string.yard_entry_coil_no_title, // yard_entry_supplier_no_title
                    tfValue = supplierNoTxt,
                    tfMaxLength = MAX_LENGTH_SUPPLIER_NO,
                    onTfValueChanged = {
                        supplierNoTxt = it
                        onAction(YardEntryAction.TypingSupplierNoTextField(it.text))
                    },
                    onTfNextActionClick = {
                        onAction(YardEntryAction.ClickNextActionSupplierNoTextField)
//                            keyboardController?.hide() // ถ้าไม่เซต จะไม่ซ่อนคีย์บอร์ด
                    }
                )
                BaseButton(
                    modifier = Modifier
                        .offset(y = 3.dp)
                        .padding(start = Dimens.space_textfield_to_textfield)
                        .weight(.49f),
                    textFontWeight = FontWeight.Normal,
                    contentPadding = PaddingValues(0.dp),
                    text = stringResource(id = R.string.yard_entry_coil_no_count, uiYardEntrySt.coilNoLs.count().padStart()), // padStartCustom
                    onButtonClick = { onAction(YardEntryAction.ClickButtonToGoToCoilDetailLsScreen) }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SilverGray)
                    .padding(horizontal = 8.dp),
                state = listState
            ) {
                item { // Table Header
                    Table2ColumnHeaderRow()
//                    Table3ColumnHeaderRow()
                }
                items(uiYardEntrySt.coilNoLs) { // Table Detail
                    Table2ColumnDetailRow(
                        isFirstColumnValueMatched = it.isMatched,
                        firstColumnValue = it.coilNo,
                        secondColumnValue = it.status
                    )
//                    Table3ColumnDetailRow(
//                        isFirstColumnValueMatched = it.isMatched,
//                        firstColumnValue = it.coilNo,
//                        secondColumnValue = it.status,
//                        thirdColumnValue = it.dock
//                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp)) // make ur own
        RowPairButton(
//            leftButtonWeight = .6f,
//            rightButtonWeight = .4f,
//            leftButtonContentPadding = PaddingValues(0.dp),
//            rightButtonContentPadding = PaddingValues(0.dp),
            onLeftButtonClick = { onAction(YardEntryAction.ClickSendButton) },
            onRightButtonClick = { onAction(YardEntryAction.ClearAllValueButton) }
        )
    }
}

//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun YardEntryScreen(
//    uiYardEntrySt: YardEntryUIStateModel,
//    onAction: (YardEntryAction) -> Unit
//) {
//    val firstFocusRequester = FocusRequester()
//    val secondFocusRequester = FocusRequester()
//    val thirdFocusRequester = FocusRequester()
//
//    val keyboardController = LocalSoftwareKeyboardController.current
//
//    var coilNoTxt by remember { mutableStateOf(TextFieldValue(uiYardEntrySt.coilNo)) }
//    var yyrrcctTxt by remember { mutableStateOf(TextFieldValue(uiYardEntrySt.yyrrcct)) }
//    var supplierNoTxt by remember { mutableStateOf(TextFieldValue(uiYardEntrySt.supplierNo)) }
//
//    LaunchedEffect(
//        uiYardEntrySt.isGetCoilRespSuccess,
//        uiYardEntrySt.isGetYYRRCCTRespSuccess,
//        uiYardEntrySt.isGetSupplierNoRespSuccess,
//        uiYardEntrySt.isClearAllTextFieldValue
//    ) {
//        when {
//            uiYardEntrySt.isGetCoilRespSuccess -> {
//                secondFocusRequester.requestFocus() // set focus next textfield
//                onAction(YardEntryAction.SetInitFlagGetCoilResp) // action to clear flag
//            }
//            uiYardEntrySt.isGetYYRRCCTRespSuccess -> {
//                thirdFocusRequester.requestFocus() // set focus next textfield
//                onAction(YardEntryAction.SetInitFlagGetYYRRCCTResp) // action to clear flag
//            }
//            uiYardEntrySt.isGetSupplierNoRespSuccess -> {
//                onAction(YardEntryAction.SetInitFlagGetSupplierNoResp) // action to clear flag
//            }
//            uiYardEntrySt.isClearAllTextFieldValue -> {
//                coilNoTxt = TextFieldValue()
//                yyrrcctTxt = TextFieldValue()
//                supplierNoTxt = TextFieldValue()
//
//                onAction(YardEntryAction.SetInitFlagClearAllTextField)
//            }
//        }
//    }
//
//    BaseContentCardWithBackButton(onBackIconClick = { onAction(YardEntryAction.GoBack) }) {
//        Column(
//            modifier = Modifier
//                .fillMaxHeight()
//                .weight(1f)
//        ) {
//            Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
//            BaseHeader(headerId = R.string.yard_entry_menu_title)
//            LazyColumn {
//                item {
//                    Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
//                    BaseDropdownDialog(
//                        dataList = uiYardEntrySt.dataLs,
//                        onDropdownItemSelected = { onAction(YardEntryAction.SelectDataDropdown(it)) },
//                        selectedItem = uiYardEntrySt.dataLs.getSelectedItem()
//                    )
//                    Spacer(modifier = Modifier.height(Dimens.space_textfield_to_textfield))
//                    PrefixTitleAndGrayBgValueTextWithIcon(
//                        value = stringResource(id = R.string.common_ok_text),
//                        iconId = uiYardEntrySt.resultIconId
//                    )
//                    Spacer(modifier = Modifier.height(Dimens.space_textfield_to_textfield))
//                    Row {
//                        TopTitleAndBaseTextField(
//                            modifier = Modifier
//                                .weight(.53f)
//                                .focusRequester(firstFocusRequester),
//                            titleId = R.string.yard_entry_coil_no_title,
//                            tfValue = coilNoTxt, // TextFieldValue(coilNo, TextRange(coilNo.length)),
//                            tfMaxLength = MAX_LENGTH_COIL_NO, //
//                            tfIsError = uiYardEntrySt.isCoilNoTfError, //
////                            tfErrorMessage = "WARNING", // stringResource(R.string.yard_entry_coil_no_title)
//                            onTfValueChanged = {
//                                coilNoTxt = it //
////                                isCoilNoTfError = it.text.isNotEmpty() && MAX_LENGTH_COIL_NO != it.text.length //
//                                onAction(YardEntryAction.TypingCoilNoTextField(it.text))
//                            },
//                            onTfNextActionClick = {
//                                onAction(YardEntryAction.ClickNextActionCoilTextField)
//                            }
//                        )
//                        Spacer(modifier = Modifier.width(Dimens.space_textfield_to_textfield))
//                        TopTitleAndBaseTextField(
//                            modifier = Modifier
//                                .weight(.47f)
//                                .focusRequester(secondFocusRequester),
//                            titleId = R.string.yard_entry_yyrrcct_title,
//                            tfValue = yyrrcctTxt,
//                            tfMaxLength = MAX_LENGTH_YYRRCCT,
//                            onTfValueChanged = {
//                                yyrrcctTxt = it
//                                onAction(YardEntryAction.TypingYYRRCCTTextField(it.text))
//                            },
//                            onTfNextActionClick = {
//                                onAction(YardEntryAction.ClickNextActionYYRRCCTTextField)
//                            }
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(Dimens.space_textfield_to_textfield))
//                    TopTitleAndBaseTextField(
//                        modifier = Modifier.focusRequester(thirdFocusRequester),
//                        titleId = R.string.yard_entry_supplier_no_title,
//                        tfValue = supplierNoTxt,
//                        tfMaxLength = MAX_LENGTH_SUPPLIER_NO,
//                        onTfValueChanged = {
//                            supplierNoTxt = it
//                            onAction(YardEntryAction.TypingSupplierNoTextField(it.text))
//                        },
//                        onTfNextActionClick = {
//                            onAction(YardEntryAction.ClickNextActionSupplierNoTextField)
////                            keyboardController?.hide() // ถ้าไม่เซต จะไม่ซ่อนคีย์บอร์ด
//                        }
//                    )
//
//                    TitleAndGrayBgValueText(
//                        modifier = Modifier.padding(
//                            top = Dimens.padding_inner_content_to_top_title_gray_value,
//                            bottom = Dimens.padding_below_bottom_title_gray_value
//                        ),
//                        titleId = R.string.yard_entry_display_yyrrcct_title,
//                        titleTextStyle = FontStyles.txt28,
//                        value = "${uiYardEntrySt.coilNo} ${uiYardEntrySt.yyrrcct} ${uiYardEntrySt.supplierNo}",
//                    )
//                }
//            }
//        }
//
//        RowPairButton(
//            onLeftButtonClick = { onAction(YardEntryAction.ClickSendButton) },
//            onRightButtonClick = {
////                coilNoTxt = TextFieldValue()
////                yyrrcctTxt = TextFieldValue()
////                supplierNoTxt = TextFieldValue()
//
//                onAction(YardEntryAction.ClearAllValueButton)
//            }
//        )
//    }
//}

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
            coilNoLs = listOf(
                CoilDetailItem(
                    coilNo = "AAAAAAAA12",
                    status = "",
                    dock = "16W"
                ),
                CoilDetailItem(
                    isMatched = true,
                    coilNo = "AAAAAAAA13",
                    status = "YES",
                    dock = "16W"
                )
            )
        ),
        onAction = {}
    )
}