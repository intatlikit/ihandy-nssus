package com.nssus.ihandy.ui.basecomposable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissions(content: @Composable () -> Unit) {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    val storagePermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )

    when {
        cameraPermissionState.status.isGranted && storagePermissionState.allPermissionsGranted -> {
            content()
        }
        else -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Permissions Required")
                Button(onClick = {
                    cameraPermissionState.launchPermissionRequest()
                    storagePermissionState.launchMultiplePermissionRequest()
                }) {
                    Text("Grant Permissions")
                }
            }
        }
    }
}
