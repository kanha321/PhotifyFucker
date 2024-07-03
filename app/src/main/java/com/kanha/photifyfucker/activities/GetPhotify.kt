package com.kanha.photifyfucker.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kanha.photifyfucker.TerminalActivity
import com.kanha.photifyfucker.res.mutableMimeType
import com.kanha.photifyfucker.ui.theme.PhotifyFuckerTheme
import com.kanha.photifyfucker.util.KToast
import com.kanha.photifyfucker.util.getNonWaterMarkedImage
import com.kanha.photifyfucker.util.getNonWaterMarkedImageFromHistory
import com.kanha.photifyfucker.util.getSharedFileName

class GetPhotify : ComponentActivity() {

    companion object{
        private const val TAG = "GetPhotify"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val imageDir = "$photifyStoragePath/Favorites/"
//        if (!exists(File(imageDir).toPath())) {
//            RunCommand.shell("mkdir $imageDir")
//        }
//        val imageFiles = RunCommand.shell("ls $imageDir").split("\n")
//        KToast.show(this, "${imageFiles.size}")

        val filename = getSharedFileName(intent)

        val regex = Regex("""^[1234567890abcdef]{32}W\.jpg$""")

        if (filename!!.startsWith("photify")) {
            getNonWaterMarkedImage(filename)
        } else {
            if(regex.matches(filename)) {
                getNonWaterMarkedImageFromHistory(filename)
            } else {
                KToast.show(this@GetPhotify, "This does not seems to be a valid photify file ")
            }
        }

        KToast.show(this, mutableMimeType)
        this.finishAffinity()
        setContent {
            PhotifyFuckerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
//                        Text(text = imageFiles.toString())
                        Button(onClick = {
                            startActivity(Intent(this@GetPhotify, TerminalActivity::class.java))
                        }) {

                        }
                    }
                }
            }
        }
    }
}
//
//@Composable
//fun ImageGrid(images: List<String>) {
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(3)
//    ) {
//        items(images.size) { index ->
//            val image = images[index]
//            ImageItem(image)
//        }
//    }
//}
//
//@Composable
//fun ImageItem(imageFile: String) {
//    val bitmap = loadBitmapFromRootAccess(imageFile)
//    bitmap?.let {
//        Image(
//            bitmap = it.asImageBitmap(),
//            contentDescription = null,
//            modifier = Modifier
//                .padding(8.dp)
//                .aspectRatio(1f)
//                .clip(RoundedCornerShape(5.dp)),
//            contentScale = ContentScale.Crop
//        )
//    }
//}