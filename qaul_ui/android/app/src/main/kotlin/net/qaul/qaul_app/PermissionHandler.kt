package net.qaul.qaul_app

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.material.dialog.MaterialAlertDialogBuilder

import net.qaul.ble.AppLog

class PermissionHandler(private val context: Context) {
    companion object {
        private const val packageName = "net.qaul.qaul_app"

        private const val BLE_PERMISSION_REQ_CODE_12 = 114
        private const val LOCATION_PERMISSION_REQ_CODE = 111
        private const val WIFI_PERMISSION_REQUEST_CODE = 1001

        private const val LOCATION_ENABLE_REQ_CODE = 112
        private const val BG_LOCATION_ENABLE_REQ_CODE = 123
        private const val REQUEST_ENABLE_BT = 113

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE)
    }

    private var permissionCallback: ((Boolean) -> Unit)? = null

    fun checkAndRequestPermissions(callback: (Boolean) -> Unit) {
        permissionCallback = callback

        val permissionsToRequest = mutableListOf<String>()
        for (permission in REQUIRED_PERMISSIONS) {
            val permissionStatus = ContextCompat.checkSelfPermission(context, permission)
            if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission)
            }
        }

        if (permissionsToRequest.isEmpty()) {
            // All permissions are already granted
            permissionCallback?.invoke(true)
        } else {
            ActivityCompat.requestPermissions(context as Activity, permissionsToRequest.toTypedArray(), WIFI_PERMISSION_REQUEST_CODE)
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == WIFI_PERMISSION_REQUEST_CODE) {
            var allPermissionsGranted = true
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false
                    break
                }
            }
            permissionCallback?.invoke(allPermissionsGranted)
        }

        if (requestCode == LOCATION_PERMISSION_REQ_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Foreground location permission granted, now check for background permission
                checkBackgroundLocationPermission()
            }
        }
    }

    fun hasLocationPermission(): Boolean {
        val permissionStatus = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        return permissionStatus == PackageManager.PERMISSION_GRANTED
    }

    fun hasBLEPermission(): Boolean {
        val permissionStatus = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        return permissionStatus == PackageManager.PERMISSION_GRANTED
    }

    fun requestBLEPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_ADVERTISE), BLE_PERMISSION_REQ_CODE_12)
        }
    }

    fun requestLocationPermission() {
        if (hasLocationPermission()) {
            checkBackgroundLocationPermission()
            return
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE)
        } else {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION), LOCATION_PERMISSION_REQ_CODE)
        }
    }

    private fun checkBackgroundLocationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            // For Android versions before 10, there's no distinction between foreground and background location
            return
        }
        val permission = ContextCompat.checkSelfPermission(context as Activity, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            return
        }
        requestBackgroundPermissionOrNavigateToSettings()
    }

    private fun requestBackgroundPermissionOrNavigateToSettings() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            showBackgroundPermissionPreNavigationDialog()
        } else {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION), BG_LOCATION_ENABLE_REQ_CODE)
        }
    }

    private fun showBackgroundPermissionPreNavigationDialog() {
        val builder: MaterialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
        builder.setTitle("Background Location Permission")
        builder.setMessage("""
            You will now be routed to the app settings screen.
            
            Please select the option "Allow all the time" to enable BLE usage while the app is in the background.
            
            This option can be found in Permissions > Location.
        """.trimIndent())
        builder.setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
            val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", packageName, null)
            }
            (context as Activity).startActivityForResult(intent, BG_LOCATION_ENABLE_REQ_CODE)
        }
        builder.setCancelable(false)
        builder.show()
    }
}
