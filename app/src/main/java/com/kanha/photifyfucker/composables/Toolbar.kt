package com.kanha.photifyfucker.composables

import android.content.Context
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.kanha.photifyfucker.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    context: Context,
    title: String = context.getString(R.string.app_name),
    content: @Composable RowScope.() -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        actions = content
    )
}








//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Toolbar(
//    context: Context,
//    title: String = context.getString(R.string.app_name),
//    showTerminalIcon: Boolean = true,
//    content: @Composable () -> Unit
//) {
//    val showMenu = remember { mutableStateOf(false) }
//    TopAppBar(
//        title = {
//            Text(text = title)
//        },
//        colors = TopAppBarDefaults.smallTopAppBarColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer,
//            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
//        ),
//        actions =
//        {
//            IconButton(onClick = {
//                // check if device is rooted
//                try {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        val rootStatus = withContext(Dispatchers.IO) {
//                            checkRootOnHost()
//                        }
//                        MyToast.show(context, rootStatus)
//                    }
//                } catch (e: Exception) {
//                    MyToast.show(
//                        context,
//                        "Device probably not Rooted"
//                    )
//                }
//            }) {
//                Icon(
//                    painter = painterResource(id = R.drawable.round_tag_24),
//                    contentDescription = "check Root",
//                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
//                )
//            }
//            IconButton(onClick = {
//                showMenu.value = !showMenu.value
//            }) {
//                Icon(
//                    painter = painterResource(id = R.drawable.baseline_refresh_24),
//                    contentDescription = "Refresh",
//                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
//                )
//            }
//            AnimatedVisibility(
//                visible = showMenu.value,
//                enter = fadeIn(animationSpec = tween(30)),
//                exit = fadeOut(animationSpec = tween(30)),
//                modifier = Modifier.offset(y = 31.dp)
//            ) {
//                DropdownMenu(
//                    expanded = showMenu.value,
//                    onDismissRequest = { showMenu.value = false }
//                ) {
//                    DropdownMenuItem(onClick = {
//                        // handle menu item click
//                        showMenu.value = false
//                    }, text = {
//                        Text("Menu Item 1")
//                    })
//                    DropdownMenuItem(onClick = {
//                        // handle menu item click
//                        showMenu.value = false
//                    }, text = {
//                        Text("Menu Item 2")
//                    })
//                }
//            }
//
//            if (showTerminalIcon) {
//                IconButton(onClick = {
//                    startActivity(context, Intent(context, TerminalActivity::class.java), null)
//                }) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.outline_terminal_24),
//                        contentDescription = "Terminal",
//                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
//                    )
//                }
//            }
//        }
//    )
//}