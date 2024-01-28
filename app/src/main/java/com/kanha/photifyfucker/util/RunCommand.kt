package com.kanha.photifyfucker.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object RunCommand {

    private const val TAG = "RunCommand"

    fun shell(
        command: String,
        asRoot: Boolean = true,
        updateSessionLog: Boolean = true
    ): String {
        return try {
            val process: Process
            if (asRoot) {
                process = Runtime.getRuntime().exec("su")
                process.outputStream.write(command.toByteArray())
                process.outputStream.flush()
                process.outputStream.close()
            } else {
                process = Runtime.getRuntime().exec(command)
            }

            val output = StringBuilder()
            val error = StringBuilder()

            val outputThread = Thread {
                val reader = BufferedReader(
                    InputStreamReader(process.inputStream)
                )
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    output.append(line).append("\n")
                }
                reader.close()
            }
            outputThread.start()

            val errorThread = Thread {
                val errorReader = BufferedReader(
                    InputStreamReader(process.errorStream)
                )
                var line: String?
                while (errorReader.readLine().also { line = it } != null) {
                    error.append(line).append("\n")
                }
                errorReader.close()
            }
            errorThread.start()

            outputThread.join()
            errorThread.join()

            process.waitFor()

            if (updateSessionLog)
                appendLogs(output.toString().trim(), error.toString().trim(), command.trim())
//            Log.d(TAG, "shell: ${error.toString().trim() + output.toString().trim()}")

            return error.toString().trim() + output.toString().trim()

        } catch (e: IOException) {
            throw RuntimeException(e)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }

//    fun shell(
//        command: String,
//        asRoot: Boolean = true,
//    ): String {
//        return try {
//            val process: Process
//            if (asRoot) {
//                process = Runtime.getRuntime().exec("su")
//                process.outputStream.write(command.toByteArray())
//                process.outputStream.flush()
//                process.outputStream.close()
//            } else {
//                process = Runtime.getRuntime().exec(command)
//            }
//
//            val output = StringBuilder()
//            val error = StringBuilder()
//
//            val outputThread = Thread {
//                val reader = BufferedReader(
//                    InputStreamReader(process.inputStream)
//                )
//                var line: String?
//                while (reader.readLine().also { line = it } != null) {
//                    output.append(line).append("\n")
//                }
//                reader.close()
//            }
//            outputThread.start()
//
//            val errorThread = Thread {
//                val errorReader = BufferedReader(
//                    InputStreamReader(process.errorStream)
//                )
//                var line: String?
//                while (errorReader.readLine().also { line = it } != null) {
//                    error.append(line).append("\n")
//                }
//                errorReader.close()
//            }
//            errorThread.start()
//
//            outputThread.join()
//            errorThread.join()
//
//            process.waitFor()
//
//            return error.toString().trim() + output.toString().trim()
//
//        } catch (e: IOException) {
//            throw RuntimeException(e)
//        } catch (e: InterruptedException) {
//            throw RuntimeException(e)
//        }
//    }

    fun isRooted(): Boolean {
        val command = "id"
        val result = shell(command)
        return result.contains("uid=0")
    }
}