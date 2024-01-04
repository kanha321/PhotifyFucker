package com.kanha.photifyfucker.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kanha.photifyfucker.res.isCopying
import com.kanha.photifyfucker.res.progress
import com.kanha.photifyfucker.res.task
import com.kanha.photifyfucker.showAppIcon
import com.kanha.photifyfucker.showProgressBar
import com.kanha.photifyfucker.util.RunCommand
import com.kanha.photifyfucker.util.clearData
import com.kanha.photifyfucker.util.copyAllPhotify
import com.kanha.photifyfucker.util.deleteEverythingExcept
import com.kanha.photifyfucker.util.getXMLData
import com.kanha.photifyfucker.util.separateAlternately
import com.kanha.photifyfucker.util.terminateApp
import com.kanha.photifyfucker.util.writeToFile
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

    fun launchPhotify(): String{
        return RunCommand.shell("monkey -p ai.photify.app -c android.intent.category.LAUNCHER 1")
    }

    fun renew1(context: Context) {
        showProgressBar = true
        terminateApp()
        clearData()
//        createData()
        writeToFileShell(fileContents = "", context = context)
        // launch ai.photify.app
//        task = "launching Photify"
//        RunCommand.shell("monkey -p ai.photify.app -c android.intent.category.LAUNCHER 1")
//        task = "Finished"
        showProgressBar = false
        task = "Open the app, press \"continue\" on onboarding screen till it asks to select a photo"
        progress = "after that open this app again and click on \"Regenerate\""
    }
    fun renew2(context: Context){
        task = "Generating tokens"
        clearData()
        writeToFileShell(context)
        task = "Finished"
        progress = "o ___ o"
        showAppIcon = true
    }

    fun renew(context: Context){
        showProgressBar = true
        terminateApp()
        clearData()
        writeToFile(context = context)
        task = "Launching Photify"
        showProgressBar = false
        RunCommand.shell("monkey -p ai.photify.app -c android.intent.category.LAUNCHER 1")
    }
}