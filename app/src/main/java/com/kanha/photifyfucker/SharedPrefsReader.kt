package com.kanha.photifyfucker

import android.content.Context
import android.util.Log
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

data class PrefsItem(val key: String, val value: Any?)

fun parseSharedPreferencesXML(context: Context, filename: String): List<PrefsItem> {
    val preferences = mutableListOf<PrefsItem>()

    try {
        // Open the file from internal storage
        val inputStream = context.openFileInput(filename)
        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val document = documentBuilder.parse(inputStream)
        document.documentElement.normalize()

        val nodeList: NodeList = document.getElementsByTagName("map").item(0).childNodes

        for (i in 0 until nodeList.length) {
            val node: Node = nodeList.item(i)
            if (node.nodeType == Node.ELEMENT_NODE) {
                val element = node as Element
                val key = element.getAttribute("name")
                val value = when (element.nodeName) {
                    "string" -> element.textContent
                    "int" -> element.getAttribute("value").toIntOrNull()
                    "boolean" -> element.getAttribute("value").toBoolean()
                    "long" -> element.getAttribute("value").toLongOrNull()
                    else -> null
                }
                preferences.add(PrefsItem(key, value))
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return preferences
}

fun deletePrefs(prefsList: List<PrefsItem>, keyToDelete: String): List<PrefsItem> {
    val updatedPrefsList = prefsList.toMutableList()
    updatedPrefsList.removeIf { it.key == keyToDelete }
    return updatedPrefsList
}

fun writePrefsListToXML(context: Context, prefsList: List<PrefsItem>, filename: String) {
    try {
        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val document = documentBuilder.newDocument()
        val rootElement = document.createElement("map")

        for (prefsItem in prefsList) {
            val element = when (prefsItem.value) {
                is String -> document.createElement("string")
                is Int -> document.createElement("int")
                is Boolean -> document.createElement("boolean")
                is Long -> document.createElement("long")
                else -> throw IllegalArgumentException("Unsupported data type")
            }
            element.setAttribute("name", prefsItem.key)
            element.textContent = prefsItem.value.toString()
            rootElement.appendChild(element)
        }

        document.appendChild(rootElement)

        // Convert Document to XML string
        val transformer = TransformerFactory.newInstance().newTransformer()
        val source = DOMSource(document)
        val writer = StringWriter()
        val result = StreamResult(writer)
        transformer.transform(source, result)
        val xmlString = writer.toString()

        // Write XML string to file
        context.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(xmlString.toByteArray())
        }

        // Optionally, log or handle success
//        println("PrefsList written to $filename successfully.")

        Log.d("Prefs", "writePrefsListToXML: \n $xmlString")

    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun createPrefsXMLString(prefsList: List<PrefsItem>): String {
    return try {
        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val document = documentBuilder.newDocument()
        val rootElement = document.createElement("map")

        for (prefsItem in prefsList) {
            val element = when (prefsItem.value) {
                is String -> document.createElement("string")
                is Int -> document.createElement("int")
                is Boolean -> document.createElement("boolean")
                is Long -> document.createElement("long")
                else -> throw IllegalArgumentException("Unsupported data type")
            }
            element.setAttribute("name", prefsItem.key)
            element.textContent = prefsItem.value.toString()
            rootElement.appendChild(element)
        }

        document.appendChild(rootElement)

        // Convert Document to XML string
        val transformer = TransformerFactory.newInstance().newTransformer()
        val source = DOMSource(document)
        val writer = StringWriter()
        val result = StreamResult(writer)
        transformer.transform(source, result)

        writer.toString() // Return the XML string
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

