package com.kanha.photifyfucker

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.kanha.photifyfucker.composables.ScrollableText
import com.kanha.photifyfucker.composables.Toolbar
import com.kanha.photifyfucker.res.COMMANDS
import com.kanha.photifyfucker.res.ERROR_LOG
import com.kanha.photifyfucker.res.OUTPUT_LOG
import com.kanha.photifyfucker.res.SELECTED_LOG_TYPE
import com.kanha.photifyfucker.res.SESSION_LOG
import com.kanha.photifyfucker.res.commands
import com.kanha.photifyfucker.res.errorLog
import com.kanha.photifyfucker.res.outputLog
import com.kanha.photifyfucker.res.sessionLog
import com.kanha.photifyfucker.ui.theme.PhotifyFuckerTheme
import com.kanha.photifyfucker.util.RunCommand
import com.kanha.photifyfucker.util.changeUserID

class TerminalActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotifyFuckerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Toolbar(
                            context = this@TerminalActivity2,
                            title = "Logs"
                        ) {
                            val showMenu = remember { mutableStateOf(false) }
                            IconButton(onClick = {
                                // check if device is rooted
//                                    CoroutineScope(Dispatchers.Main).launch {
//                                        val rootStatus = withContext(Dispatchers.IO) {
//                                            checkRootOnHost()
//                                        }
//                                        MyToast.show(this@TerminalActivity, rootStatus)
                                        changeUserID()
//                                    }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.round_tag_24),
                                    contentDescription = "check Root",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                            }
                            IconButton(onClick = {
                                val clipBoard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = when (SELECTED_LOG_TYPE) {
                                    sessionLog -> ClipData.newPlainText("Session Log", SESSION_LOG)
                                    outputLog -> ClipData.newPlainText("Output Log", OUTPUT_LOG)
                                    errorLog -> ClipData.newPlainText("Error Log", ERROR_LOG)
                                    commands -> ClipData.newPlainText("Commands", COMMANDS)
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
                            IconButton(onClick = {
//                                showMenu.value = !showMenu.value
                                RunCommand.shell("cat /data/system/users/0/settings_ssaid.xml")
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_refresh_24),
                                    contentDescription = "Refresh",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                            }
//                            AnimatedVisibility(
//                                visible = showMenu.value,
//                                enter = fadeIn(animationSpec = tween(30)),
//                                exit = fadeOut(animationSpec = tween(30)),
//                                modifier = Modifier.offset(y = 31.dp)
//                            ) {
//                                DropdownMenu(
//                                    expanded = showMenu.value,
//                                    onDismissRequest = { showMenu.value = false }
//                                ) {
//                                    DropdownMenuItem(onClick = {
//                                        ACTIVE_LOG = SESSION_LOG
//                                        showMenu.value = false
//                                    }, text = {
//                                        Text("Session Log")
//                                    })
//                                    DropdownMenuItem(onClick = {
//                                        ACTIVE_LOG = OUTPUT_LOG
//                                        showMenu.value = false
//                                    }, text = {
//                                        Text("Output Log")
//                                    })
//                                    DropdownMenuItem(onClick = {
//                                        ACTIVE_LOG = ERROR_LOG
//                                        showMenu.value = false
//                                    }, text = {
//                                        Text("Error Log")
//                                    })
//                                    DropdownMenuItem(onClick = {
//                                        ACTIVE_LOG = COMMANDS
//                                        showMenu.value = false
//                                    }, text = {
//                                        Text("Commands")
//                                    })
//                                }
//                            }
                        }
                        ScrollableText(
                            output = when (SELECTED_LOG_TYPE) {
                                sessionLog -> SESSION_LOG
                                outputLog -> OUTPUT_LOG
                                errorLog -> ERROR_LOG
                                commands -> COMMANDS
                                else -> "Something went wrong"
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}