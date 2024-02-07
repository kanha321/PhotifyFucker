package com.kanha.photifyfucker.util

import android.content.Intent
import android.net.Uri
import com.kanha.photifyfucker.res.photifyInternalDataPath
import com.kanha.photifyfucker.res.photifyStoragePath
import java.io.File
import java.nio.file.Files.exists

fun getSharedFileName(intent: Intent): String? {
    val action = intent.action
    val type = intent.type

    if (Intent.ACTION_SEND == action && type != null) {
        val sharedFileUri = intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
        if (sharedFileUri != null) {
            val fileName = File(sharedFileUri.path).name
            return fileName
        }
    }
    return null
}

fun getNonWaterMarkedImage(filename: String){

    val dir = "$photifyInternalDataPath/files/"

    val files = RunCommand.shell("ls $dir", updateSessionLog = false)
    // add only those files to array whose name starts with "jpg"
    val filesArray = files.split("\n").toTypedArray()
    val jpgFiles = ArrayList<File>()
    for (file in filesArray) {
        if (file.startsWith("photify")) {
            jpgFiles.add(File("$dir$file"))
        }
    }
    val index = linearSearch(filename, jpgFiles)
    val outputDir = "$photifyStoragePath/Favorites/"
    if (!exists(File(outputDir).toPath())) {
        RunCommand.shell("mkdir $outputDir")
    }
    copyWithShell(jpgFiles[index - 1].absolutePath, outputDir)
}

fun linearSearch(filename: String, images: ArrayList<File>): Int {
    for (index in images.indices) {
        if (images[index].name == filename) {
            return index
        }
    }
    return -1
}