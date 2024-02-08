package com.kanha.photifyfucker.activities.settings

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kanha.photifyfucker.R
import com.kanha.photifyfucker.composables.KCardSingle
import com.kanha.photifyfucker.composables.Toolbar
import com.kanha.photifyfucker.res.isDynamicColor
import com.kanha.photifyfucker.res.sharedPrefsIsDynamicColor
import com.kanha.photifyfucker.res.themeHeader
import com.kanha.photifyfucker.util.KToast
import com.kanha.quizzy.composable.LineHeader
import com.kanha.quizzy.composable.ThemeDialog

var texts by mutableStateOf(arrayListOf<String>())

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    context: Context
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            Toolbar(
                context = context,
                title = "Settings",
            ) {}
            Spacer(modifier = Modifier.size(16.dp))
            LineHeader("Theme")
            Spacer(modifier = Modifier.size(8.dp))
            KCardSingle(
                name = "App Theme",
                description = "Select theme for the app",
                icon = painterResource(R.drawable.outline_brightness_24),
                addButton = true,
                buttonText = themeHeader,
                onButtonClick = {
                    viewModel.onButtonClick()
                }
            )
            KCardSingle(
                name = "Material You",
                description = "Wallpaper based colors",
                icon = painterResource(R.drawable.outline_color_lens_24),
                addKSwitch = true,
                initialSwitchState = isDynamicColor,
                onCheckedChange = {
                    isDynamicColor = it
                    viewModel.sharedPrefsManager.saveBoolean(
                        sharedPrefsIsDynamicColor,
                        isDynamicColor
                    )
                },
                switchEnabled = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
                onSwitchDisabledClick = {
                    KToast.show(
                        context,
                        "Not Available for devices below Android 12",
                    )
                }
            )
            Spacer(modifier = Modifier.size(16.dp))
            LineHeader("Functionality")
            Spacer(modifier = Modifier.size(8.dp))
            KCardSingle(
                name = "Clear Photify Data",
                description = "Clears all the data of photify",
                icon = painterResource(R.drawable.outline_delete_24),
                onClick = {
                    KToast.show(context, "Why the fuck you wanna do this")
                }
            )
            KCardSingle(
                name = "Launch automatically",
                description = "launch Photify after regeneration",
                addKSwitch = true,
                initialSwitchState = launchAutomatically,
                icon = painterResource(R.drawable.outline_launch_24),
                onCheckedChange = {
                    viewModel.launchAutomatically()
                }
            )
        }
    }
    if (viewModel.isDialogShown) {
        ThemeDialog(
            onConfirm = {
                viewModel.onConfirmClick()
            },
            onDismiss = {
                viewModel.onCancelClick()
            },
            context = context
        )
    }
}