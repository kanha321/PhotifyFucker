package com.kanha.photifyfucker.res

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kanha.photifyfucker.util.getRandomID

var userID = getRandomID()

var sharedPref = "<?xml version='1.0' encoding='utf-8' standalone='yes' ?>\n" +
        "<map>\n" +
        "    <string name=\"userId\">${userID}</string>\n" +
        "    <boolean name=\"onboardingShown\" value=\"false\" />\n" +
        "</map>\n"

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

var internalDataPath = if (android.os.Build.VERSION.SDK_INT < 30) "/data/data/ai.photify.app" else "/data_mirror/data_ce/null/0/ai.photify.app"
var externalDataPath = "/storage/emulated/0/Android/data/ai.photify.app"