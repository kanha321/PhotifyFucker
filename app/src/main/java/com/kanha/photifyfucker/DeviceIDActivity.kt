package com.kanha.photifyfucker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kanha.photifyfucker.composables.ActionSelectionCard
import com.kanha.photifyfucker.ui.theme.PhotifyFuckerTheme
import com.kanha.photifyfucker.util.getIDs

class DeviceIDActivity : ComponentActivity() {

    private val ssaids = getIDs()
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
                    ){
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 16.dp)
                        ) {
                            items(ssaids){
                                ActionSelectionCard(
                                    icon = painterResource(id = R.drawable.baseline_apps_24),
                                    name = it.name,
                                    description = it.value,
                                    onClick = {}
                                )
                            }
                        }
//                        Card {
//
//                        }
                    }
                }
            }
        }
    }
}