package com.nssus.ihandy.data.extension

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

fun Context.restartIHandyApp() {
    val packageManager: PackageManager = this.packageManager
    val intent: Intent = packageManager.getLaunchIntentForPackage(this.packageName)!!
    val componentName: ComponentName = intent.component!!
    val restartIntent: Intent = Intent.makeRestartActivityTask(componentName)
    this.startActivity(restartIntent)
    Runtime.getRuntime().exit(0)
}