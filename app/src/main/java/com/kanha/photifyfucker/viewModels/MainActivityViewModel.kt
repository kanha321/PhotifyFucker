package com.kanha.photifyfucker.viewModels

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import com.kanha.photifyfucker.activities.settings.launchAutomatically
import com.kanha.photifyfucker.res.fuckerInternalFilePath
import com.kanha.photifyfucker.res.photifyAIXML
import com.kanha.photifyfucker.res.photifyInternalDataPath
import com.kanha.photifyfucker.res.progress
import com.kanha.photifyfucker.res.task
import com.kanha.photifyfucker.showProgressBar
import com.kanha.photifyfucker.util.RunCommand
import com.kanha.photifyfucker.util.changePhotoCount
import com.kanha.photifyfucker.util.copyWithShell
import com.kanha.photifyfucker.util.deletePhotoData
import com.kanha.photifyfucker.util.getPrompts
import com.kanha.photifyfucker.util.terminateApp
import com.kanha.photifyfucker.util.updateUserID
import com.kanha.photifyfucker.util.writeToFile

class MainActivityViewModel : ViewModel() {

    fun launchPhotify(): String {
        return RunCommand.shell("monkey -p ai.photify.app -c android.intent.category.LAUNCHER 1")
    }

    fun renew(context: Context) {
        showProgressBar = true
        task = "Editing internal data of Photify...."
        deletePhotoData()
        changePhotoCount(0)
        updateUserID()
        writeToFile(context = context, photifyAIXML)
        copyWithShell("$fuckerInternalFilePath/photifyAI.xml", "$photifyInternalDataPath/shared_prefs/photifyAI.xml")
        terminateApp()

        val clipBoard =
            context.getSystemService(ComponentActivity.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("prompt", getPrompts()[0])
        clipBoard.setPrimaryClip(clip)

        progress = if (launchAutomatically) {
            launchPhotify()
            "Launching Photify"
        } else {
            "Congratulations you got 30 more credits...\nYou can launch Photify Now :)"
        }

        showProgressBar = false
        task = "Finished..."
    }
}