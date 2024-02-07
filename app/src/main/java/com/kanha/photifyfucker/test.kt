package com.kanha.photifyfucker

import com.kanha.photifyfucker.util.RunCommand
import com.kanha.photifyfucker.util.getRandomID
import java.io.File

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
//    println(getRandomID(16))
//    println(RunCommand.shell("cat $filePath", asRoot = false))

    // read file from /home/ascalon/ple/photifyAI1.xml
    val xml = File("/home/ascalon/ple/photifyAI1.xml").readText()

//    val regex = "<string name=\"currIdentityPhoto\">\\{.*?\\}</string>".toRegex()
//
//    val updatedString = xml.replaceFirst(regex, "")

//    println()


    val regex = """&quot;file_name&quot;:&quot;photify_17\d{11}\.jpg&quot;,&quot;file_name_with_watermark&quot;:&quot;photify_17\d{11}\.jpg&quot;,&quot;prompt&quot;:&quot;(.*?)&quot;,&quot;server_filename&quot;:&quot;[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}&quot;""".toRegex()

    val matchResults = regex.findAll(xml)

    val extractedStrings = matchResults.map { it.groupValues[1] }.toSet()

    for (string in extractedStrings) {
        println(string)
    }
}