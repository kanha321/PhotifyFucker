package com.kanha.photifyfucker.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanha.photifyfucker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KCardSingle(
    icon: Painter? = null,
    iconSize: Dp = 24.dp,
    name: String,
    description: String = "",
    textSize: TextUnit = 16.sp,
    descriptionTextSize: TextUnit = 14.sp,
    onClick: () -> Unit = {},

    // switch
    onCheckedChange: (Boolean) -> Unit = {},
    addKSwitch: Boolean = false,
    initialSwitchState: Boolean = false,
    switchEnabled: Boolean = true,
    onSwitchDisabledClick: () -> Unit = {},

    // button
    addButton: Boolean = false,
    buttonText: String = "Button",
    onButtonClick: () -> Unit = {},
    buttonEnabled: Boolean = true,
    onButtonDisabledClick: () -> Unit = {},

    // icon button
    addIconButton: Boolean = false,
    iconButtonIcon: Painter = painterResource(id = R.drawable.outline_delete_24),
    onIconButtonClick: () -> Unit = {},
    iconButtonEnabled: Boolean = true,
    onIconButtonDisabledClick: () -> Unit = {},
) {

    if (addButton && addKSwitch && addIconButton) {
        throw Exception("Only one item can be added to the card")
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 4.dp),
        onClick = {
            if (switchEnabled) onCheckedChange(!initialSwitchState) else onSwitchDisabledClick()
            if (buttonEnabled) onButtonClick() else onButtonDisabledClick()
            if (!addButton && !addKSwitch) onClick()
        },
    ) {
        Row(
            modifier = if(switchEnabled){
                Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            } else {
                Modifier
                    .background(
                        if (isSystemInDarkTheme()) Color(0xFF2E2E2E) else Color(0xFFECECEC)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null){
                Icon(
                    painter = icon,
                    contentDescription = null, // Provide a meaningful description if needed
                    modifier = Modifier.size(iconSize)
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = name,
                    fontSize = textSize,
                    fontWeight = FontWeight.Bold,
                )
                if (description.isNotEmpty()){
                    Text(
                        text = description,
                        fontSize = descriptionTextSize,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                }
            }

            // switch
            if (addKSwitch) {
                KSwitch(
                    state = initialSwitchState,
                    onCheckedChange = onCheckedChange,
                    enabled = switchEnabled
                )
            }

            // button
            if (addButton) {
                Button(
                    onClick = if (buttonEnabled) onButtonClick else onButtonDisabledClick,
                    enabled = buttonEnabled,
                ) {
                    Text(text = buttonText)
                }
            }

            // Icon Button
            if (addIconButton){
                IconButton(
                    onClick = if (iconButtonEnabled) onIconButtonClick else onIconButtonDisabledClick,
                    enabled = iconButtonEnabled,
                ) {
                    Icon(
                        painter = iconButtonIcon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}