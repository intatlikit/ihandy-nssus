package com.nssus.ihandy.ui.basecomposable

import android.content.ContentValues
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun CameraAndGalleryPicker() {
    val context = LocalContext.current

    // Camera Intent Launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isSaved ->
            if (isSaved) {
                Toast.makeText(context, "Photo Saved to Photos!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Photo Capture Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    )

    // Gallery Intent Launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                Toast.makeText(context, "Image Selected: $uri", Toast.LENGTH_SHORT).show()
            }
        }
    )

    var photoUri by remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            // Create a content resolver entry for the photo in MediaStore
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "captured_photo_${System.currentTimeMillis()}.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES) // Save to "Pictures" folder
            }

            val contentResolver = context.contentResolver
            photoUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            if (photoUri != null) {
                // Launch the camera intent with the generated URI
                cameraLauncher.launch(photoUri!!)
            } else {
                Toast.makeText(context, "Failed to create MediaStore entry", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Open Camera")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            galleryLauncher.launch("image/*")
        }) {
            Text(text = "Open Gallery")
        }
    }
}

//@Composable
//fun CameraAndGalleryPicker() {
//    val context = LocalContext.current
//    val coroutineScope = rememberCoroutineScope()
//
//    // Camera Intent Launcher
//    val cameraLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.TakePicture(),
//        onResult = { isSaved ->
//            if (isSaved) {
//                Toast.makeText(context, "Photo Saved!", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Photo Capture Failed!", Toast.LENGTH_SHORT).show()
//            }
//        }
//    )
//
//    // Gallery Intent Launcher
//    val galleryLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent(),
//        onResult = { uri ->
//            uri?.let {
//                Toast.makeText(context, "Image Selected: $uri", Toast.LENGTH_SHORT).show()
//            }
//        }
//    )
//
//    var photoUri by remember { mutableStateOf<Uri?>(null) }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Button(onClick = {
//            // Set up the file URI for saving the photo to the public Pictures directory
//            val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//            val photoFile = File(picturesDir, "captured_photo_${System.currentTimeMillis()}.jpg")
//
//            // Ensure the directory exists
//            if (!picturesDir.exists()) {
//                picturesDir.mkdirs()
//            }
//
//            photoUri = FileProvider.getUriForFile(
//                context,
//                "${context.packageName}.provider",
//                photoFile
//            )
//
//            // Launch the camera intent
//            cameraLauncher.launch(photoUri!!)
//        }) {
//            Text(text = "Open Camera")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(onClick = {
//            galleryLauncher.launch("image/*")
//        }) {
//            Text(text = "Open Gallery")
//        }
//    }
//}
