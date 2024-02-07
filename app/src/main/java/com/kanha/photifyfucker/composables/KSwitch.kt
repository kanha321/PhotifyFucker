package com.kanha.photifyfucker.composables

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.kanha.photifyfucker.util.NoRippleTheme

@Composable
fun KSwitch(
    state: Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
    enabled : Boolean = true
) {

    CompositionLocalProvider(
        LocalRippleTheme provides NoRippleTheme
    ) {
        Switch(
            checked = state,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            thumbContent = if (state) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize)
                    )
                }
            } else {
                {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize)
                    )
                }
            }
        )
    }
}