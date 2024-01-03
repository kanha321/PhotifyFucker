package com.kanha.photifyfucker.viewModels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.kanha.photifyfucker.res.isCopying
import com.kanha.photifyfucker.res.progress
import com.kanha.photifyfucker.res.task
import com.kanha.photifyfucker.showProgressBar
import com.kanha.photifyfucker.util.RunCommand
import com.kanha.photifyfucker.util.clearData
import com.kanha.photifyfucker.util.copyAllPhotify
import com.kanha.photifyfucker.util.createData
import com.kanha.photifyfucker.util.deleteEverythingExcept
import com.kanha.photifyfucker.util.separateAlternately
import com.kanha.photifyfucker.util.terminateApp
import com.kanha.photifyfucker.util.writeToFileShell

class MainActivityViewModel : ViewModel() {

    fun getAllImages(){
        showProgressBar = true
        isCopying = true
        try {
            copyAllPhotify()
            separateAlternately()
            deleteEverythingExcept(1)
            task = "Finished"
            progress = "o ___ o"
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            showProgressBar = false
            isCopying = false
        }
    }
    fun renew(context: Context){
        showProgressBar = true
        terminateApp()
        clearData()
//        createData()
        writeToFileShell(context)
        // launch ai.photify.app
        RunCommand.shell("monkey -p ai.photify.app -c android.intent.category.LAUNCHER 1")
        task = "Finished"
        showProgressBar = false
        progress = "o ___ o"
    }
}