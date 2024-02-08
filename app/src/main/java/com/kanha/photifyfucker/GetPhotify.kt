package com.kanha.photifyfucker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kanha.photifyfucker.res.mutableMimeType
import com.kanha.photifyfucker.ui.theme.PhotifyFuckerTheme
import com.kanha.photifyfucker.util.KToast
import com.kanha.photifyfucker.util.RunCommand
import com.kanha.photifyfucker.util.getNonWaterMarkedImage
import com.kanha.photifyfucker.util.getSharedFileName

class GetPhotify : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageDir = "/storage/emulated/0/Pictures/Photify/Favorites/"
        val imageFiles = RunCommand.shell("ls $imageDir").split("\n")
//        MyToast.show(this, "${imageFiles.size}")

        val filename = getSharedFileName(intent)
        getNonWaterMarkedImage(filename!!)
        KToast.show(this, mutableMimeType)
        this.finishAffinity()
        setContent {
            PhotifyFuckerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {}
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