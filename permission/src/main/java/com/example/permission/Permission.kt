package com.example.permission

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.ref.WeakReference

class Permission private constructor(
    activity: Activity,
    private val permissions: List<String>,
    private val showRationale: ((List<String>) -> Unit)?,
    private val onGranted: (() -> Unit)?,
    private val onDenied: ((List<String>) -> Unit)?,
    private val onNeverAsk: ((List<String>) -> Unit)?
) {

    private val activityRef = WeakReference(activity)
    private val requestCode = 1001 // Arbitrary request code

    init {
        currentInstance = WeakReference(this)
    }

    fun request() {
        val activity = activityRef.get() ?: return

        val deniedPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
        }

        if (deniedPermissions.isEmpty()) {
            onGranted?.invoke()
            return
        }

        val rationalePermissions = deniedPermissions.filter {
            ActivityCompat.shouldShowRequestPermissionRationale(activity, it)
        }

        if (rationalePermissions.isNotEmpty()) {
            showRationale?.invoke(rationalePermissions)
        }

        ActivityCompat.requestPermissions(activity, deniedPermissions.toTypedArray(), requestCode)
    }

    fun handlePermissionsResult(
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        val activity = activityRef.get() ?: return

        val grantedPermissions = mutableListOf<String>()
        val deniedPermissions = mutableListOf<String>()
        val neverAskAgainPermissions = mutableListOf<String>()

        permissions.forEachIndexed { index, permission ->
            when {
                grantResults[index] == PackageManager.PERMISSION_GRANTED -> grantedPermissions.add(permission)
                !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) -> neverAskAgainPermissions.add(permission)
                else -> deniedPermissions.add(permission)
            }
        }

        when {
            grantedPermissions.size == this.permissions.size -> onGranted?.invoke()
            neverAskAgainPermissions.isNotEmpty() -> onNeverAsk?.invoke(neverAskAgainPermissions)
            deniedPermissions.isNotEmpty() -> onDenied?.invoke(deniedPermissions)
        }
    }

    companion object {
        private var currentInstance: WeakReference<Permission>? = null

        fun handleResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            val instance = currentInstance?.get()
            if (instance != null && requestCode == instance.requestCode) {
                instance.handlePermissionsResult(permissions, grantResults)
                currentInstance = null // Clear instance after handling the result
            }
        }
    }

    class Builder(private val activity: Activity) {
        private var permissions: List<String> = emptyList()
        private var showRationale: ((List<String>) -> Unit)? = null
        private var onGranted: (() -> Unit)? = null
        private var onDenied: ((List<String>) -> Unit)? = null
        private var onNeverAsk: ((List<String>) -> Unit)? = null

        fun permissions(vararg perms: String) = apply {
            this.permissions = perms.toList()
        }

        fun showRationale(callback: (List<String>) -> Unit) = apply {
            this.showRationale = callback
        }

        fun onGranted(callback: () -> Unit) = apply {
            this.onGranted = callback
        }

        fun onDenied(callback: (List<String>) -> Unit) = apply {
            this.onDenied = callback
        }

        fun onNeverAsk(callback: (List<String>) -> Unit) = apply {
            this.onNeverAsk = callback
        }

        fun build(): Permission {
            return Permission(
                activity = activity,
                permissions = permissions,
                showRationale = showRationale,
                onGranted = onGranted,
                onDenied = onDenied,
                onNeverAsk = onNeverAsk
            )
        }
    }
}