package com.nssus.ihandy.ui.yardentry.coildetlls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nssus.ihandy.R
import com.nssus.ihandy.model.yardentry.CoilDetailItem
import com.nssus.ihandy.model.yardentry.YardEntryAction
import com.nssus.ihandy.model.yardentry.YardEntryUIStateModel
import com.nssus.ihandy.ui.theme.FontStyles
import com.nssus.ihandy.ui.theme.MainGray

@Composable
fun YardEntryCoilDetlLsScreen(
    uiYardEntrySt: YardEntryUIStateModel,
    onAction: (YardEntryAction) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(vertical = 20.dp)
//        , horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier.size(44.dp),
                onClick = { onAction(YardEntryAction.ClickBackToMainYardEntryScreen) }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon",
                    tint = Color.Black
                )
            }
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) { //                .background(Color.White)
//                .padding(vertical = 20.dp)
//                .weight(1f)
            if (uiYardEntrySt.coilNoLs.isEmpty()) {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        text = stringResource(id = R.string.yard_entry_coil_detl_ls_empty_msg),
                        style = FontStyles.txt20,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(uiYardEntrySt.coilNoLs) { coilDetl ->
                    Column(modifier = Modifier.clickable { onAction(YardEntryAction.SelectCoilDetailItem(coilDetl)) }) {
//                        Row(
//                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            if (coilDetl.isSelectedRemove) {
//                                Image(
//                                    modifier = Modifier.size(26.dp),
//                                    painter = painterResource(id = R.drawable.ic_dialog_green_tick),
//                                    contentDescription = "Tick Icon"
//                                )
//                                Spacer(modifier = Modifier.width(8.dp))
//                            }
//                            Text(
//                                text = coilDetl.coilNo,
//                                style = FontStyles.txt20,
//                                color = Color.Black
//                            )
//                        }
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            text = coilDetl.coilNo,
                            style = FontStyles.txt20,
                            color = Color.Black
                        )
                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth(),
                            color = MainGray
                        )
                    }
                }
            }
        }

//        BaseDialogButton(
//            text = stringResource(id = R.string.common_ok_button),
//            onButtonClick = { onAction(YardEntryAction.ClickConfirmRemoveSelectedCoilNoLs) }
//        )
    }
}

@Preview(showBackground = true, locale = "th")
@Composable
fun YardEntryCoilDetlLsScreenPreview() {
    YardEntryCoilDetlLsScreen(
        uiYardEntrySt = YardEntryUIStateModel(
            coilNoLs = listOf(
                CoilDetailItem(
                    coilNo = "AAAAAAAA1"
                ),
                CoilDetailItem(
                    coilNo = "AAAAAAAA2",
                    isSelectedRemove = true
                )
            )
        ),
        onAction = {}
    )
}