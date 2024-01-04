package com.kanha.photifyfucker

import androidx.compose.material3.contentColorFor
import com.kanha.photifyfucker.util.RunCommand
import com.kanha.photifyfucker.util.getRandomID
import java.util.Random

fun replaceStringInFile(filePath: String) {
    // File path is always /data/system/users/0/test.xml

    // Regex pattern to match 16 characters followed by </string>
    val pattern = Regex("[a-zA-Z0-9]{16}</string>")

    // Read the file content
    val content = RunCommand.shell("cat $filePath", asRoot = false)

    println(content)

    val matchedString = pattern.find(content)?.value?.substring(0, 16)
    println(matchedString)

    val newID = getRandomID()
    println(newID)
    val updatedContent: String? = content.replace(matchedString!!, newID)

    // Write the updated content back to the file
    RunCommand.shell("echo \"${updatedContent!!}\" > $filePath", asRoot = false)
}

fun main() {
//    val filePath = "/home/ascalon/AndroidStudioProjects/PhotifyFucker/app/src/main/assets/settings_ssaid.xml"
//    replaceStringInFile(filePath)
    println(getRandomID(16))
//    println(RunCommand.shell("cat $filePath", asRoot = false))
}