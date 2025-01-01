package ir.sinanakhaei.baseproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.permission.PermissionCompose
import com.example.permission.rememberPermissionComposeState
import dagger.hilt.android.AndroidEntryPoint
import ir.sinanakhaei.baseproject.ui.theme.BaseprojectTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            BaseprojectTheme {
                val permissionState = rememberPermissionComposeState()
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    PermissionCompose(
                        state = permissionState,
                        permissions = listOf(
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.READ_CONTACTS,
                        ),
                        showRationale = { proceed ->
                            // Show rationale dialog
                            AlertDialog(
                                onDismissRequest = { /* Do nothing */ },
                                title = { Text("Permissions Required") },
                                text = { Text("We need access to for the app to function.") },
                                confirmButton = {
                                    Button(onClick = {
                                        proceed()
                                    }) {
                                        Text("Proceed")
                                    }
                                },
                                dismissButton = {
                                    Button(onClick = {
                                        permissionState.dismiss()
                                    }) {
                                        Text("Cancel")
                                    }
                                }
                            )
                        },
                        onGranted = { println("All permissions granted") },
                        onDenied = { deniedPermissions -> println("Permissions denied: $deniedPermissions") },
                        onNeverAskAgain = { neverAskPermissions -> println("Never ask again for: $neverAskPermissions") },
                        onAnyResult = { permissionState.dismiss() }
                    )

                    Text("Hello",
                        modifier = Modifier
                            .padding(200.dp)
                            .clickable {
//                                permissionState.trigger()
                            }
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        Permission.handleResult(requestCode, permissions, grantResults)
    }
}