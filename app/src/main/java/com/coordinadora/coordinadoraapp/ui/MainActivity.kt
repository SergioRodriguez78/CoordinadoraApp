package com.coordinadora.coordinadoraapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.coordinadora.coordinadoraapp.service.permission.PermissionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var permissionManager: PermissionManager
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var requestManageStorageLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setupPermissions()

        setContent {
            App()
        }
    }
    private fun setupPermissions() {
        permissionManager = PermissionManager(
            activity = this,
            lifecycleScope = lifecycleScope,
            onPermissionsGranted = {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
            }
        )

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            permissionManager.handlePermissionResult(permissions)
        }

        requestManageStorageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { _ ->
            permissionManager.handleManageStorageResult()
        }


        permissionManager.setupPermissionLaunchers(
            requestPermissionLauncher,
            requestManageStorageLauncher
        )

        permissionManager.checkAndRequestPermissions()
    }
}
