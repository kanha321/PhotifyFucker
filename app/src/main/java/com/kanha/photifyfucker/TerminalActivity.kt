package com.kanha.photifyfucker

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.kanha.photifyfucker.composables.DropDownMenu
import com.kanha.photifyfucker.composables.ScrollableText
import com.kanha.devicecontrol.extensions.clearString
import com.kanha.photifyfucker.composables.Toolbar
import com.kanha.photifyfucker.res.COMMANDS
import com.kanha.photifyfucker.res.ERROR_LOG
import com.kanha.photifyfucker.res.OUTPUT_LOG
import com.kanha.photifyfucker.util.RunCommand
import com.kanha.photifyfucker.res.SELECTED_LOG_TYPE
import com.kanha.photifyfucker.res.SESSION_LOG
import com.kanha.photifyfucker.res.commands
import com.kanha.photifyfucker.res.errorLog
import com.kanha.photifyfucker.res.outputLog
import com.kanha.photifyfucker.res.sessionLog
import com.kanha.photifyfucker.ui.theme.PhotifyFuckerTheme
import com.kanha.photifyfucker.util.KToast
import com.kanha.photifyfucker.util.checkRootOnHost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

var commandsText by mutableStateOf("")
var output by mutableStateOf("")

private const val TAG = "TerminalActivity"

class TerminalActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        setContent {
            PhotifyFuckerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Toolbar(
                            context = this@TerminalActivity,
                            title = "Logs"
                        ) {
//                            val showMenu = remember { mutableStateOf(false) }
                            IconButton(onClick = {
                                KToast.show(this@TerminalActivity, checkRootOnHost())
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.round_tag_24),
                                    contentDescription = "check Root",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                            }
                            IconButton(onClick = {
                                val clipBoard =
                                    getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = when (SELECTED_LOG_TYPE) {
                                    sessionLog -> ClipData.newPlainText(sessionLog, SESSION_LOG)
                                    outputLog -> ClipData.newPlainText(outputLog, OUTPUT_LOG)
                                    errorLog -> ClipData.newPlainText(errorLog, ERROR_LOG)
                                    commands -> ClipData.newPlainText(commands, COMMANDS)
                                    else -> ClipData.newPlainText("Error", "Something went wrong")
                                }
                                clipBoard.setPrimaryClip(clip)
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_copy_all_24),
                                    contentDescription = "Refresh",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                            }
//                            IconButton(onClick = {
////                                showMenu.value = !showMenu.value
//                            }) {
//                                Icon(
//                                    painter = painterResource(id = R.drawable.baseline_refresh_24),
//                                    contentDescription = "Refresh",
//                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
//                                )
//                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            var logType by remember {
                                mutableStateOf(SESSION_LOG)
                            }
                            ScrollableText(
                                output = when (SELECTED_LOG_TYPE) {
                                    sessionLog -> SESSION_LOG
                                    outputLog -> OUTPUT_LOG
                                    errorLog -> ERROR_LOG
                                    commands -> COMMANDS
                                    else -> SESSION_LOG
                                },
                                modifier = Modifier
                                    .heightIn(max = 14 * 20.dp) // 20dp is the default line height
                                    .fillMaxSize()
                                    .align(Alignment.BottomCenter),
                            )
                            Column {
                                DropDownMenu(
                                    items = ArrayList<String>().apply {
                                        add(sessionLog)
                                        add(outputLog)
                                        add(errorLog)
                                        add(commands)
                                    },
                                    label = "Session Log",
                                    onValueChange = {
                                        Log.d(TAG, "onCreate DropDownMenu: $logType")
                                        logType = when (it) {
                                            sessionLog -> SESSION_LOG
                                            outputLog -> OUTPUT_LOG
                                            errorLog -> ERROR_LOG
                                            commands -> COMMANDS
                                            else -> SESSION_LOG
                                        }
                                    }
                                )
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 0.dp,
                                            bottom = 20.dp,
                                            start = 20.dp,
                                            end = 20.dp
                                        ),
                                    value = commandsText,
                                    onValueChange = {
                                        commandsText = it
                                    },
                                    singleLine = true,
                                    label = { Text("Run Shell Commands") },
                                    readOnly = false,
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Done,
                                        keyboardType = KeyboardType.Text
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            output = RunCommand.shell(commandsText)
                                            if (commandsText == "clear") {
                                                SESSION_LOG = "session.log\n\n"
                                                OUTPUT_LOG = "output.log\n\n"
                                                ERROR_LOG = "error.log\n\n"
                                            }
                                            commandsText = ""
                                        }
                                    )
                                )
                                Text(text = output.clearString())
                            }
                        }
                    }
                }
            }
        }
    }
}