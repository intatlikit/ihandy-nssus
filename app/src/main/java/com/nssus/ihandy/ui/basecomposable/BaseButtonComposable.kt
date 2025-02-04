package com.nssus.ihandy.ui.basecomposable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nssus.ihandy.ui.theme.Black50
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles
import com.nssus.ihandy.ui.theme.Sky28

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    textButtonModifier: Modifier = Modifier.padding(6.dp),
    text: String,
    textStyle: TextStyle = FontStyles.txt24.copy(fontWeight = FontWeight.SemiBold),
    textColor: Color = Color.Black,
    borderColor: Color = Black50,
    buttonColor: Color = Sky28,
    shape: Shape = RoundedCornerShape(corner = CornerSize(Dimens.size_button_corner_small)),
    enabled: Boolean = true,
    elevation: ButtonElevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 0.dp),
    onButtonClick: () -> Unit
) {
    Button(
        modifier = modifier,
        border = BorderStroke(Dimens.border_button, borderColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = borderColor,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = borderColor
        ),
        enabled = enabled,
        shape = shape,
        elevation = elevation,
        onClick = { onButtonClick() }
    ) {
        Text(
            modifier = textButtonModifier,
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}