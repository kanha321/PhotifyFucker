package com.kanha.photifyfucker.backups

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kanha.photifyfucker.MainActivity
import com.kanha.photifyfucker.res.androidVersion
import com.kanha.photifyfucker.res.copyAll
import com.kanha.photifyfucker.res.exit
import com.kanha.photifyfucker.res.isCopying
import com.kanha.photifyfucker.res.mutableCommand
import com.kanha.photifyfucker.res.mutableMimeType
import com.kanha.photifyfucker.res.progress
import com.kanha.photifyfucker.util.RunCommand.isRooted
import com.kanha.photifyfucker.ui.theme.PhotifyFuckerTheme
import com.kanha.photifyfucker.util.RunCommand
import com.kanha.photifyfucker.util.copyAllPhotify
import com.kanha.photifyfucker.util.deleteEverythingExcept
import com.kanha.photifyfucker.util.separateAlternately
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class MainActivityOld : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContent {
                PhotifyFuckerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Text(text = mutableMimeType)
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                modifier = Modifier.width(190.dp),
                                onClick = {
                                          startActivity(Intent(this@MainActivityOld, MainActivity::class.java))
                                },
                            ) {
                                Text(text = "Exit")
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "Root access: ${isRooted()}")
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = mutableCommand)
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                modifier = Modifier.width(190.dp),
                                onClick = {
                                    GlobalScope.launch(Dispatchers.IO) { // launch a new coroutine in background and continue
                                        if (mutableCommand == exit) {
                                            finishAffinity()
                                            exitProcess(0)
                                        } else mutableMimeType = RunCommand.shell(mutableCommand)
                                            .ifBlank { "$androidVersion ✔" }
                                    }
                                },
                            ) {
                                Text(text = "Retry last command")
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                modifier = Modifier.width(190.dp),
                                onClick = {
                                    if (!isCopying) {
                                        GlobalScope.launch(Dispatchers.IO) { // launch a new coroutine in background and continue
                                            copyAllPhotify()
                                        }
                                    } else {
                                        copyAll = "Wait Bitch!!"
                                    }
                                },
                            ) {
                                Text(text = copyAll)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = progress)
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                modifier = Modifier.width(190.dp),
                                onClick = {
                                    GlobalScope.launch {
                                        separateAlternately()
                                    }
                                },
                            ) {
                                Text(text = "Separate Alternately")
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Row (
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    onClick = {
                                        GlobalScope.launch {
                                            deleteEverythingExcept(1)
                                        }
                                    },
                                ) {
                                    Text(text = "dir1")
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Button(
                                    onClick = {
                                        GlobalScope.launch {
                                            deleteEverythingExcept(2)
                                        }
                                    },
                                ) {
                                    Text(text = "dir2")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}