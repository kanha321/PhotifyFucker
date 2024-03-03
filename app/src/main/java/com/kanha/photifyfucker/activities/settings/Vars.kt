package com.kanha.photifyfucker.activities.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kanha.photifyfucker.res.sharedPrefsDisableRotation
import com.kanha.photifyfucker.res.sharedPrefsLaunchPhotify
import com.kanha.photifyfucker.util.SharedPrefsManager

var launchAutomatically by mutableStateOf(true)
var disableRotation by mutableStateOf(false)

fun loadSettings(sharedPrefsManager: SharedPrefsManager){
    launchAutomatically = sharedPrefsManager.getBoolean(sharedPrefsLaunchPhotify, true)
    disableRotation = sharedPrefsManager.getBoolean(sharedPrefsDisableRotation, false)
}

