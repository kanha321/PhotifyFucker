package com.kanha.quizzy.composable

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanha.photifyfucker.R
import com.kanha.photifyfucker.res.ThemeType
import com.kanha.photifyfucker.res.darkTheme
import com.kanha.photifyfucker.res.isDarkTheme
import com.kanha.photifyfucker.res.isSystemTheme
import com.kanha.photifyfucker.res.lightTheme
import com.kanha.photifyfucker.res.sharedPrefsThemeType
import com.kanha.photifyfucker.res.systemTheme
import com.kanha.photifyfucker.res.themeHeader
import com.kanha.photifyfucker.res.themeType
import com.kanha.photifyfucker.util.SharedPrefsManager

@Composable
fun ThemeDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    context: Context
) {

    val sharedPrefsManager = SharedPrefsManager(context)

    val options = listOf(
        lightTheme,
        darkTheme,
        systemTheme
    )
    var selectedOption by rememberSaveable { mutableStateOf(options[themeType]) }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.outline_color_lens_24),
                contentDescription = "Icon",
                modifier = Modifier.size(32.dp)
            )
        },
        title = {
            Column(
                modifier = Modifier
            ) {
                Text(
                    text = "Select Theme",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    options.forEach {
                        Button(
                            onClick = {
                                selectedOption = it
                            },
                            contentPadding = ButtonDefaults.TextButtonContentPadding,
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.onBackground,
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ){
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = selectedOption == it,
                                        onClick = {
                                            selectedOption = it
                                        },
                                    )
                                    Text(
                                        text = it,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {

            }
        },
        confirmButton = {
            Button(
                onClick = {
                    when (selectedOption) {
                        "Light" -> {
                            isDarkTheme = false
                            isSystemTheme = false
                            sharedPrefsManager.saveInt(sharedPrefsThemeType, ThemeType.LIGHT.value)
                            themeHeader = lightTheme
                            themeType = ThemeType.LIGHT.value
                        }
                        "Dark" -> {
                            isDarkTheme = true
                            isSystemTheme = false
                            sharedPrefsManager.saveInt(sharedPrefsThemeType, ThemeType.DARK.value)
                            themeHeader = darkTheme
                            themeType = ThemeType.DARK.value
                        }
                        else -> {
                            isSystemTheme = true
                            sharedPrefsManager.saveInt(sharedPrefsThemeType, ThemeType.SYSTEM.value)
                            themeHeader = systemTheme
                            themeType = ThemeType.SYSTEM.value
                        }
                    }
                    onConfirm()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.primary,
                )
            ) {
                Text("Cancel")
            }
        }
    )
}