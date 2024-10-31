package com.coordinadora.coordinadoraapp.service.permission


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.launch

class PermissionManager(
    private val activity: Activity,
    private val lifecycleScope: LifecycleCoroutineScope,
    private val onPermissionsGranted: () -> Unit
) {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var requestManageStorageLauncher: ActivityResultLauncher<Intent>

    fun setupPermissionLaunchers(
        permissionLauncher: ActivityResultLauncher<Array<String>>,
        manageLauncher: ActivityResultLauncher<Intent>
    ) {
        requestPermissionLauncher = permissionLauncher
        requestManageStorageLauncher = manageLauncher
    }

    fun checkAndRequestPermissions() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                if (!Environment.isExternalStorageManager()) {
                    requestManageExternalStoragePermission()
                } else {
                    onPermissionsGranted()
                }
            }

            Build.VERSION.SDK_INT == Build.VERSION_CODES.Q -> {
                onPermissionsGranted()
            }

            else -> {
                requestLegacyStoragePermissions()
            }
        }
    }

    fun handlePermissionResult(permissions: Map<String, Boolean>) {
        val allPermissionsGranted = permissions.values.all { it }
        if (allPermissionsGranted) {
            onPermissionsGranted()
        } else {
            val deniedPermissions = permissions.filterValues { !it }
            handleDeniedPermissions(deniedPermissions.keys)
        }
    }

    fun handleManageStorageResult() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                onPermissionsGranted()
            } else {
                showStoragePermissionRequired()
            }
        }
    }

    private fun requestLegacyStoragePermissions() {
        val permissionsNeeded = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (permissionsNeeded.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsNeeded.toTypedArray())
        } else {
            onPermissionsGranted()
        }
    }

    private fun requestManageExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                    data = Uri.parse("package:${activity.applicationContext.packageName}")
                }
                requestManageStorageLauncher.launch(intent)
            } catch (e: Exception) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                requestManageStorageLauncher.launch(intent)
            }
        }
    }

    private fun handleDeniedPermissions(deniedPermissions: Set<String>) {
        lifecycleScope.launch {
            val shouldShowRationale = deniedPermissions.any { permission ->
                activity.shouldShowRequestPermissionRationale(permission)
            }

            if (shouldShowRationale) {
                showPermissionRationaleDialog()
            } else {
                showPermissionRequiredDialog()
            }
        }
    }

    private fun showPermissionRationaleDialog() {
        Toast.makeText(
            activity,
            "Storage permission is required to save files",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showPermissionRequiredDialog() {
        Toast.makeText(
            activity,
            "Please enable storage access in settings",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showStoragePermissionRequired() {
        Toast.makeText(
            activity,
            "It is necessary to enable storage access",
            Toast.LENGTH_LONG
        ).show()
    }
}