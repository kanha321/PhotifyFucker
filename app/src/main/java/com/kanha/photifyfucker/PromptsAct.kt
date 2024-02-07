package com.kanha.photifyfucker

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanha.photifyfucker.composables.PromptCard2
import com.kanha.photifyfucker.composables.PromptList
import com.kanha.photifyfucker.composables.Toolbar
import com.kanha.photifyfucker.res.COMMANDS
import com.kanha.photifyfucker.res.ERROR_LOG
import com.kanha.photifyfucker.res.OUTPUT_LOG
import com.kanha.photifyfucker.res.SELECTED_LOG_TYPE
import com.kanha.photifyfucker.res.SESSION_LOG
import com.kanha.photifyfucker.res.commands
import com.kanha.photifyfucker.res.errorLog
import com.kanha.photifyfucker.res.outputLog
import com.kanha.photifyfucker.res.photifyAIXML
import com.kanha.photifyfucker.res.prompts
import com.kanha.photifyfucker.res.sessionLog
import com.kanha.photifyfucker.ui.theme.PhotifyFuckerTheme
import com.kanha.photifyfucker.util.getPrompts
import com.kanha.photifyfucker.util.storeXMLData

class PromptsAct : ComponentActivity() {

    private fun init() {
        photifyAIXML = storeXMLData()
        prompts = getPrompts()
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContent {
            PhotifyFuckerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {

                        Toolbar(context = this@PromptsAct){}
                        Spacer(modifier = Modifier.height(8.dp))

                        LazyColumn {
                            items(prompts) { prompt ->
                                PromptCard2(
                                    name = prompt,
                                    onClick = {
                                        // copy to clipboard
                                        val clipBoard =
                                            getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                                        val clip = ClipData.newPlainText("label", prompt)
                                        clipBoard.setPrimaryClip(clip)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}