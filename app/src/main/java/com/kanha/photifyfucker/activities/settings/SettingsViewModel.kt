package com.kanha.photifyfucker.activities.settings

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kanha.photifyfucker.util.SharedPrefsManager

class SettingsViewModel(
    context: Context
): ViewModel() {

    var isDialogShown by mutableStateOf(false)
        private set

    val sharedPrefsManager = SharedPrefsManager(context = context)

    fun onConfirmClick() {
        isDialogShown = false
    }
    fun onCancelClick() {
        isDialogShown = false
    }
    fun onButtonClick() {
        isDialogShown = true
    }

    fun launchAutomatically(){
        launchAutomatically = !launchAutomatically
        sharedPrefsManager.saveBoolean("launchAutomatically", launchAutomatically)
    }
}