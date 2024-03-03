package com.kanha.photifyfucker.res

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

//var sharedPref = "<?xml version='1.0' encoding='utf-8' standalone='yes' ?>\n" +
//        "<map>\n" +
//        "    <string name=\"userId\">${userID}</string>\n" +
//        "    <boolean name=\"onboardingShown\" value=\"true\" />\n" +
//        "    <boolean name=\"isPrem\" value=\"true\" />\n" +
//        "</map>\n"

var photifyAIXML by mutableStateOf("")
var prompts = arrayListOf("")

var SESSION_LOG by mutableStateOf("session.log\n\n")
var OUTPUT_LOG by mutableStateOf("output.log\n\n")
var ERROR_LOG by mutableStateOf("error.log\n\n")
var COMMANDS by mutableStateOf("Commands:\n\n")

var sessionLog = "Session Log"
var outputLog = "Output Log"
var errorLog = "Error Log"
var commands = "Commands"

var ACTIVE_LOG by mutableStateOf(SESSION_LOG)
var SELECTED_LOG_TYPE by mutableStateOf(sessionLog)


const val exit = "Exit??"

val androidVersion: String = "Android version: ${android.os.Build.VERSION.RELEASE}"
var mutableMimeType by mutableStateOf(androidVersion)
var mutableCommand by mutableStateOf("")
var copyAll by mutableStateOf("Copy All")
var task by mutableStateOf("")
var progress by mutableStateOf("")
var isCopying = false
var totalPhotos: Int = 0

@SuppressLint("SdCardPath")
var photifyInternalDataPath = if (android.os.Build.VERSION.SDK_INT < 30) "/data/data/ai.photify.app" else "/data_mirror/data_ce/null/0/ai.photify.app"
var photifyExternalDataPath = "/storage/emulated/0/Android/data/ai.photify.app"

var photifyStoragePath = "/storage/emulated/0/Pictures/PhotifyFucker"

@SuppressLint("SdCardPath")
var fuckerInternalDataPath = if (android.os.Build.VERSION.SDK_INT < 30) "/data/data/com.kanha.photifyfucker" else "/data_mirror/data_ce/null/0/com.kanha.photifyfucker"
var fuckerInternalFilePath = "$fuckerInternalDataPath/files"

// SharedPrefs

var sharedPrefsName = "PhotifyFuckerPrefs"
var sharedPrefsThemeType = "themeType"
var sharedPrefsIsDynamicColor = "isDynamicColor"
var sharedPrefsLaunchPhotify = "launchAutomatically"
var sharedPrefsDisableRotation = "disableRotation"


// Theming stuffs

var isDarkTheme by mutableStateOf(true)
var isSystemTheme by mutableStateOf(true)
var isDynamicColor by mutableStateOf(false)

var themeType by mutableIntStateOf(0)

var systemTheme = "System"
var lightTheme = "Light"
var darkTheme = "Dark"

var themeHeader by mutableStateOf(systemTheme)

enum class ThemeType(val value: Int) {
    LIGHT(0),
    DARK(1),
    SYSTEM(2)
}