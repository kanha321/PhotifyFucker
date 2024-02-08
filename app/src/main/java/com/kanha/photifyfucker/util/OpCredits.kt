package com.kanha.photifyfucker.util

import android.content.Context
import android.util.Xml
import com.kanha.photifyfucker.model.SSAID
import com.kanha.photifyfucker.res.photifyInternalDataPath
import com.kanha.photifyfucker.res.photifyAIXML
import com.kanha.photifyfucker.res.progress
import com.kanha.photifyfucker.res.task
import org.xmlpull.v1.XmlPullParser
import java.io.StringReader
import kotlin.random.Random

private const val TAG = "OpCredits"
fun writeToFile(context: Context, fileContents: String = getXMLData(context)){
    progress = "Writing content to file"
    val filename = "photifyAI.xml"

    context.openFileOutput(filename, Context.MODE_PRIVATE).use {
        it.write(fileContents.toByteArray())
    }
    progress = "Written Successfully"
}
fun getRandomID(length: Int = 16): String {
    val characters = "0123456789abcdef"
    return (1..length)
        .map { Random.nextInt(characters.length) }
        .map(characters::get)
        .joinToString("")
}

fun getXMLData(context: Context): String {
    val xml = readFileFromAssets(context = context, "photifyAI.xml")
    return xml.replace("0123456789abcdef", getRandomID())
}

fun storeXMLData(xmlFilePath: String = "$photifyInternalDataPath/shared_prefs/photifyAI.xml"): String {
    return RunCommand.shell("cat $xmlFilePath", updateSessionLog = false)
}

fun writeToFileShell(context: Context, fileContents: String = getXMLData(context)) {
    val fileName = "$photifyInternalDataPath/shared_prefs/photifyAI.xml"

    task = "Renewing Credits"
    progress = "Almost There..."
    val command = "echo \"$fileContents\" > $fileName"
    val output1 = RunCommand.shell(command)
    val output2 = RunCommand.shell("chmod 660 $fileName")
}

fun deletePhotoData(){
    progress = "Deleting Photo Data"
    val regex = "<string name=\"currIdentityPhoto\">\\{.*?\\}</string>".toRegex()
    photifyAIXML = photifyAIXML.replaceFirst(regex, "")
    progress = "Deleted"
}

fun changePhotoCount(count: Int = 0) {
    progress = "Changing Photo Count"
    val regex = "<long name=\"photoCount\" value=\"\\d+\" />".toRegex()
    photifyAIXML = photifyAIXML.replaceFirst(regex, "<long name=\"photoCount\" value=\"$count\" />")
    progress = "Changed"
}

fun updateUserID() {
    progress = "Updating User ID"
    val regex = "<string name=\"userId\">[a-zA-Z0-9]{16}</string>".toRegex()
    photifyAIXML = photifyAIXML.replaceFirst(regex, "<string name=\"userId\">${getRandomID()}</string>")
    progress = "Updated"
}

fun terminateApp() {
    progress = "Killing Photify"
    val command = "am force-stop ai.photify.app"
    RunCommand.shell(command)
    progress = "Killed"
}

fun clearData(){
    task = "Clearing Data"
    var count = 0

    RunCommand.shell("ls $photifyInternalDataPath", updateSessionLog = false)
    // store the folders in an array
    // if filename contains $ then escape it with \
    val folders = RunCommand.shell("ls $photifyInternalDataPath")
        .replace("$", "\\$")
        .split("\n").toTypedArray()
    val totatCount = folders.size - 1
    progress = "$count/$totatCount"
    // delete all the folders except the shared_prefs folder
    task = "Clearing Folders"
    for (folder in folders) {
        if (folder != "shared_prefs") {
            deleteWithShell("$photifyInternalDataPath/$folder")
            count++
            progress = "$count/$totatCount"
        }
    }
    task = "Clearing Files"
    val files = RunCommand.shell("ls $photifyInternalDataPath/shared_prefs")
        .replace("$", "\\$")
        .split("\n").toTypedArray()
    for (file in files) {
        if (file != "photifyAI.xml") {
            deleteWithShell("$photifyInternalDataPath/shared_prefs/$file")
            count++
            progress = "$count/${files.size-1}"
        }
    }
}