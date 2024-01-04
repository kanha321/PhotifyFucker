package com.kanha.photifyfucker.util

import android.content.Context
import android.util.Xml
import com.kanha.photifyfucker.model.SSAID
import com.kanha.photifyfucker.res.internalDataPath
import com.kanha.photifyfucker.res.progress
import com.kanha.photifyfucker.res.task
import org.xmlpull.v1.XmlPullParser
import java.io.StringReader
import kotlin.random.Random

private const val TAG = "OpCredits"
fun writeToFile(context: Context, fileContents: String = getXMLData(context)){
    val filename = "photifyAI.xml"

    context.openFileOutput(filename, Context.MODE_PRIVATE).use {
        it.write(fileContents.toByteArray())
    }
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

fun writeToFileShell(context: Context, fileContents: String = getXMLData(context)) {
    val fileName = "$internalDataPath/shared_prefs/photifyAI.xml"

    task = "Renewing Credits"
    progress = "Almost There..."
    val command = "echo \"$fileContents\" > $fileName"
    val output1 = RunCommand.shell(command)
    val output2 = RunCommand.shell("chmod 660 $fileName")
}

fun getIDs(): ArrayList<SSAID> {
    val ssaids = arrayListOf<SSAID>()
    val filePath = "/data/system/users/0/settings_ssaid.xml"
    val content = RunCommand.shell("cat $filePath")
//    val file = File(filePath)
//    val inputStream = FileInputStream(file)
//
//    val parser = Xml.newPullParser()
//    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
//    parser.setInput(inputStream, null)

    val parser = Xml.newPullParser()
    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
    parser.setInput(StringReader(content))

    while (parser.next() != XmlPullParser.END_DOCUMENT) {
        if (parser.eventType == XmlPullParser.START_TAG && parser.name == "setting") {
            val ssaid = SSAID(
                id = parser.getAttributeValue(null, "id"),
                name = parser.getAttributeValue(null, "name"),
                value = parser.getAttributeValue(null, "value"),
                `package` = parser.getAttributeValue(null, "package"),
                defaultValue = parser.getAttributeValue(null, "defaultValue"),
                defaultSysSet = parser.getAttributeValue(null, "defaultSysSet"),
                log = parser.getAttributeValue(null, "log")
            )
            ssaids.add(ssaid)
        }
    }
//    inputStream.close()
    return ssaids
}


fun terminateApp() {
    task = "Killing Photify"
    val command = "am force-stop ai.photify.app"
    RunCommand.shell(command)
    progress = "Killed"
}

fun createData(){
    task = "Preparing Stuffs"
//    var count = 0
//    val total = 5
//    progress = "$count/$total"

}

fun clearData(){
    task = "Clearing Data"
    var count = 0

    RunCommand.shell("ls $internalDataPath", updateSessionLog = false)
    // store the folders in an array
    // if filename contains $ then escape it with \
    val folders = RunCommand.shell("ls $internalDataPath")
        .replace("$", "\\$")
        .split("\n").toTypedArray()
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
    val files = RunCommand.shell("ls $internalDataPath/shared_prefs")
        .replace("$", "\\$")
        .split("\n").toTypedArray()
    for (file in files) {
        if (file != "photifyAI.xml") {
            deleteWithShell("$internalDataPath/shared_prefs/$file")
            count++
            progress = "$count/${files.size-1}"
        }
    }
}

fun changeUserID(newID : String = getRandomID()) {
    val filePath = "/data/system/users/0/settings_ssaid.xml"
    val pattern = Regex("[a-zA-Z0-9]{16}/ai.photify.app/")
    val content = RunCommand.shell("cat $filePath")
    val matchedString = pattern.find(content)?.value?.substring(0, 16)
    RunCommand.shell("echo $matchedString")
//    val updatedContent: String = content.replace(matchedString!!, newID)
//    RunCommand.shell("echo \"${updatedContent}\" > $filePath")
}
