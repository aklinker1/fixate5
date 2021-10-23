package io.aklinker1.files.common.repos

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import io.aklinker1.files.BaseActivity

typealias PermissionGrantedCallback = (isGranted: Boolean) -> Unit

class PermissionsRepo(private val activity: BaseActivity) {

  // Utils

  private fun isUsingNewManageExternalStorage(): Boolean =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

  private fun hasGrantedPermission(permission: String): () -> Boolean {
    return fun(): Boolean {
      val result =
        ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
      Log.d("fixate", "hasGrantedPermission: $permission = $result")
      return result
    }
  }

  private val permissionCallbacks: ArrayList<PermissionGrantedCallback> = ArrayList(1)
  private val requestPermissionLauncher =
    activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
      callPermissionCallbacks(it)
    }

  fun addPermissionCallback(callback: PermissionGrantedCallback) {
    permissionCallbacks.add(callback)
  }

  private fun callPermissionCallbacks(isGranted: Boolean) {
    permissionCallbacks.forEach { callback -> callback(isGranted) }
  }

  private fun requestPermission(permission: String): () -> Unit {
    val isGranted = hasGrantedPermission(permission)
    return fun() {
      when {
        isGranted() -> callPermissionCallbacks(true)
//        TODO: Warn the user
//            ActivityCompat.shouldShowRequestPermissionRationale(activity)
//            -> {
//                // In an educational UI, explain to the user why your app requires this
//                // permission for a specific feature to behave as expected. In this UI,
//                // include a "cancel" or "no thanks" button that allows the user to
//                // continue using your app without granting the permission.
//            }
        else -> {
          requestPermissionLauncher.launch(permission)
        }
      }
    }
  }

  // MANAGE_EXTERNAL_STORAGE

  @RequiresApi(Build.VERSION_CODES.R)
  private fun canManageExternalStorage(): Boolean = Environment.isExternalStorageManager()

  @RequiresApi(Build.VERSION_CODES.R)
  private fun requestManageExternalStorage() {
    val intent = Intent().apply {
      action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
      data = Uri.fromParts("package", activity.packageName, null)
    }
    activity.startActivity(intent)
  }

  // WRITE_EXTERNAL_STORAGE

  private val canWriteExternalStorage =
    hasGrantedPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
  private val requestWriteExternalStorage =
    requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)

  // FilePermissionCompat

  fun hasGrantedFilePermissionCompat(): Boolean =
    if (isUsingNewManageExternalStorage()) canManageExternalStorage()
    else canWriteExternalStorage()

  fun requestFilePermissionCompat() =
    if (isUsingNewManageExternalStorage()) requestManageExternalStorage()
    else requestWriteExternalStorage()
}
