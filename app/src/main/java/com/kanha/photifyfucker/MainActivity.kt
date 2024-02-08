package com.kanha.photifyfucker

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.kanha.photifyfucker.activities.settings.SettingsActivity
import com.kanha.photifyfucker.activities.settings.loadSettings
import com.kanha.photifyfucker.composables.KCardSingle
import com.kanha.photifyfucker.composables.Toolbar
import com.kanha.photifyfucker.res.ThemeType
import com.kanha.photifyfucker.res.accuratist
import com.kanha.photifyfucker.res.darkTheme
import com.kanha.photifyfucker.res.isDarkTheme
import com.kanha.photifyfucker.res.isDynamicColor
import com.kanha.photifyfucker.res.isSystemTheme
import com.kanha.photifyfucker.res.lightTheme
import com.kanha.photifyfucker.res.photifyAIXML
import com.kanha.photifyfucker.res.progress
import com.kanha.photifyfucker.res.prompts
import com.kanha.photifyfucker.res.sharedPrefsIsDynamicColor
import com.kanha.photifyfucker.res.sharedPrefsThemeType
import com.kanha.photifyfucker.res.systemTheme
import com.kanha.photifyfucker.res.task
import com.kanha.photifyfucker.res.themeHeader
import com.kanha.photifyfucker.res.themeType
import com.kanha.photifyfucker.ui.theme.PhotifyFuckerTheme
import com.kanha.photifyfucker.util.KToast
import com.kanha.photifyfucker.util.SharedPrefsManager
import com.kanha.photifyfucker.util.checkRootOnHost
import com.kanha.photifyfucker.util.cherryPicImages
import com.kanha.photifyfucker.util.getPrompts
import com.kanha.photifyfucker.util.storeXMLData
import com.kanha.photifyfucker.viewModels.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

var showProgressBar = false
var showAppIcon = false

class MainActivity : ComponentActivity() {

    private lateinit var sharedPrefsManager: SharedPrefsManager

    private fun init(){
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

        sharedPrefsManager = SharedPrefsManager(this)

        loadSettings(sharedPrefsManager)

        themeType = sharedPrefsManager.getInt(sharedPrefsThemeType, 2)
        when (themeType) {
            ThemeType.LIGHT.value -> {
                isDarkTheme = false
                isSystemTheme = false
                themeHeader = lightTheme
            }

            ThemeType.DARK.value -> {
                isDarkTheme = true
                isSystemTheme = false
                themeHeader = darkTheme
            }

            else -> {
                isSystemTheme = true
                themeHeader = systemTheme
            }
        }

        isDynamicColor = sharedPrefsManager.getBoolean(
            sharedPrefsIsDynamicColor,
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
        )

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
//                                .padding(top = 16.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                KCardSingle(
                                    icon = painterResource(id = R.drawable.outline_file_download_24),
                                    name = "Quickly Save All",
                                    description = "Save all images generated without watermark",
                                    onClick = {
                                        GlobalScope.launch {
                                            cherryPicImages()
                                        }
                                    }
                                )
                                KCardSingle(
                                    icon = painterResource(id = R.drawable.baseline_refresh_24),
                                    name = "Regenerate",
                                    description = "Set number of token to 30",
                                    onClick = {
                                        GlobalScope.launch {
                                            viewModel.renew(this@MainActivity)
                                        }
                                    }
                                )
                                KCardSingle(
                                    icon = painterResource(id = R.drawable.outline_text_snippet_24),
                                    name = "Prompts",
                                    description = "All input prompts",
                                    onClick = {
                                        startActivity(Intent(this@MainActivity, PromptsAct::class.java))
                                    }
                                )
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
            CoroutineScope(Dispatchers.Main).launch {
                val rootStatus = withContext(Dispatchers.IO) {
                    checkRootOnHost()
                }
                KToast.show(context, rootStatus)
            }
//            context.startActivity(Intent(context, DeviceIDActivity::class.java))
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
        IconButton(onClick = {
            context.startActivity(Intent(context, SettingsActivity::class.java))
        }) {
            Icon(
                painter = painterResource(id = R.drawable.outline_settings_24),
                contentDescription = "Theme",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}