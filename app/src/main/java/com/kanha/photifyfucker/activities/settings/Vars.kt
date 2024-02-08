package com.kanha.photifyfucker.activities.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kanha.photifyfucker.util.SharedPrefsManager

var launchAutomatically by mutableStateOf(true)

fun loadSettings(sharedPrefsManager: SharedPrefsManager){
    launchAutomatically = sharedPrefsManager.getBoolean("launchAutomatically", true)
}