package com.kanha.photifyfucker.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kanha.photifyfucker.PrefsItem
import com.kanha.photifyfucker.createPrefsXMLString
import com.kanha.photifyfucker.deletePrefs
import com.kanha.photifyfucker.parseSharedPreferencesXML
import com.kanha.photifyfucker.res.fuckerInternalFilePath
import com.kanha.photifyfucker.res.photifyInternalDataPath
import com.kanha.photifyfucker.ui.theme.PhotifyFuckerTheme
import com.kanha.photifyfucker.util.RunCommand
import com.kanha.photifyfucker.util.copyWithShell
import com.kanha.photifyfucker.util.readFromFile
import com.kanha.photifyfucker.util.writeToFile
import com.kanha.photifyfucker.writePrefsListToXML
import java.io.File

class TestActivity : ComponentActivity() {

    companion object{
        const val TAG = "TestActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use the shell command to copy the shared preferences file
        val command =
            "cp $photifyInternalDataPath/photifyAI.xml $fuckerInternalFilePath/photifyAI.xml"
        RunCommand.shell(command)
        RunCommand.shell("chmod 777 $fuckerInternalFilePath/photifyAI.xml")

        // Get the copied shared preferences XML file
//        val file = File(filesDir, "photifyAI.xml")
        var preferences = parseSharedPreferencesXML(this@TestActivity, "photifyAI.xml")

        setContent {
            PhotifyFuckerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Column{
                            Button(onClick = {
                                preferences = deletePrefs(preferences, "isPrem")
//                                writePrefsListToXML(this@TestActivity, preferences, "photifyAI.xml")
                                Log.d(TAG, "onCreate: ${createPrefsXMLString(preferences)}")
                                writeToFile(this@TestActivity, createPrefsXMLString(preferences))
                                copyWithShell("$fuckerInternalFilePath/photifyAI.xml", "$photifyInternalDataPath/shared_prefs/photifyAI.xml")
                            }) {
                                Text(text = "deleteIsPrem")
                            }
                            PreferencesList(preferences = preferences)
                        }
//                        Text(text = readFromFile(this@TestActivity, "photifyAI.xml"))
                    }
                }
            }
        }
    }
}

@Composable
fun PreferencesList(preferences: List<PrefsItem>) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(preferences) { preference ->
            Text(
                text = "${preference.key}: ${preference.value}"
            )
        }
    }
}
