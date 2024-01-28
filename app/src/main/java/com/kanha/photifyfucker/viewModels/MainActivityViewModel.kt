package com.kanha.photifyfucker.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kanha.photifyfucker.res.fuckerInternalFilePath
import com.kanha.photifyfucker.res.isCopying
import com.kanha.photifyfucker.res.photifyAIXML
import com.kanha.photifyfucker.res.photifyInternalDataPath
import com.kanha.photifyfucker.res.progress
import com.kanha.photifyfucker.res.task
import com.kanha.photifyfucker.showAppIcon
import com.kanha.photifyfucker.showProgressBar
import com.kanha.photifyfucker.util.RunCommand
import com.kanha.photifyfucker.util.changePhotoCount
import com.kanha.photifyfucker.util.clearData
import com.kanha.photifyfucker.util.copyAllPhotify
import com.kanha.photifyfucker.util.copyWithShell
import com.kanha.photifyfucker.util.deleteEverythingExcept
import com.kanha.photifyfucker.util.deletePhotoData
import com.kanha.photifyfucker.util.separateAlternately
import com.kanha.photifyfucker.util.terminateApp
import com.kanha.photifyfucker.util.updateUserID
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
        task = "Editing internal data of Photify...."
//        terminateApp()
//        clearData()
//        createData()
        deletePhotoData()
        changePhotoCount(0)
        updateUserID()
        writeToFile(context = context, photifyAIXML)
        copyWithShell("$fuckerInternalFilePath/photifyAI.xml", "$photifyInternalDataPath/shared_prefs/photifyAI.xml")
        terminateApp()
//        launchPhotify()
//        writeToFileShell(fileContents = "", context = context)
        // launch ai.photify.app
//        task = "launching Photify"
//        RunCommand.shell("monkey -p ai.photify.app -c android.intent.category.LAUNCHER 1")
//        task = "Finished"
        showProgressBar = false
        task = "Finished..."
        progress = "Congratulations you got 30 more credits..."
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