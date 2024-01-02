package com.kanha.photifyfucker.util

import android.os.Build.VERSION.SDK_INT
import android.util.Log
import com.kanha.photifyfucker.res.*
import java.io.File
import java.nio.file.Files.exists

private const val TAG = "Ops"

fun copyWithShell(sourcePath: String, destinationPath: String) {
    var tempSourcePath = sourcePath
    if (sourcePath.startsWith("root")) {
        tempSourcePath = sourcePath.replace("root", "")
    } else if (sourcePath.startsWith("/root")) {
        tempSourcePath = sourcePath.replace("/root", "")
    }
    val command = "cp $tempSourcePath $destinationPath"
    mutableCommand = command
    Log.d(TAG, "copyFile: $command")
    val result = RunCommand.shell(command, updateSessionLog = true)
    if (result.isNotEmpty()) {
        mutableMimeType = "Error copying file: $result"
    } else {
        mutableMimeType = "File copied successfully"
    }
}

fun deleteWithShell(path: String) {
    val command = "rm -rf $path"
    mutableCommand = command
    Log.d(TAG, "deleteWithShell: $command")
    val result = RunCommand.shell(command)
    if (result.isNotEmpty()) {
        mutableMimeType = "Error deleting: $result"
    } else {
        mutableMimeType = "Deleted successfully"
    }
}


fun copyAllPhotify() {
    var count = 0
    task = "Extracting photos from Photify"
    // check if android version is 11 or above
    val dir = if (SDK_INT >= 30) {
        "/data_mirror/data_ce/null/0/ai.photify.app/files/"
    } else {
        "/data/data/ai.photify.app/files/"
    }

    val destinationPath = "/storage/emulated/0/Pictures/Photify"
    // create a directory if it doesn't exist
    if (!exists(File(destinationPath).toPath())) {
        RunCommand.shell("mkdir $destinationPath")
    }

    val files = RunCommand.shell("ls $dir", updateSessionLog = false)
    // add only those files to array whose name starts with "jpg"
    val filesArray = files.split("\n").toTypedArray()
    val jpgFiles = ArrayList<File>()
    for (file in filesArray) {
        if (file.startsWith("jpg")) {
            jpgFiles.add(File("$dir$file"))
        }
    }
    totalPhotos = jpgFiles.size
    copyAll = "Copying ${jpgFiles.size} files"
    // copy all files to the destination
    for (file in jpgFiles) {
        copyWithShell(file.absolutePath, "$destinationPath/saved_${file.name}.jpg")
        count++
        progress = "Copied $count/${jpgFiles.size} files"
    }
}

fun separateAlternately() {
    var count = 0
    task = "Separating watermarked &\nnon-watermarked photos"
    val destinationPath = "/storage/emulated/0/Pictures/Photify"
    // in this directory there are some files which select them alternately and store them in 2 separate arrays
    val files = RunCommand.shell("ls $destinationPath")
    val filesArray = files.split("\n").toTypedArray()
    val filesArray1 = ArrayList<File>()
    val filesArray2 = ArrayList<File>()
    for (i in filesArray.indices) {
        if (i % 2 == 0) {
            filesArray1.add(File("$destinationPath/${filesArray[i]}"))
        } else {
            filesArray2.add(File("$destinationPath/${filesArray[i]}"))
        }
    }
    // create 2 directories (if it doesn't exist) to store the alternates named "dir1" and "dir2"
    val destinationPath1 = "/storage/emulated/0/Pictures/Photify/dir1"
    val destinationPath2 = "/storage/emulated/0/Pictures/Photify/dir2"
    if (!exists(File(destinationPath1).toPath())) {
        RunCommand.shell("mkdir $destinationPath1")
    }
    if (!exists(File(destinationPath2).toPath())) {
        RunCommand.shell("mkdir $destinationPath2")
    }
    // copy the alternates to the 2 directories
    task = "Collecting non-watermarked photos"
    for (file in filesArray1) {
        copyWithShell(file.absolutePath, "$destinationPath1/${file.name}")
        count++
        progress = "Collected $count/${totalPhotos / 2} files"
    }
    count = 0
    task = "Collecting watermarked photos"
    for (file in filesArray2) {
        copyWithShell(file.absolutePath, "$destinationPath2/${file.name}")
        count++
        progress = "Collected $count/${totalPhotos / 2} files"
    }
}

fun deleteEverythingExcept(dir: Int) {
    task = "Cleaning Up Extras"
    var count = 0
    val destinationPath = "/storage/emulated/0/Pictures/Photify"
    if (!exists(File("$destinationPath/dir1").toPath()) || !exists(File("$destinationPath/dir2").toPath())) {
        mutableMimeType = "dir1 or dir2 doesn't exist"
        mutableCommand = exit
        return
    }
    val files = RunCommand.shell("ls $destinationPath")
    val filesArray = files.split("\n").toTypedArray()
    for (file in filesArray) {
        if (file != "dir1" && file != "dir2") {
            deleteWithShell("$destinationPath/$file")
            count++
            progress = "Deleted $count/$totalPhotos files"
        }
    }
    task = "Deleting Watermarked Photos Directory"
    if (dir == 1) {
        deleteWithShell("$destinationPath/dir2")
        val tempDestinationPath = "$destinationPath/dir1"
        val tempFiles = RunCommand.shell("ls $tempDestinationPath")
        val tempFilesArray = tempFiles.split("\n").toTypedArray()
        for (file in tempFilesArray) {
            copyWithShell("$tempDestinationPath/$file", "$destinationPath/$file")
        }
        task = "Deleting Empty Directory"
        deleteWithShell("$destinationPath/dir1")
    } else if (dir == 2) {
        deleteWithShell("$destinationPath/dir1")
        val tempDestinationPath = "$destinationPath/dir2"
        val tempFiles = RunCommand.shell("ls $tempDestinationPath")
        val tempFilesArray = tempFiles.split("\n").toTypedArray()
        for (file in tempFilesArray) {
            copyWithShell("$tempDestinationPath/$file", "$destinationPath/$file")
        }
        deleteWithShell("$destinationPath/dir2")
        // "/storage/emulated/0/Pictures/Photify" rename Photify folder to Photify1
        RunCommand.shell("mv $destinationPath $destinationPath" + "1")
    }
}