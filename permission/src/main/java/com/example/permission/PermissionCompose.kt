package com.example.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

@Composable
fun PermissionCompose(
    permissions: List<String>,
    state: PermissionComposeState,
    showRationale: @Composable ((proceed: () -> Unit) -> Unit)? = null,
    onGranted: () -> Unit,
    onDenied: (List<String>) -> Unit,
    onNeverAskAgain: (List<String>) -> Unit,
    onAnyResult: () -> Unit
) {
    val context = LocalContext.current
    val deniedPermissions = rememberSaveableStateList<String>()
    val neverAskAgainPermissions = rememberSaveableStateList<String>()

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        val denied = results.filterValues { !it }.keys

        val neverAskAgain = denied.filter { permission ->
            !shouldShowRequestPermissionRationale(context, permission)
        }

        deniedPermissions.clear()
        deniedPermissions.addAll(denied - neverAskAgain.toSet())

        neverAskAgainPermissions.clear()
        neverAskAgainPermissions.addAll(neverAskAgain)

        when {
            neverAskAgain.isNotEmpty() -> onNeverAskAgain(neverAskAgain.toList())
            denied.isNotEmpty() -> onDenied(denied.toList())
            else -> onGranted()
        }

        onAnyResult()
        state.dismiss()
    }

    LaunchedEffect(state.visible) {
        if (state.visible) {
            val currentlyDenied = permissions.filter {
                ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
            }
            if (currentlyDenied.isEmpty()) {
                onGranted()
                onAnyResult()
                state.dismiss()
            } else {
                deniedPermissions.addAll(currentlyDenied)
            }
        }
    }

    if (
        state.visible &&
        showRationale != null &&
        !permissions.allGranted(context)
    ) {
        showRationale {
            launcher.launch(permissions.toTypedArray())
        }
    } else if (
        state.visible &&
        !permissions.allGranted(context)
    ) {
        launcher.launch(permissions.toTypedArray())
    }
}

private fun shouldShowRequestPermissionRationale(context: Context, permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(
        context as Activity,
        permission
    )
}

@Composable
fun <T> rememberSaveableStateList(initial: List<T> = emptyList()): SnapshotStateList<T> {
    return rememberSaveable(
        saver = listSaver(
            save = { it.toList() },
            restore = { it.toMutableStateList() }
        )
    ) {
        initial.toMutableStateList()
    }
}

private fun List<String>.allGranted(context: Context): Boolean {
    return all {
        ContextCompat.checkSelfPermission(
            context,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }
}