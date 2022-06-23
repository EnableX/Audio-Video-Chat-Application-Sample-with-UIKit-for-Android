package com.enablex.uikitsample.utility

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.DisplayMetrics
import androidx.core.app.ActivityCompat

object Utils {
    fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    fun getScreenHeight(context: Activity): Int {
        var height = 0
        return try {
            val displayMetrics = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(displayMetrics)
            height = displayMetrics.heightPixels
            height
        } catch (exception: Exception) {
            height
        }
    }

    fun getScreenWidth(context: Activity): Int {
        var width = 0
        return try {
            val displayMetrics = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(displayMetrics)
            width = displayMetrics.widthPixels
            width
        } catch (exception: Exception) {
            width
        }
    }
}
