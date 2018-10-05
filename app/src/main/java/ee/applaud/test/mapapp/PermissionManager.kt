package ee.applaud.test.mapapp

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

/**
 * This class is responsible for checking if required permissions are granted or not.
 */

class PermissionManager {
    private val requiredSdkPermissions = WRITE_EXTERNAL_STORAGE

    private val code = 101

    fun setUpPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(activity,
                arrayOf(requiredSdkPermissions),
                code)
    }

    fun hasRequiredPermissions(activity: Activity): Boolean {
        val permission = ContextCompat.checkSelfPermission(activity.baseContext,
                requiredSdkPermissions)
        return permission == PackageManager.PERMISSION_GRANTED
    }
}
