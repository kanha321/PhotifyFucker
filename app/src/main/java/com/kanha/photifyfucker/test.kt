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
//    println(getRandomID(16))
//    println(RunCommand.shell("cat $filePath", asRoot = false))

    val xml = """
        <?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
    <string name="currIdentityPhoto">{&quot;url&quot;:&quot;https://exh-photify.s3.us-west-2.amazonaws.com/identity_images/66694a0d9af5c543/a44b2e2e086aa03fb4bbda2302b74ec9.jpg&quot;,&quot;identity_image_id&quot;:&quot;104f74be-665b-4a26-936b-9e6439a22aa0&quot;,&quot;gender&quot;:&quot;Woman&quot;,&quot;skin_tone&quot;:&quot;White&quot;}</string>
    <boolean name="onboardingShown" value="true" />
    <boolean name="isPrem" value="false" />
    <long name="sessionCount" value="14" />
    <boolean name="inviteFriendShown" value="true" />
    <string name="identityPhotos">[{&quot;url&quot;:&quot;https://exh-photify.s3.us-west-2.amazonaws.com/identity_images/99b94a0d9af5c587/a44b2e2e086aa03fb4bbda2302b74ec9.jpg&quot;,&quot;identity_image_id&quot;:&quot;e036beed-4352-4cc4-8fd5-e46f237db15b&quot;,&quot;gender&quot;:&quot;Woman&quot;,&quot;skin_tone&quot;:&quot;White&quot;},[{&quot;file_name&quot;:&quot;photify_1706381154493
    """.trimIndent()

    val regex = "<string name=\"currIdentityPhoto\">\\{.*?\\}</string>".toRegex()

    val updatedString = xml.replaceFirst(regex, "")

    println(updatedString)
}