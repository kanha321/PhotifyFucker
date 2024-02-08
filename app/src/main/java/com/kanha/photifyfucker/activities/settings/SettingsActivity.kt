package com.kanha.photifyfucker.activities.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kanha.photifyfucker.ui.theme.PhotifyFuckerTheme

class SettingsActivity : ComponentActivity() {

    // view model factory with context
    private val viewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory(context = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PhotifyFuckerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SettingsScreen(
                        viewModel = viewModel,
                        context = this
                    )
                }
            }
        }
    }
}
