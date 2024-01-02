package com.kanha.photifyfucker.util

import android.content.Context
import com.kanha.photifyfucker.res.commands
import com.kanha.photifyfucker.res.externalDataPath
import com.kanha.photifyfucker.res.internalDataPath
import com.kanha.photifyfucker.res.progress
import com.kanha.photifyfucker.res.sharedPref
import com.kanha.photifyfucker.res.task
import java.io.File
import java.util.UUID

fun writeToFile(context: Context){
    val filename = "photifyAI.xml"
    val fileContents = sharedPref

    context.openFileOutput(filename, Context.MODE_PRIVATE).use {
        it.write(fileContents.toByteArray())
    }
}
fun getRandomID(): String{
    return UUID.randomUUID().toString().replace("-", "").substring(0, 16)
}

fun writeToFileShell(context: Context){
    val fileName = "$internalDataPath/shared_prefs/photifyAI.xml"
    val fileContents = sharedPref

    task = "Renewing Credits"
    progress = "Almost There..."
    val command = "echo \"$fileContents\" > $fileName"
    val output1 = RunCommand.shell(command)
    val output2 = RunCommand.shell("chmod 660 $fileName")
}


fun terminateApp() {
    task = "Killing Photify"
    val command = "am force-stop ai.photify.app"
    RunCommand.shell(command)
    progress = "Killed"
}

fun createData(){
    task = "Preparing Stuffs"
    progress = "0/5"
    RunCommand.shell("mkdir $internalDataPath")
    progress = "1/5"
//    RunCommand.shell("mkdir $externalDataPath")
    progress = "2/5"
    RunCommand.shell("mkdir $internalDataPath/code_cache/")
    RunCommand.shell("chmod 771 $internalDataPath/code_cache/")
    progress = "3/5"
    RunCommand.shell("mkdir $internalDataPath/cache/")
    RunCommand.shell("chmod 771 $internalDataPath/cache/")
    progress = "4/5"
    RunCommand.shell("mkdir $internalDataPath/shared_prefs/")
    RunCommand.shell("chmod 771 $internalDataPath/shared_prefs/")
    progress = "5/5"
}

fun clearData(){
    task = "Clearing Data"
    var count = 0

    RunCommand.shell("ls $internalDataPath", updateSessionLog = false)
    // store the folders in an array
    val folders = RunCommand.shell("ls $internalDataPath").split("\n").toTypedArray()
    val totatCount = folders.size - 1
    progress = "$count/$totatCount"
    // delete all the folders except the shared_prefs folder
    task = "Clearing Folders"
    for (folder in folders) {
        if (folder != "shared_prefs") {
            deleteWithShell("$internalDataPath/$folder")
            count++
            progress = "$count/$totatCount"
        }
    }
    task = "Clearing Files"
    val files = RunCommand.shell("ls $internalDataPath/shared_prefs").split("\n").toTypedArray()
    for (file in files) {
        if (file != "photifyAI.xml") {
            deleteWithShell("$internalDataPath/shared_prefs/$file")
            count++
            progress = "$count/${files.size-1}"
        }
    }
}