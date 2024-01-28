package com.kanha.photifyfucker.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.kanha.photifyfucker.res.*
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
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
    val commands = "$commandTime> $command\n\n"
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
    else if (output == "Failed")
        "This device is not rooted"
    else
        "Root access granted"
}

fun readFileFromAssets(context: Context, fileName: String): String {
    return try {
        val inputStream = context.assets.open(fileName)
        val reader = InputStreamReader(inputStream)
        reader.readText()
    } catch (e: IOException) {
        e.printStackTrace()
        ""
    }
}

fun loadBitmapFromRootAccess(filePath: String): Bitmap? {
    try {
        val processOutput = RunCommand.shell("cat $filePath")
        val inputStream: InputStream = processOutput.byteInputStream()
        return BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}