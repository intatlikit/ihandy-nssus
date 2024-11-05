package com.nssus.ihandy.ui.basecomposable

import android.graphics.Bitmap
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.DialogProperties
import com.nssus.ihandy.R
import com.nssus.ihandy.ui.theme.FontStyles
import com.nssus.ihandy.ui.theme.SilverGray

@Composable
fun DisplayWebViewButton(
    @StringRes buttonTextId: Int = R.string.common_open_web_view_button,
    url: String
) {
    var showDialog by remember { mutableStateOf(false) }

    BaseButton(
        text = stringResource(id = buttonTextId),
        onButtonClick = { showDialog = true }
    )

    if (showDialog) {
        FullScreenWebViewDialog(
            url = url,
            onDismiss = { showDialog = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenWebViewDialog(url: String, onDismiss: () -> Unit) {
    var webView: WebView? = remember { null }
    var canGoBack by remember { mutableStateOf(false) }
    var currentUrl by remember { mutableStateOf(url) } // Holds the current URL

    // Track changes to `canGoBack` when navigating in WebView
    LaunchedEffect(webView) {
        while (true) {
            canGoBack = webView?.canGoBack() == true
            kotlinx.coroutines.delay(100) // Polling for back state changes
        }
    }

    AlertDialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // WebView Controller Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SilverGray),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    IconButton(
                        modifier = Modifier.size(36.dp),
                        onClick = { webView?.goBack() },
                        enabled = canGoBack // Enables button only if WebView can go back
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back Icon",
                            tint = if (canGoBack) Color.Black else Color.White
                        )
                    }
                    IconButton(
                        modifier = Modifier.size(36.dp),
                        onClick = { webView?.reload() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh Icon",
                            tint = Color.Black
                        )
                    }
                }
                Text(
                    modifier = Modifier.weight(1f),
                    text = currentUrl,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = FontStyles.txt14
                )
                IconButton(
                    modifier = Modifier.size(36.dp),
                    onClick = onDismiss
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.Black
                    )
                }
            }

            // WebView Content
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    WebView(context).apply {
                        webView = this

                        settings.javaScriptEnabled = true
                        settings.builtInZoomControls = true // Enable Zoom Gesture
                        settings.cacheMode = WebSettings.LOAD_DEFAULT

                        // Custom WebViewClient to track URL changes
                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                super.onPageStarted(view, url, favicon)
                                currentUrl = url ?: ""
                            }
//                            override fun onPageFinished(view: WebView, url: String?) {
//                                super.onPageFinished(view, url)
//                                currentUrl = url ?: ""
//                            }
                        }

                        loadUrl(url)
                    }
                },
                update = { webViewInstance ->
                    webView = webViewInstance
                }
            )
        }
    }
}

@Preview(showBackground = true, locale = "th")
@Composable
fun BaseWebViewDialogPreview() {
    FullScreenWebViewDialog(
        url = "", onDismiss = {}
    )
}