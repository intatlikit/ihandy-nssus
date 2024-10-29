package com.nssus.ihandy.ui.basecomposable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.nssus.ihandy.R
import com.nssus.ihandy.ui.theme.Black60
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseDialog(
    onTouchOutsideDialog: () -> Unit,
    dialogContent: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black60)
            .zIndex(.1f)
    ) {
        AlertDialog(
            onDismissRequest = { onTouchOutsideDialog() },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            dialogContent()
        }
    }
}

@Composable
fun BaseMsgAndIconDialog(
    @DrawableRes icon: Int,
    // Title
    @StringRes titleId: Int,
    titleTextAlign: TextAlign = TextAlign.Start,
    // Description
    description: String? = null,
    descrTextAlign: TextAlign = TextAlign.Start,
    onCloseDialog: () -> Unit
) {
    BaseDialog(onTouchOutsideDialog = { onCloseDialog() }) {
        LazyColumn {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp) // outside-dialog's horizontal space
                        .background(Color.White, RoundedCornerShape(36.dp))
                        .padding(vertical = 20.dp, horizontal = 30.dp) // inner-dialog's space
                ) {
                    Row {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = titleId),
                                style = FontStyles.txt28,
                                fontWeight = FontWeight.Medium,
                                textAlign = titleTextAlign
                            )
                            description?.let { desc ->
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = desc,
                                    style = FontStyles.txt16,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = descrTextAlign
                                )
                            }
                        }
                        Image(
                            modifier = Modifier.size(40.dp),
                            painter = painterResource(id = icon),
                            contentDescription = "Dialog Icon"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BaseTitleMsgWithButtonDialog(
    // Icon
    @DrawableRes icon: Int = R.drawable.ic_dialog_warning,
    // Title
    @StringRes titleId: Int = R.string.common_dialog_warning_title,
    titleTextAlign: TextAlign = TextAlign.Center,
    // Description
    description: String? = null,
    descrTextAlign: TextAlign = TextAlign.Center,
    // Button
    leftButtonTextId: Int = R.string.common_continue_button,
    rightButtonTextId: Int = R.string.common_cancel_button,
    // Action
    onCloseDialog: () -> Unit,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit
) {
    BaseDialog(onTouchOutsideDialog = { onCloseDialog() }) {
        LazyColumn {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp) // outside-dialog's horizontal space
                        .background(Color.White, RoundedCornerShape(36.dp))
                        .padding(vertical = 20.dp, horizontal = 14.dp) // inner-dialog's space
                ) {
                    Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = titleId),
                                style = FontStyles.txt28,
                                fontWeight = FontWeight.Medium,
                                textAlign = titleTextAlign
                            )
                            description?.let { desc ->
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = desc,
                                    style = FontStyles.txt16,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = descrTextAlign
                                )
                            }
                        }
                        Image(
                            modifier = Modifier.size(40.dp),
                            painter = painterResource(id = icon),
                            contentDescription = "Dialog Icon"
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth(),
                        color = Color.Black
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 26.dp)
                            .padding(horizontal = 26.dp)
                    ) {
                        BaseDialogButton(
                            modifier = Modifier.weight(1f),
                            text = stringResource(id = leftButtonTextId),
                            onButtonClick = { onLeftButtonClick() }
                        )
                        Spacer(modifier = Modifier.width(Dimens.space_between_button_to_button))
                        BaseDialogButton(
                            modifier = Modifier.weight(1f),
                            text = stringResource(id = rightButtonTextId),
                            onButtonClick = { onRightButtonClick() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CompleteDialog(onCloseDialog: () -> Unit) {
    BaseMsgAndIconDialog(
        icon = R.drawable.ic_dialog_green_tick,
        titleId = R.string.common_dialog_sent_completed_title,
        onCloseDialog = { onCloseDialog() }
    )
}

@Composable
fun CompleteWithDescriptionDialog(
    description: String,
    onCloseDialog: () -> Unit
) {
    BaseMsgAndIconDialog(
        icon = R.drawable.ic_dialog_green_tick,
        titleId = R.string.common_dialog_sent_completed_title,
        description = description,
        onCloseDialog = { onCloseDialog() }
    )
}

@Composable
fun FailDialog(onCloseDialog: () -> Unit) {
    BaseMsgAndIconDialog(
        icon = R.drawable.ic_dialog_red_cross,
        titleId = R.string.common_dialog_sent_failed_title,
        titleTextAlign = TextAlign.Center,
        onCloseDialog = { onCloseDialog() }
    )
}

@Composable
fun FailWithDescriptionDialog(
    description: String,
    onCloseDialog: () -> Unit
) {
    BaseMsgAndIconDialog(
        icon = R.drawable.ic_dialog_red_cross,
        titleId = R.string.common_dialog_sent_failed_title,
        titleTextAlign = TextAlign.Center,
        description = description,
        descrTextAlign = TextAlign.Center,
        onCloseDialog = { onCloseDialog() }
    )
}

@Preview(showBackground = true, locale = "th")
@Composable
fun BaseDialogPreview() {
    BaseTitleMsgWithButtonDialog(
        description = "Coil No. AAAAAAAA not found",
        onCloseDialog = {}, onRightButtonClick = {}, onLeftButtonClick = {}
    )
//    CompleteDialog{}
//    CompleteWithDescriptionDialog(description = "Coil No. AAAAAAAA"){}
//    FailDialog{}
//    FailWithDescriptionDialog(description = "Coil No. AAAAAAAA") {}
//    BaseMsgAndIconDialog(
//        icon = R.drawable.ic_dialog_green_tick, // ic_dialog_red_cross ic_dialog_green_tick
//        titleId = R.string.common_dialog_sent_completed_title, // common_dialog_sent_completed_title common_dialog_sent_failed_title
//        titleTextAlign = TextAlign.Start,
//        description = "Coil No. AAAAAAAA",
//        descrTextAlign = TextAlign.Center, // Start Center
//        onCloseDialog = {}
//    )
}