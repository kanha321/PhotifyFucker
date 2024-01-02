package com.kanha.photifyfucker.util

import android.annotation.SuppressLint
import com.kanha.photifyfucker.res.*
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun appendLogs(output: String, error: String, command: String) {
    val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(System.currentTimeMillis())
    val commandTime = SimpleDateFormat("HH:mm:ss.SSS").format(System.currentTimeMillis())
    val timeLine = "--------$currentTime--------\n"
    val commandText = "> $command\n"
    val sessionLog = "$timeLine$commandText$output$error\n---------------------------------------\n\n"
    val outputLog  = "$timeLine$commandText$output\n---------------------------------------\n\n"
    val errorLog   = "$timeLine$commandText$error\n---------------------------------------\n\n"
    val commands = "$commandTime> $command\n"
    COMMANDS += commands
    if (output.isNotBlank() || error.isNotBlank())
        SESSION_LOG += sessionLog
    if (output.isNotBlank())
        OUTPUT_LOG += outputLog
    if (error.isNotBlank())
        ERROR_LOG += errorLog
}

fun checkRootOnHost(): String {
    val output: String = try {
        RunCommand.shell(command = "su -c id", asRoot = false)
    } catch (e: Exception) {
        "Failed"
    }
    return if (output.contains("Permission denied"))
        "Root Permission Denied"
    else if (output.contains("Failed"))
        "This device is not rooted"
    else
        "Root access granted"
}