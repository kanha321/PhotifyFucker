package com.kanha.photifyfucker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kanha.photifyfucker.composables.Toolbar
import com.kanha.photifyfucker.res.accuratist
import com.kanha.photifyfucker.res.photifyAIXML
import com.kanha.photifyfucker.ui.theme.PhotifyFuckerTheme
import com.kanha.photifyfucker.viewModels.MainActivityViewModel
import com.kanha.photifyfucker.res.progress
import com.kanha.photifyfucker.res.task
import com.kanha.photifyfucker.util.storeXMLData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

var showProgressBar = false
var showAppIcon = false

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photifyAIXML = storeXMLData()
        setContent {
            PhotifyFuckerTheme {
                // A surface container using the 'background' color from the theme

                val viewModel = viewModel<MainActivityViewModel>()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        Modifier
                            .padding(top = 180.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = task,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontFamily = accuratist
                        )
                        Spacer(Modifier.height(28.dp))
                        Text(
                            text = progress,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = accuratist
                        )
                    }
                    Column {
                        TBar(this@MainActivity)
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                MyButton(
                                    text = "Save All",
                                    onClick = {
                                        GlobalScope.launch {
                                            viewModel.getAllImages()
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
//                                Text("Refresh Tokens", fontSize = 24.sp)
//                                Spacer(modifier = Modifier.height(16.dp))
//                                Text("Method 1", fontSize = 16.sp)
//                                Spacer(modifier = Modifier.height(8.dp))
//                                Row {
//                                    MyButton(
//                                        text = "Generate",
//                                        onClick = {
//                                            CoroutineScope(Dispatchers.Main).launch {
//                                                viewModel.renew(context = this@MainActivity)
//                                            }
//                                        }
//                                    )
//                                }
//                                Spacer(modifier = Modifier.height(16.dp))
//                                Text("Method 2", fontSize = 16.sp)
//                                Spacer(modifier = Modifier.height(8.dp))
                                Row {
                                    MyButton(
                                        text = "Regenerate",
                                        onClick = {
                                            GlobalScope.launch {
                                                viewModel.renew1(this@MainActivity)
                                            }
                                        }
                                    )
//                                    Spacer(modifier = Modifier.width(16.dp))
//                                    MyButton(
//                                        text = "Regenerate",
//                                        onClick = {
//                                            CoroutineScope(Dispatchers.Main).launch {
//                                                viewModel.renew2(this@MainActivity)
//                                            }
//                                        }
//                                    )
                                }
                            }
                        }
                    }
                    Box(
                        contentAlignment = Alignment.BottomCenter,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(bottom = 200.dp)
                        ) {
                            // progress bar
                            if (showProgressBar)
                                CircularProgressIndicator()
                            if (showAppIcon) {
//                                Image(
//                                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
//                                    contentDescription = "App Icon",
//                                    modifier = Modifier.size(100.dp)
//                                        .clickable {
//                                            CoroutineScope(Dispatchers.Main).launch {
//                                                val output = withContext(Dispatchers.IO) {
//                                                    viewModel.launchPhotify()
//                                                }
//                                            }
//                                        }
//                                )
                                IconButton(onClick = {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        val output = withContext(Dispatchers.IO) {
                                            viewModel.launchPhotify()
                                        }
                                    }
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                        contentDescription = "App Icon",
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(30)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = accuratist
        )
    }
}

@Composable
fun TBar(context: Context) {
    Toolbar(context) {
        IconButton(onClick = {
//            CoroutineScope(Dispatchers.Main).launch {
//                val rootStatus = withContext(Dispatchers.IO) {
//                    checkRootOnHost()
//                }
//                MyToast.show(context, rootStatus)
//            }
            context.startActivity(Intent(context, DeviceIDActivity::class.java))
        }) {
            Icon(
                painter = painterResource(id = R.drawable.round_tag_24),
                contentDescription = "check Root",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
        IconButton(onClick = {
            context.startActivity(Intent(context, TerminalActivity::class.java))
        }) {
            Icon(
                painter = painterResource(id = R.drawable.outline_terminal_24),
                contentDescription = "Terminal",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}