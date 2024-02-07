package com.kanha.photifyfucker.util

import com.kanha.photifyfucker.res.photifyAIXML

fun getPrompts() : ArrayList<String>{
    val regex = """&quot;file_name&quot;:&quot;photify_17\d{11}\.jpg&quot;,&quot;file_name_with_watermark&quot;:&quot;photify_17\d{11}\.jpg&quot;,&quot;prompt&quot;:&quot;(.*?)&quot;,&quot;server_filename&quot;:&quot;[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}&quot;""".toRegex()
    val matchResults = regex.findAll(storeXMLData())
    val extractedStrings = matchResults.map { it.groupValues[1] }.toList().reversed().toSet()
    return ArrayList(extractedStrings)
}