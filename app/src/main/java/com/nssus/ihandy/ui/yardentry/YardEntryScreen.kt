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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.nssus.ihandy.R
import com.nssus.ihandy.data.extension.getSelectedItem
import com.nssus.ihandy.model.ui.DropdownUIModel
import com.nssus.ihandy.model.yardentry.YardEntryAction
import com.nssus.ihandy.ui.basecomposable.BaseDropdownDialog
import com.nssus.ihandy.ui.basecomposable.BaseHeader
import com.nssus.ihandy.ui.basecomposable.RowPairButton
import com.nssus.ihandy.ui.basecomposable.TitleAndGrayBgValueText
import com.nssus.ihandy.ui.basecomposable.TopTitleAndBaseTextField
import com.nssus.ihandy.ui.basecomposable.BaseContentCardWithBackButton
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles
import com.nssus.ihandy.ui.yardentry.constant.YardEntryConstant.MAX_LENGTH_COIL_NO
import com.nssus.ihandy.ui.yardentry.constant.YardEntryConstant.MAX_LENGTH_SUPPLIER_NO
import com.nssus.ihandy.ui.yardentry.constant.YardEntryConstant.MAX_LENGTH_YYRRCCT

@Composable
fun YardEntryScreen(
    dataLs: List<DropdownUIModel>,
    isCoilNoTfError: Boolean,
    coilNo: String,
    yyrrcct: String,
    supplierNo: String,
    onAction: (YardEntryAction) -> Unit
) {
//    var isCoilNoTfError by remember { mutableStateOf(false) }

    var coilNoTxt by remember { mutableStateOf(TextFieldValue(coilNo)) }
//    var yyrrcctTxt by remember { mutableStateOf(TextFieldValue(yyrrcct)) }
//    var supplierNoTxt by remember { mutableStateOf(TextFieldValue(supplierNo)) }

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
                        dataList = dataLs,
                        onDropdownItemSelected = { onAction(YardEntryAction.SelectDataDropdown(it)) },
                        selectedItem = dataLs.getSelectedItem() // dataLs.find { it.isSelected }
                    )
                    Row {
                        TopTitleAndBaseTextField(
                            modifier = Modifier.weight(.53f),
                            titleId = R.string.yard_entry_coil_no_title,
                            tfValue = coilNoTxt, // TextFieldValue(coilNo, TextRange(coilNo.length)), // coilNoTxt
                            tfMaxLength = MAX_LENGTH_COIL_NO, //
                            tfIsError = isCoilNoTfError, //
//                            tfErrorMessage = "WARNING", // stringResource(R.string.yard_entry_coil_no_title)
                            onTfValueChanged = {
                                coilNoTxt = it //
//                                isCoilNoTfError = it.text.isNotEmpty() && MAX_LENGTH_COIL_NO != it.text.length //
                                onAction(YardEntryAction.TypingCoilNoTextField(it.text))
                            }
                        )
                        Spacer(modifier = Modifier.width(Dimens.space_textfield_to_textfield))
                        TopTitleAndBaseTextField(
                            modifier = Modifier.weight(.47f),
                            titleId = R.string.yard_entry_yyrrcct_title,
                            tfValue = TextFieldValue(yyrrcct, TextRange(yyrrcct.length)), // yyrrcctTxt
                            tfMaxLength = MAX_LENGTH_YYRRCCT, //
                            onTfValueChanged = {
//                                yyrrcctTxt = it
                                onAction(YardEntryAction.TypingYYRRCCTTextField(it.text))
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(Dimens.space_textfield_to_textfield))
                    TopTitleAndBaseTextField(
                        titleId = R.string.yard_entry_supplier_no_title,
                        tfValue = TextFieldValue(supplierNo, TextRange(supplierNo.length)), // supplierNoTxt
                        tfMaxLength = MAX_LENGTH_SUPPLIER_NO, //
                        onTfValueChanged = {
//                            supplierNoTxt = it
                            onAction(YardEntryAction.TypingSupplierNoTextField(it.text))
                        }
                    )

                    TitleAndGrayBgValueText(
                        modifier = Modifier.padding(
                            top = Dimens.padding_inner_content_to_top_title_gray_value,
                            bottom = Dimens.padding_below_bottom_title_gray_value
                        ),
                        titleId = R.string.yard_entry_display_yyrrcct_title,
                        titleTextStyle = FontStyles.txt28,
                        value = "$coilNo $yyrrcct $supplierNo", //
                    )
                }
            }
        }

        RowPairButton(
            onLeftButtonClick = { onAction(YardEntryAction.ClickSendButton) },
            onRightButtonClick = {
                coilNoTxt = TextFieldValue()
//                yyrrcctTxt = TextFieldValue()
//                supplierNoTxt = TextFieldValue()

                onAction(YardEntryAction.ClickClearButton)
            }
        )
    }
}

@Preview(showBackground = true, locale = "th")
@Composable
fun YardEntryScreenPreview() {
    YardEntryScreen(
        dataLs = listOf(DropdownUIModel("", display = "Product Label", true)),
        isCoilNoTfError = false,
        coilNo = "",
        yyrrcct = "",
        supplierNo = "",
        onAction = {}
    )
}